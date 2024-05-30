package com.applestore.applestore.DTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private int order_id, product_id, customer_id, status;
    private String order_date;
}
