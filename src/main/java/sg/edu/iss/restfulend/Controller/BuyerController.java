package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
//@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    @Autowired
    BuyerRepository buyerRepo;
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
    SellerRepository sellerRepo;
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

    @GetMapping("/addtocart/{buyerId}/{prodId}/{qty}")
    public ResponseEntity<OrderProduct> addToCart(@PathVariable("prodId") String prodId, @PathVariable("buyerId") String buyerId,
                                                  @PathVariable("qty") int qty) {
        if (orderProductRepo.findExistInCart(buyerId, prodId) != null) {
            OrderProduct existingProdInCart = orderProductRepo.findExistInCart(buyerId, prodId);
            int oldQty = existingProdInCart.getQuantity();
            existingProdInCart.setQuantity(oldQty + qty);
            return new ResponseEntity<>(orderProductRepo.save(existingProdInCart), HttpStatus.OK);
        } else {
            OrderProduct addProdToCart = new OrderProduct(qty);
            addProdToCart.setCart(findBuyerById(buyerId).getCart());
            addProdToCart.setProduct(findProductById(prodId));
            return new ResponseEntity<>(orderProductRepo.save(addProdToCart), HttpStatus.OK);
        }
    }

    @GetMapping("/viewcart/{buyerId}")
    public ResponseEntity<Cart> viewCart(@PathVariable("buyerId") String buyerId) {
        return new ResponseEntity<>(findBuyerById(buyerId).getCart(), HttpStatus.OK);
    }















    public Stream findStreamById(String id) {
        Optional<Stream> stream = streamRepo.findById(id);
        return stream.isPresent() ? stream.get() : null;
    }

    public Buyer findBuyerById(String id) {
        Optional<Buyer> buyer = buyerRepo.findById(id);
        return buyer.isPresent() ? buyer.get() : null;
    }

    public Product findProductById(String id) {
        Optional<Product> product = productRepo.findById(id);
        return product.isPresent() ? product.get() : null;
    }
}
