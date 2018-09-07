package ru.bm.eetp.service;

import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.dto.XMLPackage;

/**
 * Created by Capfunny on 24.08.2018.
 */
public interface OutcomingPackageProcessor {

    abstract ProcessResult processing(String content);

    abstract XMLPackage getXMLPackage();
}
