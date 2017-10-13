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

package contentbot.verifier;

import contentbot.domain.LexResponse;
import contentbot.domain.action.DialogActionWithDetails;

/**
 * A class which performs some validations on the Lex Response and logs
 * warning for some common issues with responses.
 *
 * @author Mark Borner
 */
public class LexResponseVerifier extends AbstractVerifier<LexResponse> {

    private final MessageVerifier messageVerifier = new MessageVerifier();
    private final ResponseCardVerifier responseCardVerifier = new ResponseCardVerifier();

    @Override
    public void verify(final LexResponse lexResponse) {
        if (lexResponse.getDialogAction() instanceof DialogActionWithDetails) {
            final DialogActionWithDetails dialogActionWithDetails = (DialogActionWithDetails) lexResponse.getDialogAction();
            if (dialogActionWithDetails.getResponseCard() != null) {
                responseCardVerifier.verify(dialogActionWithDetails.getResponseCard());
            }
            if (dialogActionWithDetails.getMessage() != null) {
                messageVerifier.verify(dialogActionWithDetails.getMessage());
            }
        }
    }

}
