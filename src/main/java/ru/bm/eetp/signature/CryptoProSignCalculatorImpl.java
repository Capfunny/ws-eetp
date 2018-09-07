package ru.bm.eetp.signature;

import ru.CryptoPro.JCP.Sign.CryptoProSign;
import ru.bm.eetp.signature.keystore.PrivateKeySupplier;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;

public class CryptoProSignCalculatorImpl implements SignCalculator {

    private byte[] bodyBytes;
    private byte[] signatureBytes;

    private PrivateKeySupplier privateKeySupplier;

    public CryptoProSignCalculatorImpl(byte[] body, PrivateKeySupplier privateKeySupplier) {
        this.bodyBytes = body;
        this.privateKeySupplier = privateKeySupplier;
    }

    @Override
    public void calculate() {
        signatureBytes = signGOST(privateKeySupplier.privateKey(), bodyBytes);
    }

    @Override
    public byte[] signature() {
        return signatureBytes;
    }


    /**
     * Создание подписи
     *
     * @param privateKey закрытый ключ
     * @param data       подписываемые данные
     * @return подпись
     * @throws Exception /
     */
    private byte[] signGOST(PrivateKey privateKey, byte[] data) {

        try {
            CryptoProSign signature = new CryptoProSign();
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }


}
