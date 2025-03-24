package com.mycompany.app.base;

import java.io.Serializable;
import java.util.UUID;

public abstract class MessageBase implements Serializable {

    private String guid;

    protected MessageBase() {
        this.guid = UUID.randomUUID().toString();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
