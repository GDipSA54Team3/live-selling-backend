package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Helper.UserSortByName;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
//@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    ChannelStreamRepository channelRepo;
    @Autowired
    MessageRepository messageRepo;
    @Autowired
    OrderProductRepository orderProductRepo;
    @Autowired
    OrdersRepository ordersRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    RatingRepository ratingRepo;
    @Autowired
    StreamRepository streamRepo;
    @Autowired
    StreamLogRepository logRepo;

    @GetMapping("/streams")
    public List<Stream> getAllStreams() {
        return streamRepo.findAll();
    }

    @GetMapping("/channels")
    public List<ChannelStream> getAllChannels() {
        return channelRepo.findAll();
    }

    @GetMapping("/streams/{streamId}")
    public ResponseEntity<Stream> selectStream(@PathVariable("streamId") String streamId) {
        Stream selected = findStreamById(streamId);
        return selected != null ? new ResponseEntity<>(selected, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/channels/{channelId}")
    public ResponseEntity<ChannelStream> selectChannel(@PathVariable("channelId") String channelId) {
        ChannelStream selected = findChannelById(channelId);
        return selected != null ? new ResponseEntity<>(selected, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/addtocart/{userId}/{prodId}/{qty}")
    public ResponseEntity<OrderProduct> addToCart(@PathVariable("prodId") String prodId, @PathVariable("userId") String userId,
                                                  @PathVariable("qty") int qty) {
        if (orderProductRepo.findExistInCart(userId, prodId) != null) {
            OrderProduct existingProdInCart = orderProductRepo.findExistInCart(userId, prodId);
            int oldQty = existingProdInCart.getQuantity();
            existingProdInCart.setQuantity(oldQty + qty);
            return new ResponseEntity<>(orderProductRepo.save(existingProdInCart), HttpStatus.OK);
        } else {
            OrderProduct addProdToCart = new OrderProduct(qty);
            addProdToCart.setCart(findBuyerById(userId).getCart());
            addProdToCart.setProduct(findProductById(prodId));
            return new ResponseEntity<>(orderProductRepo.save(addProdToCart), HttpStatus.OK);
        }
    }

    @GetMapping("/viewcart/{userId}")
    public ResponseEntity<List<OrderProduct>> viewCart(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(findBuyerById(userId).getCart().getOrderProduct(), HttpStatus.OK);
    }

    @GetMapping("/editcartqty/{orderProdId}/{qty}")
    public ResponseEntity<OrderProduct> editCartQty(@PathVariable("orderProdId") String orderProdId, @PathVariable("qty") int qty) {
        OrderProduct op = findOrderProductById(orderProdId);
        op.setQuantity(qty);
        return new ResponseEntity<>(orderProductRepo.save(op), HttpStatus.OK);
    }

    @GetMapping("/removecartitem/{orderProdId}")
    public ResponseEntity<HttpStatus> removeCartItem(@PathVariable("orderProdId") String orderProdId) {
        try {
            orderProductRepo.deleteById(orderProdId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    //NOT WORKING
    @GetMapping("/emptycart/{userId}")
    public ResponseEntity<HttpStatus> emptyCart(@PathVariable("userId") String userId) {
        try {
            List<OrderProduct> cartItems = findBuyerById(userId).getCart().getOrderProduct();
            for (OrderProduct item : cartItems) {
                orderProductRepo.delete(item);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    //TESTING PURPOSES ONLY
    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepo.findAll();
        users.sort(new UserSortByName());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    //OTHER METHODS
    public Stream findStreamById(String id) {
        Optional<Stream> stream = streamRepo.findById(id);
        return stream.isPresent() ? stream.get() : null;
    }

    public User findBuyerById(String id) {
        Optional<User> buyer = userRepo.findById(id);
        return buyer.isPresent() ? buyer.get() : null;
    }

    public Product findProductById(String id) {
        Optional<Product> product = productRepo.findById(id);
        return product.isPresent() ? product.get() : null;
    }

    public OrderProduct findOrderProductById(String id) {
        Optional<OrderProduct> oProduct = orderProductRepo.findById(id);
        return oProduct.isPresent() ? oProduct.get() : null;
    }

    public ChannelStream findChannelById(String id) {
        Optional<ChannelStream> channel = channelRepo.findById(id);
        return channel.isPresent() ? channel.get() : null;
    }
}