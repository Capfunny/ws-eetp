package ru.bm.eetp.signature.keystore;

import java.security.PrivateKey;

public interface PrivateKeySupplier {
    PrivateKey privateKey();
}
