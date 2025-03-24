package com.dbb.node.base;

import java.io.Serializable;
import java.util.UUID;

public abstract class MessageBase implements Serializable {

    private String guid;
    private String queueName;

    protected MessageBase() {
        this.guid = UUID.randomUUID().toString();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
