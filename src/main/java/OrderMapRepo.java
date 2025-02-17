import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepo{
    private Map<String, Order> orders = new HashMap<>();

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrderById(String id) {
        return orders.get(id);
    }

    @Override
    public Order addOrder(Order newOrder) {
        orders.put(newOrder.id(), newOrder);
        return newOrder;
    }

    @Override
    public void removeOrder(String id) {
        orders.remove(id);
    }

    @Override
    public Order updateOrder(Order order) {
        if (orders.containsKey(order.id())) {
            orders.put(order.id(), order);
            return order;
        } else {
            throw new IllegalArgumentException("Order mit der Id: " + order.id() + " konnte nicht gefunden werden!");
        }
    }
}
