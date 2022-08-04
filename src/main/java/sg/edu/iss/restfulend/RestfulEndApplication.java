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
	UserRepository UserRepo;
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

	private static final Logger LOGGER = Logger.getLogger(RestfulEndApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(RestfulEndApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			clearDatabase();
			populateDatabase();
		};
	}

	public void clearDatabase() {
		UserRepo.deleteAll();
		cartRepo.deleteAll();
		channelRepo.deleteAll();
		messageRepo.deleteAll();
		orderProductRepo.deleteAll();
		ordersRepo.deleteAll();
		productRepo.deleteAll();
		ratingRepo.deleteAll();
		streamRepo.deleteAll();
		logRepo.deleteAll();

		LOGGER.info("----------------------------clearing database");
	}

	public void populateDatabase() {

		User s1 = new User("Amanda", "Chong", "Upper Paya Lebar West", "amandachong", "amandachong", true);
		UserRepo.save(s1);
		Cart cc1 = new Cart(s1);
		cartRepo.save(cc1);
		ChannelStream cs1 = new ChannelStream("Better Living", s1);
		channelRepo.save(cs1);
		Product pcs1_1 = new Product("Aloe Vera Gel", ProductCategories.HEALTH, "Healing gel from South Korea. Used by BTS.", 50.00, 100, cs1);
		productRepo.save(pcs1_1);
		Product pcs1_2 = new Product("Collagen Face Mask", ProductCategories.HEALTH, "Get glowing skin in 1 week! Number 1 product in Busan.", 25.00, 100, cs1);
		productRepo.save(pcs1_2);
		Product pcs1_3 = new Product("Anti-aging body lotion", ProductCategories.HEALTH, "Get snow white skin. Number 1 product in Busan.", 25.00, 100, cs1);
		productRepo.save(pcs1_3);
		Stream ps1 = new Stream("Beauty Bazaar", LocalDateTime.now(), cs1, StreamStatus.ONGOING);
		streamRepo.save(ps1);

		User s2 = new User("James", "Seah", "Punggol West Avenue 2", "jamesseah", "jamesseah", true);
		UserRepo.save(s2);
		Cart cc2 = new Cart(s2);
		cartRepo.save(cc2);
		ChannelStream cs2 = new ChannelStream("HighTech Gadgets", s2);
		channelRepo.save(cs2);
		Product pcs2_1 = new Product("iPhone 22", ProductCategories.TECHNOLOGY, "Latest iPhone from the future", 1500.00, 2, cs2);
		productRepo.save(pcs2_1);
		Product pcs2_2 = new Product("MacBook Pro 15 M6", ProductCategories.TECHNOLOGY, "Latest MacBook from the future", 3500.00, 3, cs2);
		productRepo.save(pcs2_2);
		Product pcs2_3 = new Product("Playstation 5", ProductCategories.TECHNOLOGY, "Next-gen console from Sony", 799.00, 5, cs2);
		productRepo.save(pcs2_3);
		Stream ps2 = new Stream("Tech Bazaar", dtc.dateTimeConvert("04/09/2022 13:30"), cs2, StreamStatus.PENDING);
		streamRepo.save(ps2);

		User b1 = new User("Tom", "Tan", "Chua Chu Kang Avenue 2", "tomtan", "tomtan", false);
		UserRepo.save(b1);
		Cart c1 = new Cart(b1);
		cartRepo.save(c1);
		ChannelStream csb1 = new ChannelStream("tomtan", b1);
		channelRepo.save(csb1);
		OrderProduct op1_1 = new OrderProduct(1, pcs1_1, null, null, c1);
		orderProductRepo.save(op1_1);
		OrderProduct op1_2 = new OrderProduct(1, pcs1_2, null, null, c1);
		orderProductRepo.save(op1_2);
		OrderProduct op1_3 = new OrderProduct(1, pcs2_3, null, null, c1);
		orderProductRepo.save(op1_3);

		User b2 = new User("Melinda", "Mak", "Woodlands Avenue 2", "melmak", "melmak", false);
		UserRepo.save(b2);
		Cart c2 = new Cart(b2);
		cartRepo.save(c2);
		ChannelStream csb2 = new ChannelStream("melmak", b2);
		channelRepo.save(csb2);

		User b3 = new User("Joe", "Lim", "Upper Changi Road 2", "joelim", "joelim", false);
		UserRepo.save(b3);
		Cart c3 = new Cart(b3);
		cartRepo.save(c3);
		ChannelStream csb3 = new ChannelStream("joelim", b3);
		channelRepo.save(csb3);

		User b4 = new User("Jess", "Park", "Coorperation Drive Avenue 2", "jesspark", "jesspark", false);
		UserRepo.save(b4);
		Cart c4 = new Cart(b4);
		cartRepo.save(c4);
		ChannelStream csb4 = new ChannelStream("jesspark", b4);
		channelRepo.save(csb4);

		User b5 = new User("Peter", "Pang", "Sentosa Cove 2", "peterpang", "peterpang", false);
		UserRepo.save(b5);
		Cart c5 = new Cart(b5);
		cartRepo.save(c5);
		ChannelStream csb5 = new ChannelStream("peterpang", b5);
		channelRepo.save(csb5);

		User b6 = new User("Sarah", "Sim", "Sims Avenue 2", "sarahsim", "sarahsim", false);
		UserRepo.save(b6);
		Cart c6 = new Cart(b6);
		cartRepo.save(c6);
		ChannelStream csb6 = new ChannelStream("sarahsim", b6);
		channelRepo.save(csb6);

		LOGGER.info("----------------------------populating database");




	}


}
