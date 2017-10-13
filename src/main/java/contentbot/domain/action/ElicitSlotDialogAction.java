/*
 *    Copyright 2017 Mark Borner
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package contentbot.domain.action;


import contentbot.domain.Message;
import contentbot.domain.ResponseCard;

import java.util.Map;

/**
 * The Elicit Slot Dialog Action
 *
 * @author Mark Borner
 */
public class ElicitSlotDialogAction extends DialogActionWithDetails {

    private final String intentName;
    private final Map<String, String> slots;
    private final String slotToElicit;

    public ElicitSlotDialogAction(final String slotToElicit, final String intentName, final Map<String, String> slots) {
        super("ElicitSlot");
        if (intentName == null) {
            throw new IllegalArgumentException("Intent Name should not be null");
        }
        if (slots == null) {
            throw new IllegalArgumentException("Slots should not be null");
        }
        this.intentName = intentName;
        this.slotToElicit = slotToElicit;
        this.slots = slots;
    }

    public ElicitSlotDialogAction(final String slotToElicit, final String intentName, final Map<String, String> slots, final Message message) {
        this(slotToElicit, intentName, slots);
        setMessage(message);
    }

    public ElicitSlotDialogAction(final String slotToElicit, final String intentName, final Map<String, String> slots, final Message message, final ResponseCard responseCard) {
        this(slotToElicit, intentName, slots, message);
        setResponseCard(responseCard);
    }

    public ElicitSlotDialogAction(final String slotToElicit, final String intentName, final Map<String, String> slots, final ResponseCard responseCard) {
        this(slotToElicit, intentName, slots);
        setResponseCard(responseCard);
    }

    public String getIntentName() {
        return intentName;
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public String getSlotToElicit() {
        return slotToElicit;
    }

}
