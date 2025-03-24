package com.dbb.node.handlers;

//import com.example.annotations.HandleMessages;
import com.dbb.node.base.SagaBase;
import com.dbb.node.annotations.IHandleMessages;
import com.dbb.node.annotations.IHandle;
import com.dbb.node.messages.TextMessage;

@IHandleMessages()
public class GetRoleHandler extends SagaBase {

//    @Injectable()
//    private Bus bus;
    private String innertest = "something in getRole handler";

    @IHandle(MessageType = TextMessage.class)
    public void some(TextMessage message) {
        log("Processing text message: " + this.innertest + message.getContent());
    }

    public void someOtherMethod() {
        log("This is not a message handler");
    }
}