package ru.bm.eetp.signature;

import com.objsys.asn1j.runtime.Asn1BerEncodeBuffer;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.ContentInfo;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignedData;
import ru.CryptoPro.JCP.JCP;

import java.io.ByteArrayInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class SignatureUtils {

    public static final String STR_CMS_OID_DIGEST_ATTR = "1.2.840.113549.1.9.4";

    public static Certificate[] readSignarureCertificates(ContentInfo contentInfo ) {
        Certificate[] certificates;
        try {
            certificates = new Certificate[((SignedData) contentInfo.content).certificates.elements.length];
            final Asn1BerEncodeBuffer encBuf = new Asn1BerEncodeBuffer();
            for (int i = 0; i < ((SignedData) contentInfo.content).certificates.elements.length; i++) {
                ((SignedData) contentInfo.content).certificates.elements[i].encode(encBuf);

                final CertificateFactory cf = CertificateFactory.getInstance("X.509");
                final Certificate cert = cf.generateCertificate(encBuf.getInputStream());
                certificates[i] = cert;
            }
        }
        catch (Exception e){
            certificates = null;
        }
        return certificates;
    }

    public static String getOIDString(int[] OID){
        String algorithmString = new String();
        for (int n : OID){
            algorithmString = algorithmString + (algorithmString.length() == 0 ?  Integer.toString(n) : "." + Integer.toString(n) );
        }
        return algorithmString;
    }

    public static String getSignatureAlgorithm(int[] digest_OID, int[] key_OID){
        return getOIDString(digest_OID)+"with"+getOIDString(key_OID);
    }

    public static byte[] digestm(byte[] bytes, String digestAlgorithmName) throws Exception {

        //calculation messageDigest
        final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        final MessageDigest digest = MessageDigest.getInstance(digestAlgorithmName, JCP.PROVIDER_NAME);
        final DigestInputStream digestStream = new DigestInputStream(stream, digest);

        while (digestStream.available() != 0) digestStream.read();

        return digest.digest();
    }
}
