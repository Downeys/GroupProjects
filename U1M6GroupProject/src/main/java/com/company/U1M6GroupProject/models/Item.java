package com.company.U1M6GroupProject.models;

import java.util.Objects;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private double dailyRate;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && Double.compare(item.dailyRate, dailyRate) == 0 && Objects.equals(name, item.name) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, description, dailyRate);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dailyRate=" + dailyRate +
                '}';
    }
}
