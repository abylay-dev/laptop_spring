package kz.spring.laptop_spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laptop_id")
    private Integer id;

    @Column(length = 100)
    private String model;

    private int price;
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Market> markets;

    public Laptop(Integer id, String model, int price, int count) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.count = count;
    }
}
