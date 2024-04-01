package org.example.dollar_front.controller;

import java.util.Arrays;
import org.springframework.ui.Model;
import org.example.dollar_front.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ShopController {

    @Autowired
    private final RestTemplate restTemplate;

    public ShopController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/")
    public String index(Model model) {
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:8083/products", Product[].class);
            Product[] products = response.getBody();
            model.addAttribute("products", Arrays.asList(products));
        } catch (Exception e) {
            // 서버에 연결되지 않았을 때의 예외 처리
            // 기본적인 화면을 보여주거나 오류 메시지를 표시할 수 있음
            model.addAttribute("errorMessage", "상품 정보를 가져오는 도중 오류가 발생했습니다.");
        }
        return "index";
    }

}
