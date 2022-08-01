package sg.edu.iss.restfulend.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private int quantity;
    
    @ManyToOne
    private Orders order;
    @ManyToOne
    private Product product;
    @ManyToOne
    private ChannelStream channel;
    @ManyToOne
    private Cart cart;
    
}
