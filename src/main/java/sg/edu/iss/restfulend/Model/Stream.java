package sg.edu.iss.restfulend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Stream {
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String title;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private LocalDateTime schedule;
    @ManyToOne
    @JsonIgnore
    private ChannelStream channel;
    @OneToOne(mappedBy = "stream", cascade = CascadeType.ALL)
    @JsonIgnore
    private StreamLog log;

    public Stream(String title, LocalDateTime schedule, ChannelStream channel) {
        this.title = title;
        this.schedule = schedule;
        this.channel = channel;

    }
}
