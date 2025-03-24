package com.mycompany.app.handlers;

//import com.example.annotations.HandleMessages;
import com.mycompany.app.annotations.IHandleMessages;
import com.mycompany.app.annotations.IHandle;
import com.mycompany.app.annotations.Injectable;
import com.mycompany.app.base.SagaBase;
import com.mycompany.app.messages.TextMessage;
import com.mycompany.app.util.Bus;

@IHandleMessages()
public class GetRoleHandler extends SagaBase {

    @Injectable()
    private Bus bus;
    private String innertest = "something in getRole handler";
//    @IHandle(MessageType = TextMessage.class)
//    public void handleTextMessage(TextMessage message) {
//        System.out.println("Processing text message: " + message.getContent());
//    }

    @IHandle(MessageType = TextMessage.class)
    public void some(TextMessage message) {
        this.bus.Test();
        System.out.println("Processing text message: " + this.innertest);
    }

//    @IHandle(messageClass = ImageMessage.class)
//    public void handleImageMessage(ImageMessage message) {
//        System.out.println("Processing image message: " + message.getImageUrl());
//    }

    public void someOtherMethod() {
        System.out.println("This is not a message handler");
    }
}