// (c)2016 Flipboard Inc, All Rights Reserved.

package com.jiajun.demo.model.entities;

public class Item {
    public String description;
    public String imageUrl;

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
