package com.anosym.twilio.sms.api;

/**
 *
 * @author marembo
 */
public class SmsResult {

    private final boolean success;
    private final String message;

    public SmsResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
