package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Helper.ProductCategories;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.util.Optional;

@CrossOrigin
//@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/product")
public class ProductController {

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

    @GetMapping("/details/{prodId}")
    public ResponseEntity<Product> findProductById(@PathVariable("prodId") String prodId) {
        Product selected = findProductById(prodId).getBody();
        return selected != null ? new ResponseEntity<>(selected, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/addtochannel/{channelId}/{prodId}/{qty}")
    public ResponseEntity<Product> addToChannel(@PathVariable("prodId") String prodId, @PathVariable("sellerId") String sellerId,
                                                @PathVariable("name") String name, @PathVariable("categories") ProductCategories category,
                                                @PathVariable("description") String description, @PathVariable("price") String price,
                                                @PathVariable("quantity") int quantity) {
        if (productRepo.findExistInChannel(sellerId, prodId) != null) {
            Product existingProdInChannel = productRepo.findExistInChannel(sellerId, prodId);
            int oldQty = existingProdInChannel.getQuantity();
            existingProdInChannel.setQuantity(oldQty + quantity);
            return new ResponseEntity<>(productRepo.save(existingProdInChannel), HttpStatus.OK);
        } else {
            Product addProdToChannel = new Product();
            addProdToChannel.setChannel(findUserById(sellerId).getChannel());
            return new ResponseEntity<>(productRepo.save(addProdToChannel), HttpStatus.OK);
        }
    }

    public User findUserById(String id) {
        Optional<User> seller = userRepo.findById(id);
        return seller.isPresent() ? seller.get() : null;

    }
}



