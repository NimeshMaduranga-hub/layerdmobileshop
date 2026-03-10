package lk.ijse.layerdmobileshop.mobileshop.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity

public class Customer implements Comparable<Customer>{

    @Id
    private String id;

    private String name;
    private String address;
    private String mobile;

    @Override
    public int compareTo(Customer o) {
        return this.id.compareTo(o.id);
    }
}