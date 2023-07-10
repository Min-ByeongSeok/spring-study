package com.example.DependencyInjectionPractice;

public class Store {

    private Product product;

    public Store(Product product) {
        this.product = product;
    }

    Store store = new Store(product);
}
