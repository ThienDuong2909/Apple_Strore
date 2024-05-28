package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Order;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Entities.UserEntity;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM ORDERS", nativeQuery = true)
    List<Order> findAllOrder();

    @Query(value = "SELECT * FROM ORDERS WHERE ORDERS.STATUS = ?1", nativeQuery = true)
    List<Order> listApprovedOrNotOrder(int status);

    @Query(value = "UPDATE ORDERS SET ORDERS.STATUS = ?1 WHERE ORDERS.ORDER_ID = ?2", nativeQuery = true)
    void updateStatusOrder(int status, int id);
    
    @Query(value = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?1", nativeQuery = true)
    List<Order> findByCustomerId(int customer_id);

    @Query(value = "SELECT * FROM ORDERS WHERE ORDER_ID = ?1", nativeQuery = true)
    List<Order> findByOrderId(int order_id);
}
