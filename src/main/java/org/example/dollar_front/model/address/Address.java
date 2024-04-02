package org.example.dollar_front.model.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private Long id;
    private String city;
    private String village;
    private String province;
}
