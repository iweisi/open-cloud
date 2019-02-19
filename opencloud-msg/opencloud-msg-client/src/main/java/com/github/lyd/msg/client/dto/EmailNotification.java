package com.github.lyd.msg.client.dto;

/**
 * @author woodev
 */
public class EmailNotification extends Notification{

    private static final long serialVersionUID = 3975277953501451835L;
    private String title;

    private String content;

    private String to;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
