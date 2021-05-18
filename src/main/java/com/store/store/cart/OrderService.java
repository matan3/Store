package com.store.store.cart;
import com.store.store.payment.Payment;
import com.store.store.product.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;


@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        ProductRepository productRepository1) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository1;
    }
    public List<Order> getCart() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByUserName(userName);
    }

    public void purchaseProduct(Integer productId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        productRepository.findById(productId.longValue()).orElseThrow(() ->
                new EntityNotFoundException("Product with id " + productId + " was not found"));

        Order order = new Order(userName,productId);
        orderRepository.save(order);
    }

    public void removeProduct(Integer productId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean exist = orderRepository.existsByUserNameAndProductId(userName, productId);
        if(exist)
            orderRepository.deleteByUserNameAndProductId(userName, productId);
        else
            throw new EntityNotFoundException("Order with id " + productId + " was not found in Cart");
    }

    public String payAllProductsInCart(Payment payment) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> allOrdersByUserName = orderRepository.findByUserName(userName);
        int totalPrice = allOrdersByUserName.stream()
                .mapToInt(o -> productRepository.findById(o.getProductId().longValue()).get().getPrice())
                .sum();
        if(payment.checkIfValid()){
            payment.pay(totalPrice);
            orderRepository.deleteByUserName(userName);
            return "Success - paid "+totalPrice;
        }
        return "Failed";
    }
}
