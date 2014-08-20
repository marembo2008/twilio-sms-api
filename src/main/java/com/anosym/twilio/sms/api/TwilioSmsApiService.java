package com.anosym.twilio.sms.api;

import com.twilio.sdk.TwilioRestException;

/**
 *
 * @author marembo
 */
public interface TwilioSmsApiService {

    boolean sendSms(String number, String msg) throws TwilioRestException;

    SmsResult sendSmsForResult(String number, String msg) throws TwilioRestException;
}
