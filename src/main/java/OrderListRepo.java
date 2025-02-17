import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo{
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Order addOrder(Order newOrder) {
        orders.add(newOrder);
        return newOrder;
    }

    public void removeOrder(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                orders.remove(order);
                return;
            }
        }
    }

    @Override
    public Order updateOrder(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).id().equals(order.id())) {
                orders.set(i, order);
                return order;
            }
        }
        throw new IllegalArgumentException("Order mit der Id: " + order.id() + " konnte nicht gefunden werden!");
    }
}
