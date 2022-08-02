package sg.edu.iss.restfulend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "UserType", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Column(length = 200)
    private String address;
    private String username;
    private String password;
    private Boolean isVerified;
    @OneToOne(mappedBy = "buyer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Rating> reviews;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Orders> ordersHistory;

    public Buyer(String firstName, String lastName, String address, String username, String password, Boolean isVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.username = username;
        this.password = password;
        this.isVerified = isVerified;
        this.reviews = new ArrayList<>();
        this.ordersHistory = new ArrayList<>();
    }
}
