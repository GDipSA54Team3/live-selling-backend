package sg.edu.iss.restfulend.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Repository.*;
import java.text.DecimalFormat;



@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
    @Autowired
    OrdersRepository ordersRepo;
  
    @GetMapping("/pendingorders/{userId}")
    public String getPendingOrdersCount(@PathVariable("userId") String userId) {    	
    	Integer pendingOrdersCount = ordersRepo.getPendingOrderCountBySeller(userId);    
            return String.valueOf(pendingOrdersCount);
    }    

}
