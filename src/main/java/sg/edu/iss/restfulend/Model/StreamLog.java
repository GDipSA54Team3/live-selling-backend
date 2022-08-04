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
public class StreamLog {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private int numLikes;
    @OneToOne
    private Stream stream;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;

    public StreamLog(int numLikes) {
        this.numLikes = numLikes;
        this.messages = new ArrayList<>();
    }
}
