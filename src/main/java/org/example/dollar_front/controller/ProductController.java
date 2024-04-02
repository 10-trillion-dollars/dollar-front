package org.example.dollar_front.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import org.example.dollar_front.feign.ProductServiceClient;
import org.example.dollar_front.model.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductServiceClient productServiceClient;

    public ProductController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request, @RequestParam(value = "search", required = false) String searchQuery) {
        boolean isAuthenticated = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
            .anyMatch(cookie -> "Authentication".equals(cookie.getName()) && !cookie.getValue().isEmpty());

        List<Product> products;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            products = productServiceClient.searchProducts(searchQuery);
        } else {
            products = productServiceClient.getProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "index";
    }
}


