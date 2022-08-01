package sg.edu.iss.restfulend.Model;

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
    private ChannelStream channel;
}
