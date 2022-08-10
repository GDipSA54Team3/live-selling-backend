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
import java.util.stream.Collectors;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepo;
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
    
    @GetMapping("/channels/finduser/{userId}")
    public ResponseEntity<ChannelStream> findChannelByUserId(@PathVariable("userId") String userId) {
    	List<ChannelStream> allChannels = 
    	channelRepo.findAll()
    		.stream()
    		.filter(x -> x.getUser().getId().equals(userId))
    		.collect(Collectors.toList());
    	
        ChannelStream selected = allChannels.get(0);
        return selected != null ? new ResponseEntity<>(selected, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    //Gab's API for Registration
    @PostMapping("/register/{channelName}")
    public ResponseEntity<User> addNewUser(@RequestBody User newUser, @PathVariable("channelName") String channelName ) {
        try {
        	User user = userRepo.save(new User(newUser.getFirstName(), newUser.getLastName(), newUser.getAddress(), newUser.getUsername(), newUser.getPassword(), newUser.getIsVerified()));
        	ChannelStream channel = channelRepo.save(new ChannelStream(channelName, user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
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
