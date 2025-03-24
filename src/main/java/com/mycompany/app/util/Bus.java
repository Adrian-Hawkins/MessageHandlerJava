package com.mycompany.app.util;

import com.mycompany.app.base.MessageBase;

import java.awt.*;
import java.io.*;
import java.util.Base64;

public class Bus {
    String innner = "jsdjfsdklfj";
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
        String QueueName = msg.getClass().getSimpleName()
                .replaceAll("([A-Z])", "_$1").toUpperCase().replaceFirst("^_", "");
        try {
            String content = this.serialize(msg);
            System.out.println(this.innner);
        } catch (Exception e) {

        }
    }

    public void Test() {
        System.out.println("TESTing bus");
    }
}
