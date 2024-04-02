package org.example.dollar_front.feign;

import java.util.List;
import org.example.dollar_front.model.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "http://localhost:8083")
public interface ProductServiceClient {

    @GetMapping("/products")
    List<Product> getProducts();

    // 검색 쿼리를 인자로 받아 검색 결과를 반환하는 메소드
    @GetMapping("/products/search")
    List<Product> searchProducts(@RequestParam("search") String searchQuery);
}

