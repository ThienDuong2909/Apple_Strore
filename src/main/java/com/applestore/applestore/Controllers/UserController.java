package com.applestore.applestore.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import com.applestore.applestore.Entities.UserEntity;
import com.applestore.applestore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.applestore.applestore.DTOs.CustomerDto;
import com.applestore.applestore.DTOs.OrderDto;
import com.applestore.applestore.DTOs.ProductDto;
import com.applestore.applestore.DTOs.detailOrderDto;
import com.applestore.applestore.Entities.Customer;
import com.applestore.applestore.Entities.Order;
import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Security.CustomUserDetails;
import com.applestore.applestore.Services.CustomerService;
import com.applestore.applestore.Services.OrderService;
import com.applestore.applestore.Services.ProductService;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

	
    @GetMapping("/customer_info")
    public String CustomerInfo(Model model,Authentication authentication) {
    	
//    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
    	CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		List<CustomerDto> CustomerDtos = customerService.list_CustomerDto();
		List<String> ds_SDT = new ArrayList<String>();
		String tmp = null;
		if(customerDto != null) {
			tmp = customerDto.getPhone();
		}
		
		for(CustomerDto ctmDto : CustomerDtos) {
			
			if(ctmDto.getPhone().equals(tmp)) {
				
			}else {
				ds_SDT.add(ctmDto.getPhone());
			}
			
			
			
		}
		
		model.addAttribute("ds_SDT", ds_SDT);
		model.addAttribute("customerDto", customerDto);
    	model.addAttribute("user", user);
    	
        return "/Fragments/user/customer_info";
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

        return "redirect:/user/customer_info";
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
        
        
        
        
        return "redirect:/user/customer_info";
    }
    
    
	
    
    
    
    
	
    @GetMapping("/")
    public String index(Model model){
        List<ProductDto> listAllProduct = productService.listALlProduct();
        if(listAllProduct.size() > 20){
            listAllProduct = listAllProduct.subList(0, 15);
        }
        model.addAttribute("listAllProduct", listAllProduct);
    	return "/Fragments/user/index";
    }
    @GetMapping("/products")
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

        return "/Fragments/user/view-all-product";
    }

    @GetMapping("/search")
    public String searchProd(Model model, @Param("search") String search, @Param("comboBox") String price, @Param("color") String color){
        List<ProductDto> resultSearchList = productService.listALlProduct();
        List<String> listColor = productService.getAllColor();
        if (search != null && !search.isEmpty()){
            System.out.println("Search result: ");
            resultSearchList = resultSearchList.stream().filter((x)-> x.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        }

        if (color != null && !color.isEmpty()) {
            resultSearchList = resultSearchList.stream()
                    .filter(product -> product.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
        }

        if (price != null && (price.equalsIgnoreCase("asc") || price.equalsIgnoreCase("desc"))) {
            if (price.equalsIgnoreCase("asc")) {
                resultSearchList.sort(Comparator.comparingInt(ProductDto::getIntPrice));
            } else if (price.equalsIgnoreCase("desc")) {
                resultSearchList.sort(Comparator.comparingInt(ProductDto::getIntPrice).reversed());
            }
        }
        model.addAttribute("resultSearchList", resultSearchList);
        model.addAttribute("listColor", listColor);

        model.addAttribute("searchKey", search);
        return "/Fragments/user/search-prod";

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

        return "/Fragments/user/product_detail";
    }
    
    

    
    @PostMapping("/checkout")
    public String checkout(
    		@RequestParam("product_id") int product_id,
            Authentication authentication,
            Model model
    ) {	
//    	CustomUserDetails u = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
	   
	    
		OrderDto orderDto = new OrderDto();
		orderDto.setCustomer_id(customerDto.getCustomer_id());
		orderDto.setProduct_id(product_id);
		orderDto.setOrder_date(formattedDateTime);
		orderDto.setStatus(0);
		Order order = orderService.convertDtoToEntity(orderDto);
		orderService.saveOrder(order);	
		
		Product product = productService.getProductById(product_id); 
		ProductDto productDto = productService.convertProductToDto(product);
        String tmp = product.getStock();
        int tmp1 = Integer.parseInt(tmp);
		product.setStock(String.valueOf(tmp1-1));
		productService.saveProduct(product);
		
		
		
		return "redirect:/user/products";
    }

    @PostMapping("/purchase-detail-info")
    public String purchaseDetailInfo(
            CustomerDto customerDto
    ){
        System.out.println(customerDto);
        return "redirect:/user/";
    }
    @GetMapping("/purchase_history")
    public String purchaseHistory(Model model, @Param("Time") String Time, @Param("Status") String Status ){

        UserEntity user = curUser();
		CustomerDto customerDto = new CustomerDto();
		customerDto = customerService.getCustomerByuserId(user.getUser_id());
		
		List<detailOrderDto> listOrder = new ArrayList<>();
		listOrder = orderService.getDetailOrderByCustomerId(customerDto.getCustomer_id(),Time, Status );
				
		
        model.addAttribute("listDetailOrder",listOrder );
        
        return "/Fragments/user/purchase_history";
    }

    @GetMapping("/change-password" )
    public String changePasswordPage(Model model){
        UserEntity user = curUser();

        model.addAttribute("username", user.getUsername());
        return "Fragments/user/change_password";
    }
    @PostMapping("/change-password" )
    public String changePassword(@RequestParam("username") String username,
                                 @RequestParam("newPw") String newPw,
                                 @RequestParam("oldPw") String oldPw){
        System.out.println("taiKhoan: "+username);
        System.out.println("matKhauCu: "+oldPw);
        System.out.println("matKhauMoi: "+newPw);
        UserEntity user = userService.findByUsername(username);
        System.out.println("User: "+user);
        if (passwordEncoder.matches(oldPw, user.getPassword())) {
            userService.changePassword(username, newPw);
            return "redirect:/user/change-password?success";
        } else {
            return "redirect:/user/change-password?fail"; // Trả về lỗi nếu mật khẩu hiện tại không khớp
        }
    }
}
