package com.Utils;

import java.io.*;

public class Serialize {

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
//            System.out.println(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                System.out.println("error2");
//                logger.error("{}", e);
            }
        }
        return bytes;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
//            logger.error("{}", e);
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
//                logger.error("{}", e);
            }
        }
        return null;
    }
}
