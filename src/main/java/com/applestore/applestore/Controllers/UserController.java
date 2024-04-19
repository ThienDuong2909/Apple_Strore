package com.applestore.applestore.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.applestore.applestore.Entities.UserEntity;
import com.applestore.applestore.Security.Oauth2.CustomOAuth2User;
import com.applestore.applestore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.applestore.applestore.DTOs.CustomerDto;
import com.applestore.applestore.DTOs.OrderDto;
import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.DTOs.detailOrderDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Order;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Repositories.ProductRepository;
import com.applestore.applestore.Security.CustomUserDetails;
import com.applestore.applestore.Services.CustomerService;
import com.applestore.applestore.Services.OrderService;
import com.applestore.applestore.Services.ProductService;


import lombok.Data;

import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
	
	@Autowired
    private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService ;

    public UserEntity curUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String gmail = "";
        if (principal instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) principal;
            gmail =  oauthUser.getAttribute("email");
        } else if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            gmail =  userDetails.getGmail();
        }
        return userService.findByGmail(gmail);
    }
	
	@GetMapping("/")
    public String index(Model model){
        UserEntity user = curUser();
//
        model.addAttribute("name", user.getGmail()+user.getL_name()+user.getF_name());
//        model.addAttribute("username",k.getUsername());
        return "/Fragments/user/header";
    }
	
	
    @GetMapping("/customer_info")
    public String CustomerInfo(Model model,Authentication authentication) {
    	
//    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
    	CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		model.addAttribute("customerDto", customerDto);
    	model.addAttribute("user", user);
    	
        return "/Fragments/user/customer_info";
    }
    
    @GetMapping("/edit")
    public String Edit(Model model,Authentication authentication) {
//    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
    	CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		model.addAttribute("customerDto", customerDto);
    	
        return "/Fragments/user/Edit";
    }
    
    @PostMapping("/update")
    public String update(
            @RequestParam("address_line") String address_line,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("phone") String phone,
            Authentication authentication,
            Model model
    ){  
//        CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
        CustomerDto customerDto = customerService.getCustomerByuserId(user.getUser_id());
        Customer customer = customerService.getCustomerById1(customerDto.getCustomer_id());
        customer.setAddress_line(address_line);
        customer.setCountry(country);
        customer.setCity(city);
        customer.setPhone(phone);
        customerService.saveCustomer(customer);
        
        
        model.addAttribute("customerDto", customer);
        model.addAttribute("user", user);
        
        return "/Fragments/user/customer_info";
    }
    
    
	
    
    @GetMapping("/enter_customer_info")
    public String EnterCustomerInfo() {
        return "/Fragments/user/enter_customer_info";
    }
    
    @PostMapping("/save")
    public String save(
            @RequestParam("address_line") String address_line,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("phone") String phone,
            Authentication authentication,
            Model model
    ){  
//        CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUser_id(user.getUser_id());
        customerDto.setAddress_line(address_line);
        customerDto.setCountry(country);
        customerDto.setCity(city);
        customerDto.setPhone(phone);
    
        Customer customer = customerService.convertCustomerDtoToCustomer(customerDto);
        customerService.saveCustomer(customer);


        model.addAttribute("customerDto", customerDto);
        model.addAttribute("user", user);

        return "/Fragments/user/customer_info";
    }

	
    @GetMapping("products")
    public String products(Model model, @Param("search") String search, @Param("comboBox") String price, @Param("color") String color){

        List<ProductDto> listAllProduct = new ArrayList<>();
        List<String> listColor = productService.getAllColor();
        System.out.println("Color: "+color);
        if (search == null && price == null && color == null || Objects.equals(color, "")) {
            System.out.println("Normal list: ");
            listAllProduct = productService.listALlProduct();
        }
        else if (search != null){
            System.out.println("Search result: ");
            listAllProduct = productService.findProductByName(search);
        }
        else {
            if (price != null){
                if (price.equals("asc")){
                    System.out.println("List ordered asc: ");
                    listAllProduct = productService.getAllOrderByPrice(true);
                } else if (price.equals("desc")){
                    System.out.println("List ordered desc: ");
                    listAllProduct = productService.getAllOrderByPrice(false);
                }
            } else {
                listAllProduct = productService.getProductByColor(color);
            }
        }
        model.addAttribute("listAllProduct", listAllProduct);
        model.addAttribute("listColor", listColor);
        System.out.println("SelectedItem: " + price);
        System.out.println("SelectedColor: " + color);

    	return "/Fragments/user/products";
    }
    
    
    @GetMapping("/product_detail")
    public String product_detail(@RequestParam("product_id") int productId,
    		Authentication authentication,
    		Model model){
    	
//
    	CustomerDto customerDto = new CustomerDto();
//        CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();

        customerDto = customerService.getCustomerByuserId(user.getUser_id());
    	
        Product product = productService.getProductById(productId);
        ProductDto productDto = productService.convertProductToDto(product);
        
        model.addAttribute("productDto", productDto);
        model.addAttribute("customerDto", customerDto);

        return "/Fragments/user/Product_detail";
    }
    
    

    
    @GetMapping("/checkout")
    public String checkout(
    		@RequestParam("product_id") int product_id,
            Authentication authentication,
            Model model
    		
    ) {	
//    	CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		LocalDate currentDate = LocalDate.now();
	    String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	   
	    
		OrderDto orderDto = new OrderDto();
		orderDto.setCustomer_id(customerDto.getCustomer_id());
		orderDto.setProduct_id(product_id);
		orderDto.setOrder_date(formattedDate);
		orderDto.setStatus(0);
		Order order = orderService.convertDtoToEntity(orderDto);
		orderService.saveOrder(order);
		
		Product product = productService.getProductById(product_id);     
        String tmp = product.getStock();
        int tmp1 = Integer.parseInt(tmp);
		product.setStock(String.valueOf(tmp1-1));
		productService.saveProduct(product);
		
		
		
        return "/Fragments/user/checkout";
    }
    
    @GetMapping("/purchase_history")
    public String purchaseHistory(
    		Authentication authentication,
    		Model model){
//    	CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		List<detailOrderDto> listOrder1 = new ArrayList<>();
		listOrder1 = orderService.getDetailOrderByCustomerId(customerDto.getCustomer_id());
		
		
        model.addAttribute("listDetailOrder",listOrder1 );
        
        return "/Fragments/user/purchase_history";
    }
    
    
      

 
    
    

}
