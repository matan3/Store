package com.store.store.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserName(String userName);

    boolean existsByUserNameAndProductId(String userName, Integer productId);

    void deleteByUserNameAndProductId(String userName, Integer productId);

    void deleteByUserName(String userName);
}
