package org.example.dollar_front.controller;

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
        List<Address> addresses = addressServiceClient.getAddresses();
        model.addAttribute("addresses", addresses);
        return "cart";
    }

}
