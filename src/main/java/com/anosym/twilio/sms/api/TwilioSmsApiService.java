/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
