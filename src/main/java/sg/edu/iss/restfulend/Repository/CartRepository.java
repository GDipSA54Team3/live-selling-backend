package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
