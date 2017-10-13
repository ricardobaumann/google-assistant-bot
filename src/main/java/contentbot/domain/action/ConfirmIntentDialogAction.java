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

import java.util.Collections;
import java.util.Map;

/**
 * The Confirm Intent Dialog Action
 *
 * @author Mark Borner
 */
public class ConfirmIntentDialogAction extends DialogActionWithDetails {

    private final String intentName;
    private final Map<String, String> slots;

    public ConfirmIntentDialogAction(final String intentName, final Map<String, String> slots) {
        super("ConfirmIntent");
        if (intentName == null) {
            throw new IllegalArgumentException("Intent Name should not be null");
        }
        if (slots == null) {
            throw new IllegalArgumentException("Slots should not be null");
        }
        this.intentName = intentName;
        this.slots = slots;
    }

    public ConfirmIntentDialogAction(final String intentName, final Map<String, String> slots, final Message message) {
        this(intentName, slots);
        setMessage(message);
    }

    public ConfirmIntentDialogAction(final String intentName, final Map<String, String> slots, final Message message, final ResponseCard responseCard) {
        this(intentName, slots, message);
        setResponseCard(responseCard);
    }

    public ConfirmIntentDialogAction(final String intentName, final Map<String, String> slots, final ResponseCard responseCard) {
        this(intentName, slots);
        setResponseCard(responseCard);
    }

    public String getIntentName() {
        return intentName;
    }

    public Map<String, String> getSlots() {
        return Collections.unmodifiableMap(slots);
    }

}
