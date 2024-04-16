package com.epam.rd.autotask.io.serialization;

import java.io.*;
import java.util.Objects;

public class DoubleItemCharacteristic extends ItemCharacteristic implements Serializable {
    protected double value;

    public DoubleItemCharacteristic(Long id, String name, String type, double value) {
        super(id, name, type);
        this.value = value;
    }



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleItemCharacteristic that)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(getValue(), that.getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }

    @Override
    public String toString() {
        return "DoubleItemCharacteristic{" +
                "value=" + value +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        System.out.println("doublewrite");
        out.writeLong(this.id);
        out.writeUTF(this.name);
        out.writeUTF(this.type);
        out.writeDouble(this.value);
    }
    @Serial
    private void readObject(ObjectInputStream in) throws IOException {
        System.out.println("doubleread");
        setId(in.readLong());
        this.name=in.readUTF();
        this.type=in.readUTF();
        this.value=in.readDouble();
    }
}
