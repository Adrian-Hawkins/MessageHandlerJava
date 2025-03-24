package com.dbb.node.util;

import com.dbb.node.logging.CorrelationContext;
import com.dbb.node.base.MessageBase;

import java.io.*;
import java.util.Base64;

public class Bus {
    public String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public Object deserialize(String str) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(str);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }

    public void Send(MessageBase msg) {
        // Most if not all of thi will have to be moved when rabbit is introduced
        String QueueName = msg.getClass().getSimpleName()
                .replaceAll("([A-Z])", "_$1").toUpperCase().replaceFirst("^_", "");
        CorrelationContext.setCorrelationGuid(msg.getGuid());
        try {
            String content = this.serialize(msg);
            Object obj = this.deserialize(content);
            MessageHandlerScanner.callHandler(QueueName, (MessageBase) obj);
        } catch (Exception e) {
            // idk
        } finally {
            CorrelationContext.clear();
        }
    }
}
