package ru.bm.eetp.signature;

import com.objsys.asn1j.runtime.*;
import org.springframework.stereotype.Component;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.ContentInfo;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignedData;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.SignerInfo;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Attribute;
import ru.CryptoPro.JCP.JCP;
import ru.CryptoPro.JCP.params.OID;
import ru.CryptoPro.JCP.tools.Array;
import ru.CryptoPro.JCP.tools.Decoder;
import ru.bm.eetp.config.Utils;

import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


@Component
public class CMSSignatureValidatorImpl implements SignatureValidator {

    private boolean validateResult = false;

    @Override
    public boolean validateSignature(String document, String signature, Certificate[]  certificates){
        try {
            Utils.debugg("Попытка валидации подписи:");
            //начальная инициализация
            byte[] docbytes = document.getBytes();
            final Decoder decoder = new Decoder();
            final byte[] signbytes = decoder.decodeBuffer(signature);
            final Asn1BerDecodeBuffer asnBuf = new  Asn1BerDecodeBuffer(signbytes);
            final ContentInfo ci = new ContentInfo();
            ci.decode(asnBuf);
            final SignedData cms = (SignedData) ci.content;

            //проверка сертификатов
            X509Certificate certificate = null;
            final Certificate[] signatureCerts = SignatureUtils.readSignarureCertificates(ci);
            if (signatureCerts.length == 0){
                throw new Exception("Сертификаты в подписи не найдены!");
            }
            //если передан набор внешних сертификатов - верифицируем по ним
            if (certificates != null && certificates.length > 0) {
                Utils.debugg("для валидации использованы внешние сертификаты ("+certificates.length+")");
                for (int i = 0; i < certificates.length; i++)
                {
                    final X509Certificate cert = (X509Certificate) certificates[i];
                    for (int j = 0; j < cms.signerInfos.elements.length; j++)
                    {
                        try{
                            validateResult = VerifyByCert(docbytes, cms.signerInfos.elements[j], cert);
                            if (validateResult) {break;}
                        }
                        catch (Exception e){
                            Utils.debugg("ошибка валидации по сертификату ["+cert.getSubjectDN()+"]\n ==>", e);
                        }
                    } // for
                } // for
            }
            //иначе пробуем верифицировать по каждому из найденных в подписи
            else{
                Utils.debugg("для валидации использованы внутренние сертификаты ("+signatureCerts.length+")");
                for (int i = 0; i < signatureCerts.length; i++)
                {
                    final X509Certificate cert = (X509Certificate)signatureCerts[i];
                    try{
                        validateResult = VerifyByCert(docbytes, cms.signerInfos.elements[i], cert);
                        if (validateResult) {break;}
                    }
                    catch (Exception e){
                        Utils.debugg("ошибка валидации по сертификату ["+cert.getSubjectDN()+"]\n ==>", e);
                    }
                }
            }
        }
        catch (Exception e)
        {
            Utils.debugg("Произошли ошибки валидации ==>", e.fillInStackTrace());
        }
        //конечная обработка результатов
        if (validateResult) {
            Utils.debugg("Валидация подписи прошла успешно!");
        }
        else {
            Utils.debugg("Не удалось валидировать подпись!");
        }
        return validateResult;
    }

    private boolean VerifyByCert(byte[] docbytes, SignerInfo info, X509Certificate certificate) throws Exception
    {
        //дальнейшая проверка по соответствующему сертификату
        int[] digestAlgorithm = info.digestAlgorithm.algorithm.value;
        int[] keyAlgorithm = info.signatureAlgorithm.algorithm.value;
        final byte[] data;
        if (info.signedAttrs == null) {
            //аттрибуты подписи не присутствуют, верификация по документу
            Utils.debugg("валидация проводится по ==> документу");
            data = docbytes;
        }
        else {
            //присутствуют аттрибуты подписи (SignedAttr)
            Utils.debugg("валидация проводится по ==> атрибутам");
            final Attribute[] signAttrElem = info.signedAttrs.elements;

            //проверка аттрибута message-digest
            final Asn1ObjectIdentifier messageDigestOid = new Asn1ObjectIdentifier(
                    (new OID(SignatureUtils.STR_CMS_OID_DIGEST_ATTR)).value);

            Attribute messageDigestAttr = null;

            for (int r = 0; r < signAttrElem.length; r++) {
                final Asn1ObjectIdentifier oid = signAttrElem[r].type;
                if (oid.equals(messageDigestOid)) {
                    messageDigestAttr = signAttrElem[r];
                } // if
            } // for

            if (messageDigestAttr == null) {
                throw new Exception("message-digest attribute not present");
            } // if

            final Asn1Type open = messageDigestAttr.values.elements[0];
            final Asn1OctetString hash = (Asn1OctetString) open;
            final byte[] md = hash.value;

            //вычисление messageDigest
            final byte[] dm = SignatureUtils.digestm(docbytes, SignatureUtils.getOIDString(digestAlgorithm));

            if (!Array.toHexString(dm).equals(Array.toHexString(md))) {
                throw new Exception("ошибка верификации хэша документа!");
            } // if

            //верификация по атрибутам!
            final Asn1BerEncodeBuffer encBufSignedAttr = new Asn1BerEncodeBuffer();
            info.signedAttrs.needSortSignedAttributes = false;
            info.signedAttrs.encode(encBufSignedAttr);
            data = encBufSignedAttr.getMsgCopy();
        }
        //окончательная проверка
        final byte[] sign = info.signature.value;
        String signAlgorithm = SignatureUtils.getSignatureAlgorithm(digestAlgorithm, keyAlgorithm);
        Utils.debugg("алгоритм шифрования ==>", signAlgorithm);

        final Signature signobj = Signature.getInstance(signAlgorithm, JCP.PROVIDER_NAME);
        signobj.initVerify(certificate);
        signobj.update(data);

        return signobj.verify(sign);
    }

    @Override
    public boolean getValidateResult() {
        return validateResult;
    }


}
