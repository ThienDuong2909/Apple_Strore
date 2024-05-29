package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.CustomerDto;
import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Repositories.CustomerRepository;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public CustomerDto getCustomerById(int id){
        CustomerDto customerDto = new CustomerDto();
        Customer customer = customerRepository.getReferenceById(id);
        customerDto.setUser_id(customer.getUser_id());
        customerDto.setCustomer_id(customer.getCustomer_id());
        customerDto.setAddress_line(customer.getAddress_line());
        customerDto.setPhone(customer.getPhone());
        customerDto.setCity(customer.getCity());
        customerDto.setCountry(customer.getCountry());
        return customerDto;
    }
    
    public Customer getCustomerById1(int id){
        
        Customer customer = customerRepository.getReferenceById(id);
        return customer;
    }
    
    public CustomerDto getCustomerByuserId(int user_id){
        List<Customer> customers = customerRepository.findAll();
        		
        for (Customer customer : customers) {
            if (customer.getUser_id() == user_id) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setUser_id(customer.getUser_id());
                customerDto.setCustomer_id(customer.getCustomer_id());
                customerDto.setAddress_line(customer.getAddress_line());
                customerDto.setPhone(customer.getPhone());
                customerDto.setCity(customer.getCity());
                customerDto.setCountry(customer.getCountry());
                return customerDto;
            }
        }
        return null; // Trả về null nếu không tìm thấy khách hàng với user_id tương ứng
    }
    
    public Customer convertCustomerDtoToCustomer(CustomerDto customerDto){
    	Customer customer = new Customer();
        customer.setUser_id(customerDto.getUser_id());
        customer.setAddress_line(customerDto.getAddress_line());
        customer.setCity(customerDto.getCity());
        customer.setCountry(customerDto.getCountry());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }
    
    public void saveCustomer(Customer customer){

    	customerRepository.save(customer);
    }
}
