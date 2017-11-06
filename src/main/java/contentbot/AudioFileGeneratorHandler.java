package contentbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import contentbot.service.AudioFileService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AudioFileGeneratorHandler implements RequestStreamHandler {
    @Override
    public void handleRequest(final InputStream input, final OutputStream output, final Context context) throws IOException {
        ContentBotApplication.getApplicationContext().getBean(AudioFileService.class).generateAudioFile();
    }
}
