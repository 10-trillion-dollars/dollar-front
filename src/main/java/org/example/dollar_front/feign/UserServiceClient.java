package org.example.dollar_front.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface UserServiceClient {

    @PostMapping("/users/signup")
    String signup(@RequestBody Map<String, Object> user);

    @PostMapping("/users/login")
    ResponseEntity<String> login(@RequestBody Map<String, Object> user);
}
