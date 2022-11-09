package edu.upc.dsa.minim.Domain.Repository;

import edu.upc.dsa.minim.Domain.Entity.Order;
import edu.upc.dsa.minim.Domain.Entity.Product;

import java.util.List;

public interface ProductManager {
    //BOTH
    public List<Order> ordersByUser(String userId);
    public int size();

    //Product
    public List<Product> productsByPrice();
    public List<Product> productsBySales();
    public void addProduct(String productId, String name, double price);
    public Product getProduct(String productId);
    public int numProducts();
    public int numSales(String b001);

    //User
    public void addUser(String s, String name, String surname);
    public int numUsers();

    //Order
    public void addOrder(Order order);
    public Order processOrder();
    public int numOrders();
}