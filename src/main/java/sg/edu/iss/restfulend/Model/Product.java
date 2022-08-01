package sg.edu.iss.restfulend.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(length = 100)
    private String name;
    private String category;
    @Column(length = 500)
    private String description;
    private double price;
    private int quantity;
    
    @ManyToOne
    private ChannelStream channel;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProduct;
    // @OneToMany
    // private List<Stream> streams;

}
