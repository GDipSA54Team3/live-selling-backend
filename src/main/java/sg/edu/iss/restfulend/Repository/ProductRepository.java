package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.OrderProduct;
import sg.edu.iss.restfulend.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p JOIN p.channel ch JOIN ch.product chp WHERE ch.seller.id =:sellerId AND chp.id =:prodId")
    public Product findExistInChannel(String buyerId, String prodId);
}


