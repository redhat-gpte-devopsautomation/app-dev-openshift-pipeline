package com.redhat.rotation.market.utils;

import com.redhat.rotation.market.model.Fruit;

public final class MockDataUtils {
    public static Fruit createFruit(String name, String description, int quantity) {
        Fruit newFruit = new Fruit();
        newFruit.setQuantity(quantity);
        newFruit.setName(name);
        newFruit.setDescription(description);
        return newFruit;
    }

    public static Fruit createFruit(long fruitId, String name, String description, int quantity) {
        Fruit fruit = createFruit(name, description, quantity);
        fruit.setId(fruitId);
        return fruit;
    }
}
