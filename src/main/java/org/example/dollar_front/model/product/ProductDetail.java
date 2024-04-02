package org.example.dollar_front.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetail {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private Long stock;
    private String photo;
    private String adminname;
}
