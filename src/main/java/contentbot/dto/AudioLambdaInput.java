package contentbot.dto;

public class AudioLambdaInput {
    private final String ssml;
    private final String bucketName;
    private final String fileName;
    private final String region;

    public AudioLambdaInput(final String ssml, final String bucketName, final String fileName, final String region) {

        this.ssml = ssml;
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.region = region;
    }

    public String getSsml() {
        return ssml;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRegion() {
        return region;
    }
}
