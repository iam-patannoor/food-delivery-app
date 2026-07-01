package com.example.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/orders")
public class FoodDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }

    @PostMapping("/place")
    public String placeOrder(@RequestBody Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return "Order failed: No items selected.";
        }
        
        double total = calculateTotal(order.getItems());
        
        if ("VIP".equals(order.getCustomerType())) {
            return processVIPOrder(order, total);
        }
        
        return "Order placed successfully! Total: $" + total;
    }

    public double calculateTotal(List<Double> prices) {
        double total = 0.0;
        for (int i = 0; i < prices.size(); i++) {
            total = total + prices.get(i);
        }
        return total;
    }

    public String processVIPOrder(Order order, double total) {
        double vipTotal = total * 0.90;
        return "VIP Order placed! Total after discount: $" + vipTotal;
    }
}

class Order {
    private List<Double> items;
    private String customerType;

    public List<Double> getItems() { return items; }
    public void setItems(List<Double> items) { this.items = items; }
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }
}
