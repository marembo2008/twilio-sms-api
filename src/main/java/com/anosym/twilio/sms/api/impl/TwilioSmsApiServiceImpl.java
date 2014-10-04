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

    private final TwilioSmsApiConfigurationService smsApiConfigurationService;

    /**
     * @param smsApiConfigurationService
     */
    @Inject
    public TwilioSmsApiServiceImpl(final TwilioSmsApiConfigurationService smsApiConfigurationService) {
        this.smsApiConfigurationService = smsApiConfigurationService;
    }

    @Override
    public boolean sendSms(String number, String msg) throws TwilioRestException {
        return sendSmsForResult(number, msg).isSuccess();
    }

    @Override
    public SmsResult sendSmsForResult(String number, String msg) throws TwilioRestException {
        if (this.smsApiConfigurationService.isSimulateSuccess()) {
            final String info = "Successfully sent the following sms: " + msg;
            LOG.info(info);
            return new SmsResult(true, info);
        }
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
            LOG.log(Level.SEVERE,
                    "Twilio SMS Error Code: {0}, Error Message: {1}, More Information: {2}",
                    new Object[]{e.getErrorCode(), e.getErrorMessage(), e.getMoreInfo()});
            LOG.log(Level.SEVERE, "Send SMS FAIL: " + number + ", " + msg + ", " + message, e);
            return new SmsResult(false, "Technical error sending sms");
        }
    }
}
