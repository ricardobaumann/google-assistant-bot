package bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "audio-file")
public class AudioFileProperties {
    private String targetS3Bucket;

    private String region;

    private String fileName;

    public String getTargetS3Bucket() {
        return targetS3Bucket;
    }

    public void setTargetS3Bucket(final String targetS3Bucket) {
        this.targetS3Bucket = targetS3Bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
}
