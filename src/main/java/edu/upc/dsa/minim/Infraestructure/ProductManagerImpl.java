package edu.upc.dsa.minim.Infraestructure;
import edu.upc.dsa.minim.Domain.Entity.Order;
import edu.upc.dsa.minim.Domain.Entity.Product;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Entity.VO.LP;
import edu.upc.dsa.minim.Domain.Repository.ProductManager;
import org.apache.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
    private static ProductManager instance;
    List<Product> products;
    Map<String, User> users;
    Queue<Order> orders;

    final static Logger logger = Logger.getLogger(ProductManagerImpl.class);

    public static ProductManager getInstance() {
        if (instance==null) instance = new ProductManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.products.size();
        logger.info("size " + ret);

        return ret;
    }
    public ProductManagerImpl(){
        this.products = new ArrayList<>();
        this.users = new HashMap<>();
        this.orders = new LinkedList<>();
    }
//PRODUCTS
    @Override
    public List<Product> productsByPrice() {
        this.products.sort((Product p1,Product p2)->Double.compare(p1.getPrice(),p2.getPrice()));
        return this.products;
    }

    @Override
    public List<Product> productsBySales() {
        this.products.sort((Product p2, Product p1)->(p1.getNumSales() - p2.getNumSales()));
        return this.products;
    }

    @Override
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public Order processOrder() {
        Order order = this.orders.poll();
        executeProcess(order);
        return order;
    }

    @Override
    public List<Order> ordersByUser(String userId) {
        return this.users.get(userId).getProcessedOrders();
    }

    @Override
    public void addUser(String s, String name, String surname) {
        this.users.put(s, new User(s, name, surname));
    }

    @Override
    public void addProduct(String productId, String name, double price) {
        if (!getProduct(productId).isNull()){
            return;
        }
        this.products.add(new Product(productId, name, price, 0));
    }

    @Override
    public Product getProduct(String productId) {
        Product product = new Product("","",0,0);
        for(Product p : this.products){
            if(Objects.equals(p.getProductId(), productId)){
                product = p;
            }
        }
        return product;
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numProducts() {
        return products.size();
    }

    @Override
    public int numOrders() {
        return this.orders.size();
    }

    @Override
    public int numSales(String b001) {
        return this.getProduct(b001).getNumSales();
    }

    private void executeProcess(Order order) {
        for(LP element : order.getElements()) {
            Product product = this.getProduct(element.getProduct());
            int index = products.indexOf(product);
            product.sold(element.getQuantity());
            products.set(index, product);
        }
        this.users.get(order.getUserId()).addProcessedOrder(order);
    }
}
