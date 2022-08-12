package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Helper.OrderStatus;
import sg.edu.iss.restfulend.Model.OrderProduct;
import sg.edu.iss.restfulend.Model.Orders;
import sg.edu.iss.restfulend.Repository.OrderProductRepository;
import sg.edu.iss.restfulend.Repository.OrdersRepository;

import java.util.List;
import java.util.stream.Collectors;



@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
    @Autowired
    OrdersRepository ordersRepo;
    @Autowired
    OrderProductRepository orderProdRepo;
  
    @GetMapping("/pendingorders/{userId}")
    public String getPendingOrdersCount(@PathVariable("userId") String userId) {    	
    	Integer pendingOrdersCount = ordersRepo.getPendingOrderCountBySeller(userId);    
            return String.valueOf(pendingOrdersCount);
    }
    
    @GetMapping("/purchases/{userId}")
    public ResponseEntity<List<Orders>> getUserPurchases(@PathVariable("userId") String userId) {
        List<Orders> allPurchases = ordersRepo.findAll()
        .stream()
        .filter(order -> order.getUser().getId().equals(userId))
        .sorted((o1, o2) -> o1.getOrderDateTime().compareTo(o2.getOrderDateTime()))
        .collect(Collectors.toList());
        
        return new ResponseEntity<>(allPurchases, HttpStatus.OK);
    }
    
    @GetMapping("/channelorders/{channelId}")
    public ResponseEntity<List<Orders>> getChannelOrders(@PathVariable("channelId") String channelId) {
        List<Orders> allOrders = ordersRepo.findAll()
        .stream()
        .filter(order -> order.getChannel().getId().equals(channelId) && (order.getStatus() == OrderStatus.PENDING))
        .sorted((o1, o2) -> o1.getOrderDateTime().compareTo(o2.getOrderDateTime()))
        .collect(Collectors.toList());
        
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("/channelordersuser/{userId}")
    public ResponseEntity<List<Orders>> getChannelOrdersByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(ordersRepo.findChannelOrdersByUserIdAndStatus(userId, OrderStatus.PENDING), HttpStatus.OK);
    }

    @GetMapping("/getorder/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("orderId") String orderId) {
        try {
            return new ResponseEntity<>(ordersRepo.findById(orderId).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateorderstatus/{orderId}/{status}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable("orderId") String orderId,
                                                    @PathVariable("status") String status) {
        try{
            Orders updateOrder = ordersRepo.findById(orderId).get();
//            OrderStatus state = (Objects.equals(status, "CONFIRMED")) ? OrderStatus.CONFIRMED : OrderStatus.PENDING;
            updateOrder.setStatus((status.equals("CONFIRMED")) ? OrderStatus.CONFIRMED : OrderStatus.PENDING);
            ordersRepo.save(updateOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @GetMapping("/products/{orderId}")
    public ResponseEntity<List<OrderProduct>> getProductsInOrder(@PathVariable("orderId") String orderId){
    	List<OrderProduct> productsInOrder = orderProdRepo.findAll()
    			.stream()
    			.filter(orderProduct -> orderProduct.getOrder().getId().equals(orderId))
    			.collect(Collectors.toList());
    	
    	return new ResponseEntity<>(productsInOrder, HttpStatus.OK);
    }

}
