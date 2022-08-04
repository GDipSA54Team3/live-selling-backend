package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, String> {
    @Query("SELECT op FROM OrderProduct op JOIN op.cart opc JOIN op.product opp WHERE opc.user.id = :buyerId AND opp.id = :prodId")
    OrderProduct findExistInCart(String buyerId, String prodId);

}
