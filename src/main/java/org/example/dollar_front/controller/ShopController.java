package org.example.dollar_front.controller;

import java.util.Arrays;
import org.springframework.ui.Model;
import org.example.dollar_front.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping("/login")
//    public String handleLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
//        // RestTemplate을 사용하여 인증 서버에 로그인 요청
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8082/users/login";
//        // 로그인 요청에 필요한 데이터 설정
//        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
//        requestMap.add("email", username);
//        requestMap.add("password", password);
//
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(url, requestMap, String.class);
//            // 인증 성공 시 처리 로직
//            // 예: 세션에 사용자 정보 저장, 성공 메시지 설정 등
//            return "redirect:/"; // 홈 페이지로 리다이렉트
//        } catch (HttpClientErrorException e) {
//            // 인증 실패 시 처리 로직
//            // 예: 실패 메시지 설정, 로그인 페이지로 리다이렉트
//            System.out.println("im work");
//            redirectAttributes.addFlashAttribute("error", "로그인 실패");
//            return "redirect:/signupin"; // 로그인 페이지로 리다이렉트
//        }
//    }


}
