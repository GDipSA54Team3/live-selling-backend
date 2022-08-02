package sg.edu.iss.restfulend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ChannelStream {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    @OneToOne(mappedBy = "channel", cascade = CascadeType.ALL)
    @JsonIgnore
    private Seller seller;
    
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Rating> ratings;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<OrderProduct> orders;
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Stream> streams;

    public ChannelStream(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.streams = new ArrayList<>();
    }
}
