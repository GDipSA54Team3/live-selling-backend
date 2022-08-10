package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.iss.restfulend.Model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
	@Query(value ="SELECT count(*) FROM orders o\r\n"		
			+ "INNER JOIN channel_stream cs ON o.channel_id = cs.id\r\n"
			+ "where o.status = 'PENDING' and cs.user_id = ?1", nativeQuery = true)	
	Integer getPendingOrderCountBySeller(String id);
	
}
