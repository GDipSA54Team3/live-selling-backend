package sg.edu.iss.restfulend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.restfulend.Helper.OrderStatus;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime orderDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    
    private ChannelStream channel;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderProduct> orderProduct;

    public Orders(User user, LocalDateTime orderDateTime, OrderStatus status, ChannelStream channel) {
        this.orderDateTime = orderDateTime;
        this.orderProduct = new ArrayList<>();
        this.user = user;
        this.status = status;
        this.channel=channel;
    }
    
}
