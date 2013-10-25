package ru.yandex.money.api;

import ru.yandex.money.api.enums.OperationHistoryType;
import ru.yandex.money.api.response.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * <p/>
 * <p/>
 * Copyright 2012 Yandex Money, All rights reserved.
 * <p/>
 * Date: 25.10.13 18:52
 *
 * @author sergeev
 */
public interface ApiCommandsFacade {
    /**
     * URI API сервера Яндекс.Денег
     */
    String URI_YM_API = "https://money.yandex.ru/api";

    /**
     * Метод для отзыва токена авторизации. При отзыве токена, все права, выданные этому токену, будут отменены.
     *
     * @param accessToken токен авторизации пользователя
     * @throws java.io.IOException           ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InvalidTokenException указан несуществующий, просроченный, или отозванный токен
     */
    void revokeOAuthToken(String accessToken) throws IOException, InvalidTokenException;

    /**
     * Метод получения информации о текущем состоянии счета пользователя.
     * Требуемые права токена: account-info
     *
     * @param accessToken string токен авторизации пользователя
     * @return экземпляр класса {@link ru.yandex.money.api.response.AccountInfoResponse}
     * @throws java.io.IOException        ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException запрошена операция, на которую у
     *                                    токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException      указан несуществующий, просроченный, или отозванный токен.
     */
    AccountInfoResponse accountInfo(String accessToken)
            throws IOException, InvalidTokenException,
            InsufficientScopeException;

    /**
     * Метод позволяет просматривать историю операций (полностью или частично)
     * в постраничном режиме. Записи истории выдаются в обратном хронологическом
     * порядке. Операции выдаются для постраничного отображения (ограниченное количество).
     * Требуемые права токена: operation-history.
     *
     * @param accessToken    токен авторизации пользователя
     * @param startRecord    integer порядковый номер первой записи в выдаче. По умолчанию
     *                       выдается с первой записи
     * @param records        количество запрашиваемых записей истории операций.
     *                       Допустимые значения: от 1 до 100, по умолчанию 30.
     * @param operationsType перечень типов операций, которые требуется отобразить.
     *                       Типы операций перечисляются через пробел. В случае, если параметр
     *                       отсутствует, выводятся все операции. Возможные значения: payment deposition.
     *                       В качестве разделителя элементов списка используется пробел, элементы списка
     *                       чувствительны к регистру.
     * @return экземпляр класса {@link ru.yandex.money.api.response.OperationHistoryResponse}
     * @throws java.io.IOException          ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException   запрошена операция, на которую у
     *                                      токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException        указан несуществующий, просроченный, или отозванный токен.
     */
    OperationHistoryResponse operationHistory(String accessToken,
                                              Integer startRecord, Integer records,
                                              Set<OperationHistoryType> operationsType) throws IOException,
            InvalidTokenException, InsufficientScopeException;

    /**
     * Метод позволяет просматривать историю операций (полностью или частично)
     * в постраничном режиме. Записи истории выдаются в обратном хронологическом
     * порядке. Операции выдаются для постраничного отображения (ограниченное количество).
     * Требуемые права токена: operation-history.
     *
     * @param accessToken токен авторизации пользователя
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.OperationHistoryResponse}
     * @throws java.io.IOException        ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException запрошена операция, на которую у
     *                                    токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException      указан несуществующий, просроченный, или отозванный токен.
     */
    OperationHistoryResponse operationHistory(String accessToken)
            throws IOException, InvalidTokenException,
            InsufficientScopeException;

    OperationHistoryResponse operationHistory(String accessToken,
                                              Integer startRecord)
                    throws IOException, InvalidTokenException,
                    InsufficientScopeException;

    /**
     * Метод позволяет просматривать историю операций (полностью или частично)
     * в постраничном режиме. Записи истории выдаются в обратном хронологическом
     * порядке. Операции выдаются для постраничного отображения (ограниченное количество).
     * Требуемые права токена: operation-history.
     *
     * @param accessToken токен авторизации пользователя
     * @param startRecord запись с которой начинать вывод
     * @param records     количество записей
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.OperationHistoryResponse}
     * @throws java.io.IOException        ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException запрошена операция, на которую у
     *                                    токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException      указан несуществующий, просроченный, или отозванный токен.
     */
    OperationHistoryResponse operationHistory(String accessToken,
                                              Integer startRecord, Integer records)
            throws IOException, InvalidTokenException,
            InsufficientScopeException;

    /**
     * Метод получения детальной информации по операции из истории.
     *
     * @param accessToken токен авторизации пользователя
     * @param operationId идентификатор операции. Значение параметра соответствует
     *                    либо значению поля operationId ответа метода operationHistory, либо, в
     *                    случае если запрашивается история счета плательщика, значению поля
     *                    paymentId ответа метода processPayment.
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.OperationHistoryResponse}
     * @throws java.io.IOException        ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException запрошена операция, на которую у
     *                                    токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException      указан несуществующий, просроченный, или отозванный токен.
     */
    OperationDetailResponse operationDetail(String accessToken,
                                            String operationId) throws IOException, InvalidTokenException,
            InsufficientScopeException;

    /**
     * <p>Запрос p2p перевода другому пользователю.</p>
     * <b>Внимание</b>: перевод на счет пользователя, чей токен указывается в параметрах,
     * невозможен. Т.е. самому себе делать переводы нельзя.
     *
     * @param accessToken токен авторизации пользователя
     * @param to          номер счета получателя платежа (счет Яндекс.Денег)
     * @param amount      сумма перевода. Представляет собой число с фиксированной точкой,
     *                    два знака после точки.
     * @param comment     название платежа, отображается только в истории платежей
     *                    отправителя.
     * @param message     сообщение получателю платежа.
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.RequestPaymentResponse}
     * @throws java.io.IOException          ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException   запрошена операция, на которую у
     *                                      токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException        указан несуществующий, просроченный, или отозванный токен.
     * @throws ru.yandex.money.api.InternalServerErrorException техническая ошибка сервера Яндекс.Денег
     */
    RequestPaymentResponse requestPaymentP2P(String accessToken,
                                             String to, BigDecimal amount, String comment,
                                             String message) throws IOException, InvalidTokenException,
            InsufficientScopeException;

    /**
     * Запрос оплаты в магазин.
     *
     * @param accessToken токен авторизации пользователя
     * @param patternId   идентификатор шаблона платежа
     * @param params      пользовательские параметры шаблона платежа, требуемые
     *                    магазином.
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.RequestPaymentResponse}
     * @throws java.io.IOException                  ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException   запрошена операция, на которую у
     *                                      токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException        указан несуществующий, просроченный, или отозванный токен.
     * @throws ru.yandex.money.api.InternalServerErrorException техническая ошибка сервера Яндекс.Денег
     */
    RequestPaymentResponse requestPaymentShop(String accessToken,
                                              String patternId, Map<String, String> params) throws IOException,
            InvalidTokenException, InsufficientScopeException;

    /**
     * Метод подтверждения платежа с оплатой с привязанной карты.
     *
     * @param accessToken токен авторизации пользователя
     * @param requestId   идентификатор запроса (requestId), полученный с
     *                    помощью методов requestPayment*.
     * @param csc         Card Security Code, CVV2/CVC2-код привязанной
     *                    банковской карты пользователя.
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.ProcessPaymentResponse}
     * @throws java.io.IOException          ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException   запрошена операция, на которую у
     *                                      токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException        указан несуществующий, просроченный, или отозванный токен.
     * @throws ru.yandex.money.api.InternalServerErrorException техническая ошибка сервера Яндекс.Денег
     */
    ProcessPaymentResponse processPaymentByCard(String accessToken,
                                                String requestId, String csc) throws IOException,
            InsufficientScopeException, InvalidTokenException;

    /**
     * Метод подтверждения платежа c оплатой с кошелька пользователя.
     *
     * @param accessToken токен авторизации пользователя
     * @param requestId   идентификатор запроса (requestId), полученный с
     *                    помощью методов requestPayment*.
     * @return возвращает экземпляр класса {@link ru.yandex.money.api.response.ProcessPaymentResponse}
     * @throws java.io.IOException          ошибка связи с сервером Яндекс.Денег
     * @throws ru.yandex.money.api.InsufficientScopeException   запрошена операция, на которую у
     *                                      токена нет прав.
     * @throws ru.yandex.money.api.InvalidTokenException        указан несуществующий, просроченный, или отозванный токен.
     * @throws ru.yandex.money.api.InternalServerErrorException техническая ошибка сервера Яндекс.Денег
     */
    ProcessPaymentResponse processPaymentByWallet(String accessToken,
                                                  String requestId) throws IOException, InsufficientScopeException,
            InvalidTokenException;
}
