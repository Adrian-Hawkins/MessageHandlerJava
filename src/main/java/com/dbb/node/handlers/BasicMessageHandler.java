package com.dbb.node.handlers;

import com.dbb.node.base.SagaBase;
import com.dbb.node.messages.ImageMessage;
import com.dbb.node.annotations.IHandleMessages;
import com.dbb.node.annotations.IHandle;
import com.dbb.node.messages.TextMessage;

@IHandleMessages()
public class BasicMessageHandler extends SagaBase {

    @IHandle(MessageType = ImageMessage.class)
    public void handleTextMessage(TextMessage message) {
        someOtherMethod();
    }


    public void someOtherMethod() {
        log("This is not a message handler");
    }
}