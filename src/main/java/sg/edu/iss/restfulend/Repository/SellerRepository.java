package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
}
