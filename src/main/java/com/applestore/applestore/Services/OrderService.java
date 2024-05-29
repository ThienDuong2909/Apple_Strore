package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.*;
import com.applestore.applestore.Entities.Order;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    public OrderDto convertEntityToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setProduct_id(order.getProduct_id());
        orderDto.setOrder_id(order.getOrder_id());
        orderDto.setOrder_date(order.getOrder_date());
        orderDto.setCustomer_id(order.getCustomer_id());
        return orderDto;
    }

    public Order convertDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        order.setProduct_id(orderDto.getProduct_id());
        order.setOrder_id(orderDto.getOrder_id());
        order.setOrder_date(orderDto.getOrder_date());
        order.setCustomer_id(orderDto.getCustomer_id());
        return order;
    }

    public List<OrderDto> getAllOrder(){
        List<OrderDto> listAllOrder = new ArrayList<>();
        for (Order order : orderRepository.findAllOrder()){
            listAllOrder.add(convertEntityToDto(order));
        }
        System.out.println("List order: ");
        for (OrderDto orderDto : listAllOrder){
            System.out.println(orderDto.getOrder_id());
        }
        return listAllOrder;
    }

    // LAY RA THONG TIN CHI TIET CUA TAT CA CAC ORDER
    public List<detailOrderDto> getDetailOrder(){
        List<detailOrderDto> listDetailOrder = new ArrayList<>();
        for (OrderDto orderDto : getAllOrder()){
            CustomerDto customer = customerService.getCustomerById(orderDto.getCustomer_id());
            UserDto user = userService.getUserById(customer.getUser_id());
            ProductDto product = productService.convertProductToDto(productService.getProductById(orderDto.getProduct_id()));
            detailOrderDto detailOrder = new detailOrderDto();
            detailOrder.setOrder_id(orderDto.getOrder_id());
            detailOrder.setOrder_date(orderDto.getOrder_date());
            detailOrder.setProduct_name(product.getName());
            detailOrder.setProduct_color(product.getColor());
            detailOrder.setCustomer_f_name(user.getF_name());
            detailOrder.setCustomer_l_name(user.getL_name());
            detailOrder.setPhone(customer.getPhone());
            detailOrder.setPrice(product.getPrice());
            detailOrder.setAddress_line(customer.getAddress_line());
            detailOrder.setCity(customer.getCity());
            detailOrder.setCountry(customer.getCountry());
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }

    // DANH SACH CAC ORDER DUOC DUYET HOAC CHUA
    public List<detailOrderDto> getStatusOrder(int status){
        List<OrderDto> list = new ArrayList<>();
        for (Order order : orderRepository.listApprovedOrNotOrder(status)){
            list.add(convertEntityToDto(order));
        }
        System.out.println(getMoreDetailOrder(list));
        return getMoreDetailOrder(list);
    }

    // LAY THONG TIN CHI TIET VE CAC ORDER (SP, KHACH HANG,...)
    public List<detailOrderDto> getMoreDetailOrder(List<OrderDto> list){
        List<detailOrderDto> listDetailOrder = new ArrayList<>();
        for (OrderDto order : list){
            CustomerDto customer = customerService.getCustomerById(order.getCustomer_id());
            UserDto user = userService.getUserById(customer.getUser_id());
            ProductDto product = productService.convertProductToDto(productService.getProductById(order.getProduct_id()));

            detailOrderDto detailOrder = new detailOrderDto();
            detailOrder.setOrder_id(order.getOrder_id());
            detailOrder.setOrder_date(order.getOrder_date());
            detailOrder.setProduct_name(product.getName());
            detailOrder.setProduct_color(product.getColor());
            detailOrder.setCustomer_f_name(user.getF_name());
            detailOrder.setCustomer_l_name(user.getL_name());
            detailOrder.setPhone(customer.getPhone());
            detailOrder.setPrice(product.getPrice());
            detailOrder.setAddress_line(customer.getAddress_line());
            detailOrder.setCity(customer.getCity());
            detailOrder.setCountry(customer.getCountry());

            System.out.println("Print at getMore: " + detailOrder.getOrder_id());
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }
    
    //lấy thông tin đơn hàng bằng id khách hàng
    public List<OrderDto> getOrderByCustomerId_ASC(int customer_id){
        List<OrderDto> listOrder = new ArrayList<>();
        for (Order order : orderRepository.findByCustomerId_ASC(customer_id)){
        	OrderDto orderDto = new OrderDto();
        	orderDto.setOrder_id(order.getOrder_id());
        	orderDto.setCustomer_id(order.getCustomer_id());
        	orderDto.setProduct_id(order.getProduct_id());
        	orderDto.setOrder_date(order.getOrder_date());
        	orderDto.setStatus(order.getStatus());
        	listOrder.add(orderDto);
        }
        
        return listOrder;
    }
    
    public List<OrderDto> getOrderByCustomerId_DESC(int customer_id){
        List<OrderDto> listOrder = new ArrayList<>();
        for (Order order : orderRepository.findByCustomerId_DESC(customer_id)){
        	OrderDto orderDto = new OrderDto();
        	orderDto.setOrder_id(order.getOrder_id());
        	orderDto.setCustomer_id(order.getCustomer_id());
        	orderDto.setProduct_id(order.getProduct_id());
        	orderDto.setOrder_date(order.getOrder_date());
        	orderDto.setStatus(order.getStatus());
        	listOrder.add(orderDto);
        }
        
        return listOrder;
    }
    
    public List<detailOrderDto> getDetailOrderByCustomerId(int customer_id, String Time, String Status){
        List<detailOrderDto> listOrder = new ArrayList<>();
        
        if( Time == null) {
	        for (OrderDto orderDto : getOrderByCustomerId_DESC(customer_id)){
	            CustomerDto customer = customerService.getCustomerById(orderDto.getCustomer_id());
	            ProductDto product = productService.convertProductToDto(productService.getProductById(orderDto.getProduct_id()));
	            detailOrderDto detailOrder = new detailOrderDto();
	            detailOrder.setOrder_id(orderDto.getOrder_id());
	            detailOrder.setOrder_date(orderDto.getOrder_date());
	            detailOrder.setProduct_name(product.getName());
	            detailOrder.setProduct_color(product.getColor());
	            detailOrder.setPrice(product.getPrice());
	            detailOrder.setAddress_line(customer.getAddress_line() + ", " + customer.getCity() + ", " + customer.getCountry());
	            detailOrder.setImg(product.getImg());
	            if(orderDto.getStatus() == 0) {
	            	detailOrder.setStatus("Chờ xét duyệt");
	            }
	            if(orderDto.getStatus() == 1) {
	            	detailOrder.setStatus("Đã xét duyệt");
	            }
	            if(orderDto.getStatus() == 2) {
	            	detailOrder.setStatus("Đã giao hàng");
	            }
	            
	            listOrder.add(detailOrder);
	        }
        }else if(Time.equals("newest") || Time.equals("") ) {
        	for (OrderDto orderDto : getOrderByCustomerId_DESC(customer_id)){
	            CustomerDto customer = customerService.getCustomerById(orderDto.getCustomer_id());
	            ProductDto product = productService.convertProductToDto(productService.getProductById(orderDto.getProduct_id()));
	            detailOrderDto detailOrder = new detailOrderDto();
	            detailOrder.setOrder_id(orderDto.getOrder_id());
	            detailOrder.setOrder_date(orderDto.getOrder_date());
	            detailOrder.setProduct_name(product.getName());
	            detailOrder.setProduct_color(product.getColor());
	            detailOrder.setPrice(product.getPrice());
	            detailOrder.setAddress_line(customer.getAddress_line() + ", " + customer.getCity() + ", " + customer.getCountry());
	            detailOrder.setImg(product.getImg());
	            if(orderDto.getStatus() == 0) {
	            	detailOrder.setStatus("Chờ xét duyệt");
	            }
	            if(orderDto.getStatus() == 1) {
	            	detailOrder.setStatus("Đã xét duyệt");
	            }
	            if(orderDto.getStatus() == 2) {
	            	detailOrder.setStatus("Đã giao hàng");
	            }
	            
	            listOrder.add(detailOrder);
        	} 
  	
        }else {
        	for (OrderDto orderDto : getOrderByCustomerId_ASC(customer_id)){
	            CustomerDto customer = customerService.getCustomerById(orderDto.getCustomer_id());
	            ProductDto product = productService.convertProductToDto(productService.getProductById(orderDto.getProduct_id()));
	            detailOrderDto detailOrder = new detailOrderDto();
	            detailOrder.setOrder_id(orderDto.getOrder_id());
	            detailOrder.setOrder_date(orderDto.getOrder_date());
	            detailOrder.setProduct_name(product.getName());
	            detailOrder.setProduct_color(product.getColor());
	            detailOrder.setPrice(product.getPrice());
	            detailOrder.setAddress_line(customer.getAddress_line() + ", " + customer.getCity() + ", " + customer.getCountry());
	            detailOrder.setImg(product.getImg());
	            if(orderDto.getStatus() == 0) {
	            	detailOrder.setStatus("Chờ xét duyệt");
	            }
	            if(orderDto.getStatus() == 1) {
	            	detailOrder.setStatus("Đã xét duyệt");
	            }
	            if(orderDto.getStatus() == 2) {
	            	detailOrder.setStatus("Đã giao hàng");
	            }
	            
	            listOrder.add(detailOrder);
	        }
        }
        
        if (Status != null && (Status.equals("Chờ xét duyệt") || Status.equals("Đã xét duyệt") || Status.equals("Đã giao hàng"))) {
            List<detailOrderDto> filteredList = new ArrayList<>();
            for (detailOrderDto detailOrder : listOrder) {
                if (detailOrder.getStatus().equals(Status)) {
                    filteredList.add(detailOrder);
                }
            }
            return filteredList;
        }
        
        return listOrder;
    }
    

    public void updateOrderStatus(int status, int orderId){
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }
    
    public void saveOrder(Order order) {
    	orderRepository.save(order);
    }

}
