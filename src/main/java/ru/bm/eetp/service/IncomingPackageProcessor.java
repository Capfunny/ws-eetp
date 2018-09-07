package ru.bm.eetp.service;

import ru.bm.eetp.dto.ProcessResult;

public interface IncomingPackageProcessor {

    public ProcessResult processing(String content);

}
