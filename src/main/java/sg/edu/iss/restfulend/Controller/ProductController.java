package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Model.Product;
import sg.edu.iss.restfulend.Repository.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {

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

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/products/{prodId}")
    public ResponseEntity<Product> findProductById(@PathVariable("prodId") String prodId) {
        String s = prodId;
        Optional<Product> pData = productRepo.findById(s);
        if (pData.isPresent()) {
            return new ResponseEntity<>(pData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/edit/{prodId}")
    public ResponseEntity<Product> editProduct(@PathVariable("prodId") String prodId, @RequestBody Product product) {
        Optional<Product> pData = productRepo.findById(prodId);
        if (pData.isPresent()) {
            Product _product = pData.get();
            _product.setId(product.getId());
            _product.setName(product.getName());
            _product.setCategory(product.getCategory());
            _product.setDescription(product.getDescription());
            _product.setPrice(product.getPrice());
            return new ResponseEntity<>(productRepo.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addtostore/")
    public ResponseEntity<Product> addToStore(Product product) {
        try {
            Product p = productRepo.save(new Product(product.getName(), product.getCategory(), product.getDescription(),
                    product.getPrice(), product.getQuantity(), product.getChannel()));
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/products/{prodId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("prodId") String prodId) {
        try {
            productRepo.deletebyId(prodId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);

        }
    }
}










