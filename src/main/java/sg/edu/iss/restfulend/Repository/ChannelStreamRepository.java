package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.ChannelStream;
import sg.edu.iss.restfulend.Model.Product;

@Repository
public interface ChannelStreamRepository extends JpaRepository<ChannelStream, String> {
}
