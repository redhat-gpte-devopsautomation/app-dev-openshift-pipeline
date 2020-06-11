package com.redhat.rotation.market.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.util.Objects;

public class Fruit extends PanacheMongoEntity {
    private long id;
    private String name;
    private String description;
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return id == fruit.id &&
                quantity == fruit.quantity &&
                Objects.equals(name, fruit.name) &&
                Objects.equals(description, fruit.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, quantity);
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
