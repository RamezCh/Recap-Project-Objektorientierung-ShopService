import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product mit der Id: " + productId + " konnte nicht gefunden werden!"));

            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepo.getOrders();
        if (orders == null) {
            return Collections.emptyList();
        }
        return orders.stream()
                .filter(order -> order.status().equals(status))
                .collect(Collectors.toList());
    }

    public Order updateOrder(String orderId, OrderStatus newStatus) {
        try {
            Order existingOrder = orderRepo.getOrderById(orderId);
            Order updatedOrder = existingOrder.withStatus(newStatus);
            return orderRepo.updateOrder(updatedOrder);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Order with ID " + orderId + " does not exist.", e);
        }
    }
}
