package edu.upc.dsa.minim.Domain.Entity.VO;

public class LP {
    int quantity;
    String product;

    public LP(int quantity, String product){
        this.quantity = quantity;
        this.product = product;
    }

    public String getProduct(){
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
