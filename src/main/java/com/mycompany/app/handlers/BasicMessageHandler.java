package com.mycompany.app.handlers;

//import com.example.annotations.HandleMessages;
import com.mycompany.app.annotations.IHandleMessages;
import com.mycompany.app.annotations.IHandle;
import com.mycompany.app.annotations.Injectable;
import com.mycompany.app.base.SagaBase;
import com.mycompany.app.messages.ImageMessage;
import com.mycompany.app.messages.TextMessage;
import com.mycompany.app.util.Bus;

@IHandleMessages(description = "Handles text and image messages")
public class BasicMessageHandler extends SagaBase {



    private String name = "Something";

    @IHandle(MessageType = ImageMessage.class)
    public void handleTextMessage(TextMessage message) {
        someOtherMethod();
    }


    public void someOtherMethod() {
        System.out.println("This is not a message handler");
    }
}