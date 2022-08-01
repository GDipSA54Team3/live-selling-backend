package sg.edu.iss.restfulend.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class StreamLog {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private int numLikes;
    @OneToOne(mappedBy = "log", cascade = CascadeType.ALL)
    private Stream stream;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL)
    private List<Message> messages;
}
