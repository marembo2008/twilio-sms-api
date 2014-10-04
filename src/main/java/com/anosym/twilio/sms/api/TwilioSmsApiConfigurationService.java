package com.anosym.twilio.sms.api;

/**
 *
 * @author marembo
 */
public interface TwilioSmsApiConfigurationService {

    String getAccountSid();

    String getAuthToken();

    String getFromPhoneNumber();

    /**
     * If true, we always return transaction as success.
     *
     * @return
     */
    boolean isSimulateSuccess();
}
