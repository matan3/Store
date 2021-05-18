package com.store.store.cart;
import com.store.store.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cart")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping()
    public List<Order> getCart(){
        return orderService.getCart();
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/purchase/{productId}")
    public void purchaseProduct(@PathVariable("productId") Integer productId){
        orderService.purchaseProduct(productId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/remove/{productId}")
    public void removeProduct(@PathVariable("productId") Integer productId){
        orderService.removeProduct(productId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/payment")
    public String payAllProductsInCart(@RequestBody Payment payment){
        return orderService.payAllProductsInCart(payment);
    }
}
