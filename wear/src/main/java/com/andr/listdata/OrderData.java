package com.andr.listdata;

import java.lang.String; /**
 * Created by CHANDRASAIMOHAN on 9/19/2015.
 */
public class OrderData {
    private String orderId;
    private String orderCost;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }
}
