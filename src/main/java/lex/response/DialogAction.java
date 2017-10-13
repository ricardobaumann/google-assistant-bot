package lex.response;

import java.util.HashMap;
import java.util.Map;

public class DialogAction {
    private String type;
    private String fulfillmentState;
    private Message message;
    private String intentName;
    private String slotToElicit;
    private Map<String, String> slots;
    private ResponseCard responseCard;

    public static final String FULFILLMENT_STATE_FULFILLED = "Fulfilled";
    public static final String FULFILLMENT_STATE_FAILED = "Failed";

    public static final String ELICIT_INTENT_TYPE = "ElicitIntent";
    public static final String ELICIT_SLOT_TYPE = "ElicitSlot";
    public static final String CONFIRM_TYPE = "ConfirmIntent";
    public static final String DELEGATE_TYPE = "Delegate";
    public static final String CLOSE_TYPE = "Close";

    public DialogAction() {
    }

    public DialogAction(final String type, final String fulfillmentState, final Message message) {
        this.type = type;
        this.fulfillmentState = fulfillmentState;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getFulfillmentState() {
        return fulfillmentState;
    }

    public void setFulfillmentState(final String fulfillmentState) {
        this.fulfillmentState = fulfillmentState;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(final Message message) {
        this.message = message;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(final String intentName) {
        this.intentName = intentName;
    }

    public String getSlotToElicit() {
        return slotToElicit;
    }

    public void setSlotToElicit(final String slotToElicit) {
        this.slotToElicit = slotToElicit;
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public void addSlots(final String k, final String v) {
        if (slots == null) {
            slots = new HashMap<>();
        }

        slots.put(k, v);
    }

    public void setSlots(final Map<String, String> slots) {
        this.slots = slots;
    }

    public ResponseCard getResponseCard() {
        return responseCard;
    }

    public void setResponseCard(final ResponseCard responseCard) {
        this.responseCard = responseCard;
    }
}
