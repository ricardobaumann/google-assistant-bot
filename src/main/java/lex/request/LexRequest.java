package lex.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class LexRequest {
    String messageVersion;
    String invocationSource;
    String userId;
    Map<String, String> sessionAttributes;
    Bot bot;
    String outputDialogMode;
    Intent currentIntent;
    String confirmationStatus;
    String inputTranscript;

    public static final String INVOCATION_SOURCE_DIALOG_CODE_HOOK = "DialogCodeHook";
    public static final String INVOCATION_SOURCE_FULFILLMENT_CODE_HOOK = "FulfillmentCodeHook";

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(final String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public String getInvocationSource() {
        return invocationSource;
    }

    public void setInvocationSource(final String invocationSource) {
        this.invocationSource = invocationSource;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(final Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(final Bot bot) {
        this.bot = bot;
    }

    public String getOutputDialogMode() {
        return outputDialogMode;
    }

    public void setOutputDialogMode(final String outputDialogMode) {
        this.outputDialogMode = outputDialogMode;
    }

    public Intent getCurrentIntent() {
        return currentIntent;
    }

    public void setCurrentIntent(final Intent currentIntent) {
        this.currentIntent = currentIntent;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(final String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getInputTranscript() {
        return inputTranscript;
    }

    public void setInputTranscript(final String inputTranscript) {
        this.inputTranscript = inputTranscript;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
