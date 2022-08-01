package sg.edu.iss.restfulend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.restfulend.Model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Integer>{
    
}
