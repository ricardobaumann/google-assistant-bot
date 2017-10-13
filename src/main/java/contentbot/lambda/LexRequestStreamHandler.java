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

package contentbot.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import contentbot.LexRequestHandler;
import contentbot.domain.Bot;
import contentbot.domain.LexRequest;
import contentbot.domain.LexResponse;
import contentbot.verifier.LexResponseVerifier;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A Lambda Request Stream Handler for processing a request from Lex
 *
 * @author Mark Borner
 */
public abstract class LexRequestStreamHandler implements RequestStreamHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final LexResponseVerifier lexResponseVerifier = new LexResponseVerifier();
    private final LexRequestHandler lexRequestHandler;
    private final String botName;

    public LexRequestStreamHandler(final String botName, final LexRequestHandler lexRequestHandler) {
        if (botName == null) {
            throw new IllegalArgumentException("Bot name should not be null");
        }
        if (lexRequestHandler == null) {
            throw new IllegalArgumentException("LexRequestHandler should not be null");
        }
        this.lexRequestHandler = lexRequestHandler;
        this.botName = botName;
    }

    @Override
    public void handleRequest(final InputStream inputStream, final OutputStream outputStream, final Context context) throws IOException {
        final byte[] requestBytes = IOUtils.toByteArray(inputStream);
        if (logger.isDebugEnabled()) {
            logger.debug("Request Json:\n {}", new String(requestBytes));
        }
        final byte[] responseBytes;
        try {
            final LexRequest lexRequest = LexRequest.fromJson(requestBytes);
            final String requestBotName = nullSafeGetBotName(lexRequest);
            if (!botName.equals(requestBotName)) {
                throw new RuntimeException("This bot cannot handle request for bot named: " + requestBotName);
            }
            final Map<String, String> sessionAttributes = new HashMap<>(lexRequest.getSessionAttributes());
            final LexResponse lexResponse = lexRequestHandler.handleRequest(lexRequest, sessionAttributes);
            lexResponse.setSessionAttributes(sessionAttributes);
            lexResponseVerifier.verify(lexResponse);
            responseBytes = lexResponse.toJson();
            if (logger.isDebugEnabled()) {
                logger.debug("Response Json:\n {}", new String(responseBytes));
            }
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
        outputStream.write(responseBytes);
    }

    private String nullSafeGetBotName(final LexRequest lexRequest) {
        final Bot bot = lexRequest.getBot();
        if (bot == null) {
            return null;
        } else {
            return bot.getName();
        }
    }

}
