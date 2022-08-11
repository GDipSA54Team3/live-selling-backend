package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Helper.ProductCategories;
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

    @GetMapping("/channelproducts/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userRepo.findById(userId).get().getChannel().getProducts(), HttpStatus.OK);
    }

    @GetMapping("/selectproduct/{prodId}")
    public ResponseEntity<Product> findProductById(@PathVariable("prodId") String prodId) {
        String s = prodId;
        Optional<Product> pData = productRepo.findById(s);
        if (pData.isPresent()) {
            return new ResponseEntity<>(pData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editproduct/{prodId}")
    public ResponseEntity<Product> editProduct(@PathVariable("prodId") String prodId, @RequestBody Product product) {
        Optional<Product> pData = productRepo.findById(prodId);
        if (pData.isPresent()) {
            Product _product = pData.get();
            String category = product.getCat();
            ProductCategories cat;
            switch(category) {
                case ("CLOTHING"):
                    cat = ProductCategories.CLOTHING;
                    break;
                case ("FOOD"):
                    cat = ProductCategories.FOOD;
                    break;
                case ("FURNITURES"):
                    cat = ProductCategories.FURNITURES;
                    break;
                case ("APPLIANCES"):
                    cat = ProductCategories.APPLIANCES;
                    break;
                case ("TECHNOLOGY"):
                    cat = ProductCategories.TECHNOLOGY;
                    break;
                case ("BABY"):
                    cat = ProductCategories.BABY;
                    break;
                case ("HEALTH"):
                    cat = ProductCategories.HEALTH;
                    break;
                case ("SPORTS"):
                    cat = ProductCategories.SPORTS;
                    break;
                case ("GROCERIES"):
                    cat = ProductCategories.GROCERIES;
                    break;
                case ("OTHERS"):
                    cat = ProductCategories.OTHERS;
                    break;
                default:
                    cat = ProductCategories.OTHERS;
                    break;
            }
            _product.setName(product.getName());
            _product.setCategory(cat);
            _product.setDescription(product.getDescription());
            _product.setPrice(product.getPrice());
            _product.setQuantity(product.getQuantity());
            return new ResponseEntity<>(productRepo.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addtostore/{userId}")
    public ResponseEntity<Product> addToStore(@PathVariable("userId") String userId, @RequestBody Product product) {
        try {
            String category = product.getCat();
            ProductCategories cat;
            switch(category) {
                case("CLOTHING"):
                    cat = ProductCategories.CLOTHING;
                    break;
                case("FOOD"):
                    cat = ProductCategories.FOOD;
                    break;
                case("FURNITURES"):
                    cat = ProductCategories.FURNITURES;
                    break;
                case("APPLIANCES"):
                    cat = ProductCategories.APPLIANCES;
                    break;
                case ("TECHNOLOGY"):
                    cat = ProductCategories.TECHNOLOGY;
                    break;
                case("BABY"):
                    cat = ProductCategories.BABY;
                    break;
                case("HEALTH"):
                    cat = ProductCategories.HEALTH;
                    break;
                case("SPORTS"):
                    cat = ProductCategories.SPORTS;
                    break;
                case("GROCERIES"):
                    cat = ProductCategories.GROCERIES;
                    break;
                case("OTHERS"):
                    cat = ProductCategories.OTHERS;
                    break;
                default:
                    cat = ProductCategories.OTHERS;
                    break;
            }
            Product p = productRepo.save(new Product(product.getName(), cat, product.getDescription(),
                    product.getPrice(), product.getQuantity(), userRepo.findById(userId).get().getChannel()));
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/products/{prodId}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("prodId") String prodId) {
        try {
            productRepo.deleteById(prodId);
            return new ResponseEntity<>(HttpStatus.OK);
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










