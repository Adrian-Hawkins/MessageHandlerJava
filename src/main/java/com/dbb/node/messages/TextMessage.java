package com.dbb.node.messages;
import com.dbb.node.base.MessageBase;

public class TextMessage extends MessageBase {
    private String content;

    public TextMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}