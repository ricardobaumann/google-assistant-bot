package lex.response;

import java.util.HashMap;
import java.util.Map;

public class LexResponse {
    Map<String, String> sessionAttributes;
    private DialogAction dialogAction;

    public LexResponse() {
    }

    public LexResponse(final DialogAction dialogAction) {
        this.dialogAction = dialogAction;
    }

    public LexResponse(final DialogAction dialogAction, final Map<String, String> sessionAttributes) {
        this(dialogAction);
        this.sessionAttributes = sessionAttributes;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    public void setDialogAction(final DialogAction dialogAction) {
        this.dialogAction = dialogAction;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(final Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void addAttribute(final String k, final String v) {
        if (sessionAttributes == null) {
            sessionAttributes = new HashMap<>();
        }

        sessionAttributes.put(k, v);
    }

    public void clearAttributes() {
        if (sessionAttributes != null) {
            sessionAttributes.clear();
        }
    }
}
