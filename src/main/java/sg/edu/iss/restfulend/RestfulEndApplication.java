package sg.edu.iss.restfulend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.edu.iss.restfulend.Helper.DateTimeConverter;
import sg.edu.iss.restfulend.Helper.ProductCategories;
import sg.edu.iss.restfulend.Helper.StreamStatus;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@SpringBootApplication
public class RestfulEndApplication {

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
	@Autowired
	DateTimeConverter dtc;

	private static final Logger LOGGER = Logger.getLogger(RestfulEndApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(RestfulEndApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
//			clearDatabase();
			populateDatabase();
		};
	}

	public void clearDatabase() {
		buyerRepo.deleteAll();
		cartRepo.deleteAll();
		channelRepo.deleteAll();
		messageRepo.deleteAll();
		orderProductRepo.deleteAll();
		ordersRepo.deleteAll();
		productRepo.deleteAll();
		ratingRepo.deleteAll();
		sellerRepo.deleteAll();
		streamRepo.deleteAll();
		logRepo.deleteAll();
		LOGGER.info("----------------------------clearing Database");
	}

	public void populateDatabase() {
		ChannelStream cs1 = new ChannelStream("Better Living");
		channelRepo.save(cs1);
		Seller s1 = new Seller("Amanda", "Chong", "Upper Paya Lebar West", "amandachong", "amandachong", true, cs1);
		sellerRepo.save(s1);
		Cart cc1 = new Cart(s1);
		cartRepo.save(cc1);
		Product pcs1_1 = new Product("Aloe Vera Gel", ProductCategories.HEALTH, "Healing gel from South Korea. Used by BTS.", 50.00, 100, cs1);
		productRepo.save(pcs1_1);
		Product pcs1_2 = new Product("Collagen Face Mask", ProductCategories.HEALTH, "Get glowing skin in 1 week! Number 1 product in Busan.", 25.00, 100, cs1);
		productRepo.save(pcs1_2);
		Product pcs1_3 = new Product("Anti-aging body lotion", ProductCategories.HEALTH, "Get snow white skin. Number 1 product in Busan.", 25.00, 100, cs1);
		productRepo.save(pcs1_3);
		Stream ps1 = new Stream("Beauty Bazaar", LocalDateTime.now(), cs1, StreamStatus.ONGOING);
		streamRepo.save(ps1);
		ChannelStream cs2 = new ChannelStream("HighTech Gadgets");
		channelRepo.save(cs2);
		Seller s2 = new Seller("James", "Seah", "Punggol West Avenue 2", "jamesseah", "jamesseah", true, cs2);
		sellerRepo.save(s2);
		Cart cc2 = new Cart(s2);
		cartRepo.save(cc2);
		Product pcs2_1 = new Product("iPhone 22", ProductCategories.TECHNOLOGY, "Latest iPhone from the future", 1500.00, 2, cs2);
		productRepo.save(pcs2_1);
		Product pcs2_2 = new Product("MacBook Pro 15 M6", ProductCategories.TECHNOLOGY, "Latest MacBook from the future", 3500.00, 3, cs2);
		productRepo.save(pcs2_2);
		Product pcs2_3 = new Product("Playstation 5", ProductCategories.TECHNOLOGY, "Next-gen console from Sony", 799.00, 5, cs2);
		productRepo.save(pcs2_3);
		Stream ps2 = new Stream("Tech Bazaar", dtc.dateTimeConvert("04/09/2022 13:30"), cs2, StreamStatus.PENDING);
		streamRepo.save(ps2);
		LOGGER.info("----------------------------populating Sellers");

		Buyer b1 = new Buyer("Tom", "Tan", "Chua Chu Kang Avenue 2", "tomtan", "tomtan", false);
		buyerRepo.save(b1);
		Cart c1 = new Cart(b1);
		cartRepo.save(c1);
		OrderProduct op1_1 = new OrderProduct(1, pcs1_1, null, null, c1);
		orderProductRepo.save(op1_1);
		OrderProduct op1_2 = new OrderProduct(1, pcs1_2, null, null, c1);
		orderProductRepo.save(op1_2);
		OrderProduct op1_3 = new OrderProduct(1, pcs2_3, null, null, c1);
		orderProductRepo.save(op1_3);
		Buyer b2 = new Buyer("Melinda", "Mak", "Woodlands Avenue 2", "melmak", "melmak", false);
		buyerRepo.save(b2);
		Cart c2 = new Cart(b2);
		cartRepo.save(c2);
		Buyer b3 = new Buyer("Joe", "Lim", "Upper Changi Road 2", "joelim", "joelim", false);
		buyerRepo.save(b3);
		Cart c3 = new Cart(b3);
		cartRepo.save(c3);
		Buyer b4 = new Buyer("Jess", "Park", "Coorperation Drive Avenue 2", "jesspark", "jesspark", false);
		buyerRepo.save(b4);
		Cart c4 = new Cart(b4);
		cartRepo.save(c4);
		Buyer b5 = new Buyer("Peter", "Pang", "Sentosa Cove 2", "peterpang", "peterpang", false);
		buyerRepo.save(b5);
		Cart c5 = new Cart(b5);
		cartRepo.save(c5);
		Buyer b6 = new Buyer("Sarah", "Sim", "Sims Avenue 2", "sarahsim", "sarahsim", false);
		buyerRepo.save(b6);
		Cart c6 = new Cart(b6);
		cartRepo.save(c6);
		LOGGER.info("----------------------------populating Buyers");




	}


}
