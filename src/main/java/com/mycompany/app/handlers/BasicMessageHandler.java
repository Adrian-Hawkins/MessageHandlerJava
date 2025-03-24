package com.mycompany.app.handlers;

import com.mycompany.app.annotations.IHandleMessages;
import com.mycompany.app.annotations.IHandle;
import com.mycompany.app.annotations.Injectable;
import com.mycompany.app.base.SagaBase;
import com.mycompany.app.messages.ImageMessage;
import com.mycompany.app.messages.TextMessage;
import com.mycompany.app.util.Bus;

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