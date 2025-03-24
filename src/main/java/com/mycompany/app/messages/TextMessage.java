package com.mycompany.app.messages;
import com.mycompany.app.base.MessageBase;

public class TextMessage extends MessageBase {
    private String content;

    public TextMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}