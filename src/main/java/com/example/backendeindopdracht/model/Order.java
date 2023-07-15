package com.example.backendeindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Invoice invoice;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;
    private String customerName;
    private double unitPrice;
    private int quantity;
    private BigDecimal totalAmount;


    public void addOrderLine(OrderLine orderLine) {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }



    /*public void setQuantity(double q) {
        if q < 0 {

        }
        this.quantity = q;
    }



public void updateStock() {
    for (products) {

    }
}



    public BigDecimal calculateTotalPrice() {
        for (Product product : products) {
            totalAmount = totalAmount + products.unitPrice;
        }
        return totalAmount;
    }

    public BigDecimal calculateTotal(){
		BigDecimal total = BigDecimal.ZERO;
		for (LineItem lineItem : this.getLinesItems()) {
			total.add(lineItem.getPrice().multiply(new BigDecimal(lineItem.getQuantity())));
		}
		return total;




*/
}