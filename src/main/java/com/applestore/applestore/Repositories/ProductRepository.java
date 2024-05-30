package com.applestore.applestore.Repositories;

import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select product_id, name, description, color, stock, CONVERT(INT, price) AS price, img from products", nativeQuery = true)
    List<Product> findAllProduct();
    List<Product> findProductsByNameIgnoreCase(String search);
    List<Product> getProductsByColorIgnoreCase(String color);
    @Query(value = "select color from products group by color", nativeQuery = true)
    List<String> getAllColor();
}
