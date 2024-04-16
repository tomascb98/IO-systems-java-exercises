package com.epam.rd.autotask.io.serialization;

import java.io.*;
import java.util.Objects;

public class IntItemCharacteristic extends ItemCharacteristic implements Serializable {

    protected int value;


    public IntItemCharacteristic(Long id, String name, String type, int value) {
        super(id, name, type);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntItemCharacteristic that)) return false;
        if (!super.equals(o)) return false;
        return getValue() == that.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }

    @Override
    public String toString() {
        return "IntItemCharacteristic{" +
                "value=" + value +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(this.id);
        out.writeUTF(this.name);
        out.writeUTF(this.type);
        out.writeInt(this.value);
    }
    @Serial
    private void readObject(ObjectInputStream in) throws IOException {
        this.id=in.readLong();
        this.name=in.readUTF();
        this.type=in.readUTF();
        this.value=in.readInt();
    }
}
