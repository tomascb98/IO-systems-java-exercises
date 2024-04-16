package com.epam.rd.autotask.io.serialization;


import java.util.Objects;

public abstract class ItemCharacteristic {
    protected Long id;
    protected String name;
    protected String type;

    public ItemCharacteristic(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public ItemCharacteristic() {
        System.out.println("Entré");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemCharacteristic that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getType());
    }



}
