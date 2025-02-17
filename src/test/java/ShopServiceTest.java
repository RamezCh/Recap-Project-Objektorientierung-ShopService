import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING, Instant.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrderByStatus_returnEmptyList(){
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        // WHEN
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.COMPLETED);
        // THEN
        assertEquals(Collections.emptyList(), orders);
    }

    @Test
    void getOrderByStatus_returnList(){
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        // WHEN
        List<Order> orders = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        // THEN
        assertNotEquals(Collections.emptyList(), orders);
        assertNotNull(orders);
    }

    @Test
    void updateOrder_returnExceptionIllegalArgument(){
        //GIVEN
        ShopService shopService = new ShopService();
        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> shopService.updateOrder("2", OrderStatus.COMPLETED));
    }

    @Test
    void updateOrder_returnUpdatedOrder(){
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        Order added = shopService.addOrder(productsIds);
        // WHEN
        Order expected = added.withStatus(OrderStatus.IN_DELIVERY);
        Order actual = shopService.updateOrder(added.id(),OrderStatus.IN_DELIVERY);
        // THEN
        assertEquals(expected, actual);
    }
}
