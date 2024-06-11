package com.applestore.applestore.Controllers;
import com.applestore.applestore.DTOs.*;

import com.applestore.applestore.Entities.Product;
import com.applestore.applestore.Services.OrderService;
import com.applestore.applestore.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/")
    public String index(){
        return "Fragments/admin/header";
    }

    // -------------------- MANAGEMENT PRODUCT ---------------------------------------------
    @GetMapping("viewAll")
    public String viewAllProduct(Model model, @Param("search") String search, @Param("comboBox") String price, @Param("color") String color){
        List<ProductDto> listAllProduct = new ArrayList<>();
        List<String> listColor = productService.getAllColor();
        if (search == null && price == null && color == null) {
            listAllProduct = productService.listALlProduct();
        }
        else if (search != null){
            listAllProduct = productService.findProductByName(search);
        }
        else {
            if (price != null){
                if (price.equals("asc")){
                    listAllProduct = productService.getAllOrderByPrice(true);
                } else if (price.equals("desc")){
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
        return "Fragments/admin/view-all-product";
    }

    @GetMapping("addNew")
    public String addNewProduct(){
        return "Fragments/admin/add-new-product";
    }

    @PostMapping("save")
    public String saveNewProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("stock") String stock,
            @RequestParam("color") String color,
            @RequestParam("img") MultipartFile imageFile
    ){
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setStock(Integer.parseInt(stock));
        productDto.setPrice(price);
        productDto.setColor(color);
        productDto.setImg(productService.encodingImage(imageFile));
        Product product = productService.convertProductDtoToEntity(productDto);
        productService.saveProduct(product);
        return "redirect:/admin/viewAll";
    }

    @PostMapping("update")
    public String updateProduct(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") String price,
            @RequestParam("stock") String stock,
            @RequestParam("color") String color
    ){
        Product product = productService.getProductById(id);
        product.setName(name);
        product.setDescription(description);
        product.setStock(String.valueOf(stock));
        product.setPrice(productService.convertToInt(price));
        product.setColor(color);
        productService.saveProduct(product);
        return "redirect:/admin/viewAll";
    }

    @GetMapping("edit/id={id}")
    public String editInformation(@PathVariable("id") Integer id, Model model){
        model.addAttribute("product", productService.convertProductToDto(productService.getProductById(id)));
        model.addAttribute("id", id);
        return "Fragments/admin/edit-infor-product";
    }

    @GetMapping("delete/id={id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        // kiem tra xem co
        boolean isDuplicate = true;
        for (int i = 0; i < orderService.getStatusOrder(1).size(); i++) {
            if (orderService.getStatusOrder(1).get(i).getProduct_id() == id){
                isDuplicate = false;
                break;
            }
        }
        if (isDuplicate) {
            productService.deleteProduct(id);
            return "redirect:/admin/viewAll";
        } else {
            return "redirect:/admin/viewAll";
        }

    }

    // -------------------- MANAGEMENT ORDER ---------------------------------------------
    @GetMapping("viewAllOrder")
    public String viewAllOrder(Model model){
        model.addAttribute("listDetailOrder", orderService.getDetailOrder());
        return "Fragments/admin/management-order";
    }

    @GetMapping("notApproved")
    public String viewListNotApprovedOrder(Model model){
        model.addAttribute("listNotApproved", orderService.getStatusOrder(0));
        return "Fragments/admin/not-approved-order";
    }

    @GetMapping("isApproved")
    public String viewListIsApprovedOrder(Model model){
        model.addAttribute("listIsApproved", orderService.getStatusOrder(1));
        return "Fragments/admin/is-approved-order";
    }

    @GetMapping("delivered")
    public String viewListDeliveredOrder(Model model){
        model.addAttribute("listDeliveredOrder", orderService.getStatusOrder(2));
        return "Fragments/admin/delivered-order";
    }

    @GetMapping("canceled")
    public String viewListCanceledOrder(Model model){
        model.addAttribute("listCanceledOrder", orderService.getCanceledOrder(-1));
        return "Fragments/admin/canceled-order";
    }

    @GetMapping("accept/orderid={id}")
    public String acceptOrder(@PathVariable("id") Integer orderId){
        orderService.updateOrderStatus(1,orderId,"");
        return "redirect:/admin/notApproved";
    }

    @GetMapping("complete/orderid={id}")
    public String completeOrder(@PathVariable("id") Integer orderId){
        orderService.updateOrderStatus(2,orderId, "");
        return "redirect:/admin/isApproved";
    }

    @GetMapping("cancel/orderid={id}/reason={reason}")
    public String cancelOrder(@PathVariable("id") Integer orderId, @PathVariable("reason") String reason){
        System.out.println("Reason: " + reason);
        orderService.updateOrderStatus(-1,orderId,reason);
        return "redirect:/admin/notApproved";
    }

    @GetMapping("fail/orderid={id}/reason={reason}")
    public String failDeliveredOrder(@PathVariable("id") Integer orderId, @PathVariable("reason") String reason){
        System.out.println("Reason: " + reason);
        orderService.updateOrderStatus(-1,orderId,reason);
        return "redirect:/admin/isApproved";
    }

}
