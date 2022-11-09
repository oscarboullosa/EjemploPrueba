package edu.upc.dsa.minim.Domain.Entity;

import edu.upc.dsa.minim.Domain.Entity.VO.LP;

import java.util.*;

public class Order {
    String userId;
    LinkedList<LP> elements;

    public Order(String userId){
        this.userId = userId;
        this.elements = new LinkedList<>();
    }

    public LinkedList<LP> getElements(){
        return this.elements;
    }

    public void setElements(LinkedList<LP> elements) {
        this.elements = elements;
    }
    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addLP(int i, String b001) {
        elements.add(new LP(i, b001));
    }

    public LP getLP(int i) {
        return elements.get(i);
    }
}
