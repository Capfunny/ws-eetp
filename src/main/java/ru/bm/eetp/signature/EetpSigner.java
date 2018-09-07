package ru.bm.eetp.signature;

public interface EetpSigner {
    /**
     *
     * @param signatureIdentification - идентификатор ключа для генерации подписи
     *
     *                                  например: VTB_FOR_EETP - для основной генерации ключа
     *                                                           при отправке документов во внешний сервис
     *
     *                                     или  DEBUG_ONE_FOR_EETP_MOCK_SIGNATURE - для генерации "отладочной" подписи
     *                                                                          при тестировании внешних XML документов
     *                                                                          для которых мы ещё не имеем открытых ключей
     *                                     или  DEBUG_TWO_FOR_EETP_MOCK_SIGNATURE - для "отладочной" подписи
     *                                                                              для второй площадки
     * @param stringDocumentBody
     * @return
     */
    SignCalculator calculator(String signatureIdentification, String stringDocumentBody);
}
