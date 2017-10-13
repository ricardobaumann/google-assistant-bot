package lex.response;

public class ResponseCard {
    int version;
    String contentType;
    Attachments[] genericAttachments;

    public ResponseCard() {
    }

    public ResponseCard(final int version, final String contentType, final Attachments[] genericAttachments) {
        this.version = version;
        this.contentType = contentType;
        this.genericAttachments = genericAttachments;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public Attachments[] getGenericAttachments() {
        return genericAttachments;
    }

    public void setGenericAttachments(final Attachments[] genericAttachments) {
        this.genericAttachments = genericAttachments;
    }
}
