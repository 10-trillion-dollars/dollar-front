package org.example.dollar_front.feign;

import java.util.List;
import org.example.dollar_front.model.review.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service", url = "http://localhost:8085")
public interface ReviewServiceClient {
    @GetMapping("/products/{productId}/reviews")
    List<Review> getReviews(@PathVariable("productId") Long productId);
}
