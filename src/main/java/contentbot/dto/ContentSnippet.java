package contentbot.dto;

public class ContentSnippet {
    private final String topic;
    private final String intro;
    private final String summary;
    private final String url;
    private final String id;

    public ContentSnippet(final String topic, final String intro, final String summary, final String url, final String id) {
        this.topic = topic;
        this.intro = intro;
        this.summary = summary;
        this.url = url;
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public String getIntro() {
        return intro;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }
}
