package org.example.dollar_front.model.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Review {
    private Long id;
    private String content;
    private String photo;
    private int score;
}
