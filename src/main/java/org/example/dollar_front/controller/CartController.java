package org.example.dollar_front.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dollar_front.feign.AddressServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.example.dollar_front.model.address.Address;



@Controller
@RequiredArgsConstructor
public class CartController {

    private final AddressServiceClient addressServiceClient;

    @GetMapping("/cart")
    public String showCart(Model model) {
        try {
            List<Address> addresses = addressServiceClient.getAddresses();
            model.addAttribute("addresses", addresses);
        } catch (Exception e) {
            // 주소를 가져오는 데 실패한 경우 빈 목록을 모델에 추가
            model.addAttribute("addresses", new ArrayList<Address>());
            // 실패 이유 등을 로깅하거나 예외 처리할 수도 있음
            // logger.error("Failed to fetch addresses: " + e.getMessage());
        }
        return "cart";
    }


}
