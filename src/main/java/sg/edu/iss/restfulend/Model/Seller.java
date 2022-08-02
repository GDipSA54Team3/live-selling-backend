package sg.edu.iss.restfulend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Seller")
@Getter
@Setter
@NoArgsConstructor
public class Seller extends Buyer{
    @OneToOne
    @JsonIgnore
    private ChannelStream channel;

    public Seller(String firstName, String lastName, String address, String username, String password,
                  Boolean isVerified, ChannelStream channel) {
        super(firstName, lastName, address, username, password, isVerified);
        this.channel = channel;
    }
}
