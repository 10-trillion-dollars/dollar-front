package org.example.dollar_front.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    private Long id;
    private String photo;
    private String name;
    private Long price;
}
