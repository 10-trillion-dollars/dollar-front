package org.example.dollar_front.feign;

import java.util.List;
import org.example.dollar_front.model.address.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "address-service", url = "http://localhost:8082")
public interface AddressServiceClient {
    @GetMapping("/users/address")
    List<Address> getAddresses();
}
