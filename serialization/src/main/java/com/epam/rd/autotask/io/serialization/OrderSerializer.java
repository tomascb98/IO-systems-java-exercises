package com.epam.rd.autotask.io.serialization;

import java.io.*;
import java.util.Map;

public class OrderSerializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // you can add your code here for personal purpose
    }

    /**
     * Serializes Order to a given OutputStream
     */
    public static void serializeOrder(Order order, OutputStream sink) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(sink);
        out.writeLong(order.getId());
        out.writeObject(order.getItems());
    }


    /**
     * Deserializes Order from a given InputStream
     */
    public static Order deserializeOrder(InputStream sink) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(sink);
        return new Order(
                in.readLong(),
                (Map<Item, Integer>) in.readObject()
        );
    }
}
