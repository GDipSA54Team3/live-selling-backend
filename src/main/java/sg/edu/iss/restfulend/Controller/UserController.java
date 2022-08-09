package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Helper.DateTimeConverter;
import sg.edu.iss.restfulend.Helper.StreamStatus;
import sg.edu.iss.restfulend.Helper.UserSortByName;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins= "*")
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
    @Autowired
    DateTimeConverter dtc;

    @GetMapping("/streams")
    public List<Stream> getAllStreams() {
        return streamRepo.findAll();
    }

    @GetMapping("/userstreams/{userId}")
    public ResponseEntity<List<Stream>> getAllUserStreams(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(channelRepo.getStreamsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/userstreamspending/{userId}")
    public ResponseEntity<List<Stream>> getAllUserStreamsPending(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(streamRepo.getStreamsByUserIdAndStatus(userId, StreamStatus.PENDING), HttpStatus.OK);
    }

    @DeleteMapping("/deletestream/{streamId}")
    public ResponseEntity<HttpStatus> deleteStream(@PathVariable("streamId") String streamId) {
        Stream selected = findStreamById(streamId);
        streamRepo.delete(selected);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping("/addtocart/{userId}/{prodId}/{qty}")
    public ResponseEntity<OrderProduct> addToCart(@PathVariable("prodId") String prodId, @PathVariable("userId") String userId,
                                                  @PathVariable("qty") int qty) {
        if (orderProductRepo.findExistInCart(userId, prodId) != null) {
            OrderProduct existingProdInCart = orderProductRepo.findExistInCart(userId, prodId);
            int oldQty = existingProdInCart.getQuantity();
            existingProdInCart.setQuantity(oldQty + qty);
            return new ResponseEntity<>(orderProductRepo.save(existingProdInCart), HttpStatus.OK);
        } else {
            OrderProduct addProdToCart = new OrderProduct(qty);
            addProdToCart.setCart(findUserById(userId).getCart());
            addProdToCart.setProduct(findProductById(prodId));
            return new ResponseEntity<>(orderProductRepo.save(addProdToCart), HttpStatus.OK);
        }
    }

    @GetMapping("/viewcart/{userId}")
    public ResponseEntity<List<OrderProduct>> viewCart(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(findUserById(userId).getCart().getOrderProduct(), HttpStatus.OK);
    }

    @PutMapping("/editcartqty/{orderProdId}/{qty}")
    public ResponseEntity<OrderProduct> editCartQty(@PathVariable("orderProdId") String orderProdId, @PathVariable("qty") int qty) {
        OrderProduct op = findOrderProductById(orderProdId);
        op.setQuantity(qty);
        return new ResponseEntity<>(orderProductRepo.save(op), HttpStatus.OK);
    }

    @DeleteMapping("/removecartitem/{orderProdId}")
    public ResponseEntity<HttpStatus> removeCartItem(@PathVariable("orderProdId") String orderProdId) {
        try {
            orderProductRepo.deleteById(orderProdId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    //NOT WORKING
    @DeleteMapping("/emptycart/{userId}")
    public ResponseEntity<HttpStatus> emptyCart(@PathVariable("userId") String userId) {
        try {
            List<OrderProduct> cartItems = findUserById(userId).getCart().getOrderProduct();
            for (OrderProduct item : cartItems) {
                orderProductRepo.delete(item);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/addstream/{userId}")
    public ResponseEntity<Stream> addNewStream(@RequestBody Stream newStream, @PathVariable("userId") String userId) {
        try {
            Stream stream = streamRepo.save(new Stream(newStream.getTitle(), dtc.dateTimeConvertClient(newStream.getTempSchedule()), findUserById(userId).getChannel(), StreamStatus.PENDING));
            return new ResponseEntity<>(stream, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/editstream/{streamId}")
    public ResponseEntity<Stream> editStream(@RequestBody Stream stream, @PathVariable("streamId") String streamId) {
        try {
            Stream selectStream = findStreamById(streamId);
            selectStream.setTitle(stream.getTitle());
            streamRepo.save(selectStream);
            selectStream.setSchedule(dtc.dateTimeConvertClient(stream.getTempSchedule()));
            streamRepo.save(selectStream);
            return new ResponseEntity<>(selectStream, HttpStatus.OK);
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

    public User findUserById(String id) {
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
    
    @GetMapping("/upcomingstreams/{userId}")
    public ResponseEntity<List<Stream>> getThreeClosestUserStreamsPending(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(streamRepo.getThreeClosestStreamsByUserId(userId), HttpStatus.OK);
    }
    
    @GetMapping("/upcomingstreamcount/{userId}")
    public String PendingStreamCountByUser(@PathVariable("userId") String userId) {
    	Integer pendingStreamCount = streamRepo.getPendingStreamCountByUser(userId);
        return String.valueOf(pendingStreamCount);
    }    
    
}
