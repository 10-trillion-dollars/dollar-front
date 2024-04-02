package org.example.dollar_front.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dollar_front.feign.ProductServiceClient;
import org.example.dollar_front.feign.ReviewServiceClient;
import org.example.dollar_front.model.product.Product;
import org.example.dollar_front.model.product.ProductDetail;
import org.example.dollar_front.model.review.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceClient productServiceClient;
    private final ReviewServiceClient reviewServiceClient;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request,
        @RequestParam(value = "search", required = false) String searchQuery) {
        boolean isAuthenticated = Arrays.stream(
                request.getCookies() != null ? request.getCookies() : new Cookie[0])
            .anyMatch(
                cookie -> "Authorization".equals(cookie.getName()) && !cookie.getValue().isEmpty());

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

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable Long productId, Model model) {
        ProductDetail product = productServiceClient.getProductDetail(productId);
        List<Review> reviews = reviewServiceClient.getReviews(productId);

        double averageScore = reviews.stream()
            .mapToInt(Review::getScore)
            .average()
            .orElse(0.0);
        String formattedAverageScore = String.format("%.1f", averageScore);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageScore", formattedAverageScore);

        return "productDetail";
    }
}


