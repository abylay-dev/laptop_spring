package kz.spring.laptop_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {
    private Integer id;
    private String model;
    private int price;
    private int count;
//    private Country country;
}
