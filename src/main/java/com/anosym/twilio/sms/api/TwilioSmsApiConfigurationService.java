/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    boolean isSimulatesuccess();
}
