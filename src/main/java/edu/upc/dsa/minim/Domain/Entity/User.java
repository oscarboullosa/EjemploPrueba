package edu.upc.dsa.minim.Domain.Entity;
import java.util.LinkedList;
import java.util.List;
public class User {
        String userId;

        String userName;

        String userSurname;

        List<Order> processedOrders;


        public User(String userId, String userName, String userSurname){
            this.userId = userId;
            this.userName = userName;
            this.userSurname = userSurname;
            this.processedOrders = new LinkedList<>();
        }
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserSurname() {
            return userSurname;
        }

        public void setUserSurname(String userSurname) {
            this.userSurname = userSurname;
        }

        public void addProcessedOrder(Order order) {
            this.processedOrders.add(order);
        }

        public List<Order> getProcessedOrders() {
            return this.processedOrders;
        }
    }
