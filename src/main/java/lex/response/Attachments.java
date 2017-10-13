package lex.response;

public class Attachments {
    String title;
    String subTitle;
    String imageUrl;
    String attachmentLinkUrl;
    Buttons[] buttons;

    public Attachments() {
    }

    public Attachments(final String title, final String subTitle, final Buttons[] buttons) {
        this.title = title;
        this.subTitle = subTitle;
        this.buttons = buttons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAttachmentLinkUrl() {
        return attachmentLinkUrl;
    }

    public void setAttachmentLinkUrl(final String attachmentLinkUrl) {
        this.attachmentLinkUrl = attachmentLinkUrl;
    }

    public Buttons[] getButtons() {
        return buttons;
    }

    public void setButtons(final Buttons[] buttons) {
        this.buttons = buttons;
    }
}
