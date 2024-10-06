package exercise.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.*;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "price"})
public class Product {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private long id;

    private String title;
    private long price;

    public Product(String title, long price) {
        this.title = title;
        this.price = price;
    }
}