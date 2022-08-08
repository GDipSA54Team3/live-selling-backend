package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Helper.StreamStatus;
import sg.edu.iss.restfulend.Model.Stream;

import java.util.List;

@Repository
public interface StreamRepository extends JpaRepository<Stream, String> {
    @Query("SELECT s FROM Stream s JOIN s.channel sc WHERE sc.user.id = :id AND s.status = :status")
    List<Stream> getStreamsByUserIdAndStatus(String id, StreamStatus status);
}
