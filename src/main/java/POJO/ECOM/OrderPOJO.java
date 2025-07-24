/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:19-07-2025
 * Time:23:20
 */
package POJO.ECOM;

import java.util.List;

public class OrderPOJO {

    private List<OrderDetailsPOJO> orders;

    public List<OrderDetailsPOJO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetailsPOJO> orders) {
        this.orders = orders;
    }
}
