/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.twilio.sms.api.impl;

import com.anosym.twilio.sms.api.SmsResult;
import com.anosym.twilio.sms.api.TwilioSmsApiConfigurationService;
import com.anosym.twilio.sms.api.TwilioSmsApiService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author marembo
 */
public class TwilioSmsApiServiceImpl implements TwilioSmsApiService {

    private static final Logger LOG = Logger.getLogger(TwilioSmsApiServiceImpl.class.getName());

    @Inject
    private TwilioSmsApiConfigurationService smsApiConfigurationService;

    /**
     * For JSE environment.
     *
     * @param smsApiConfigurationService
     */
    public TwilioSmsApiServiceImpl(TwilioSmsApiConfigurationService smsApiConfigurationService) {
        this.smsApiConfigurationService = smsApiConfigurationService;
    }

    public TwilioSmsApiServiceImpl() {
    }

    @Override
    public boolean sendSms(String number, String msg) throws TwilioRestException {
        return sendSmsForResult(number, msg).isSuccess();
    }

    @Override
    public SmsResult sendSmsForResult(String number, String msg) throws TwilioRestException {
        /**
         * The number must start with +
         */
        if (!number.startsWith("+")) {
            number += "+";
        }
        //if the message is more than 160 character, truncate it!!
        msg = msg.length() > 160 ? msg.substring(0, 160) : msg;
        TwilioRestClient client = new TwilioRestClient(
                smsApiConfigurationService.getAccountSid(), smsApiConfigurationService.getAuthToken());
        Map<String, String> params = new HashMap<>();
        params.put("Body", msg);
        params.put("To", number);
        params.put("From", smsApiConfigurationService.getFromPhoneNumber());
        SmsFactory messageFactory = client.getAccount().getSmsFactory();
        Sms message = null;
        try {
            message = messageFactory.create(params);
            LOG.log(Level.INFO, "Sent SMS: {0}", number + ", " + msg + ", " + message);
            return new SmsResult(true, message.getStatus());
        } catch (TwilioRestException e) {
            LOG.log(Level.SEVERE, "Send SMS FAIL: " + number + ", " + msg + ", " + message, e);
            return new SmsResult(false, e.getErrorMessage() + ": " + e.getMoreInfo());
        }
    }
}