package sg.edu.iss.restfulend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sg.edu.iss.restfulend.Helper.DateTimeConverter;
import sg.edu.iss.restfulend.Helper.OrderStatus;
import sg.edu.iss.restfulend.Helper.ProductCategories;
import sg.edu.iss.restfulend.Helper.StreamStatus;
import sg.edu.iss.restfulend.Model.*;
import sg.edu.iss.restfulend.Repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootApplication
public class RestfulEndApplication {

	@Autowired
	UserRepository UserRepo;
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

		LOGGER.info("----------------------------Populating database.." );		
		
		User s1 = new User("Amanda", "Chong", "Upper Paya Lebar West", "amandachong", "amandachong", true);
		UserRepo.save(s1);
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
		ChannelStream csb1 = new ChannelStream("tomtan", b1);
		channelRepo.save(csb1);

		User b2 = new User("Melinda", "Mak", "Woodlands Avenue 2", "melmak", "melmak", false);
		UserRepo.save(b2);
		ChannelStream csb2 = new ChannelStream("melmak", b2);
		channelRepo.save(csb2);

		User b3 = new User("Joe", "Lim", "Upper Changi Road 2", "joelim", "joelim", false);
		UserRepo.save(b3);
		ChannelStream csb3 = new ChannelStream("joelim", b3);
		channelRepo.save(csb3);

		User b4 = new User("Jess", "Park", "Coorperation Drive Avenue 2", "jesspark", "jesspark", false);
		UserRepo.save(b4);
		ChannelStream csb4 = new ChannelStream("jesspark", b4);
		channelRepo.save(csb4);

		User b5 = new User("Peter", "Pang", "Sentosa Cove 2", "peterpang", "peterpang", false);
		UserRepo.save(b5);
		ChannelStream csb5 = new ChannelStream("peterpang", b5);
		channelRepo.save(csb5);

		User b6 = new User("Sarah", "Sim", "Sims Avenue 2", "sarahsim", "sarahsim", false);
		UserRepo.save(b6);
		ChannelStream csb6 = new ChannelStream("sarahsim", b6);
		channelRepo.save(csb6);
		
		//populate rating for dashboard
		Rating rs1 = new Rating(2, cs1, s1); //Amanda's rating
		Rating rs2 = new Rating(3, cs2, s2); //James's rating
		Rating rs3 = new Rating(4, cs2, s2); //James's another rating
		Rating rs4 = new Rating(1, cs2, s2); //James's another rating
		Rating rb1 = new Rating(5, csb1, b1); //Tom's rating
		Rating rb2 = new Rating(4, csb2, b2); //Melinda's rating
		Rating rb3 = new Rating(3, csb3, b3);  //Jee's rating
		Rating rb4 = new Rating(1, csb4, b4);  //Jess's rating
		Rating rb5 = new Rating(4, csb5, b5);  //Peter's rating
		Rating rb6 = new Rating(2, csb6, b6);  //Sarah's rating
		
		ratingRepo.save(rs1);ratingRepo.save(rs2);ratingRepo.save(rs3);ratingRepo.save(rs4);
		ratingRepo.save(rb1);ratingRepo.save(rb2);ratingRepo.save(rb3);
		ratingRepo.save(rb4);ratingRepo.save(rb5);ratingRepo.save(rb6);
		
		//populate orders to plot djshbord charts
		LOGGER.info("---------------------------This will take a while. Please wait" );
		
		User buyer1 = new User("Jack", "Lee", "Chua Chu Kang Avenue 2", "jacklee", "jacklee", false); //buyer
		UserRepo.save(buyer1);
		LocalDateTime startDateTime = LocalDateTime.of(2022,Month.JULY,1,1,30,40,50000); //2022-07-08T01:30:40.000050
		LocalDateTime now = LocalDateTime.now();
		Integer daysBetween = (int)Duration.between(startDateTime, now).toDays();
	
		for (int i =0; i <daysBetween; i++) {	
			Random r = new Random();
			int low = 1;
			int high = 20;
			int noOfOrders = r.nextInt(high-low) + low;			
			LocalDateTime datetime = startDateTime.plusDays(i);
			for (int j =0; j <noOfOrders; j++) {
				LocalDateTime datetime1 = datetime;
				LocalDateTime datetime2 = datetime1.plusHours(6);
				LocalDateTime datetime3 = datetime1.plusHours(12);
				LocalDateTime datetime4 = datetime1.plusHours(18);
				Orders order1 = new Orders(buyer1, datetime1, OrderStatus.CONFIRMED);
				Orders order2 = new Orders(buyer1, datetime2, OrderStatus.CONFIRMED);
				Orders order3 = new Orders(buyer1, datetime2, OrderStatus.CONFIRMED);
				Orders order4 = new Orders(buyer1, datetime2, OrderStatus.CONFIRMED);
				Orders order5 = new Orders(buyer1, datetime2, OrderStatus.CONFIRMED);
				Orders order6 = new Orders(buyer1, datetime2, OrderStatus.CONFIRMED);
				Orders order7 = new Orders(buyer1, datetime3, OrderStatus.CONFIRMED);
				Orders order8 = new Orders(buyer1, datetime3, OrderStatus.CONFIRMED);
				Orders order9 = new Orders(buyer1, datetime3, OrderStatus.CONFIRMED);
				Orders order10 = new Orders(buyer1, datetime4, OrderStatus.CONFIRMED);
				Orders order11 = new Orders(buyer1, datetime4, OrderStatus.CONFIRMED);
				ordersRepo.save(order1);	ordersRepo.save(order2);
				ordersRepo.save(order3);ordersRepo.save(order4);
				ordersRepo.save(order5);ordersRepo.save(order6);
				ordersRepo.save(order7);ordersRepo.save(order8);
				ordersRepo.save(order9);ordersRepo.save(order10);	
				ordersRepo.save(order11);
			}			
		}
		//populate Jame's streamlog for (initilal dashboard display)
		StreamLog streamlog1 = new StreamLog(50, s2, null ); //Jame's streamlog 
		StreamLog streamlog2 = new StreamLog(40, s2, null ); //Jame'sanother streamlog
		StreamLog streamlog3 = new StreamLog(25, s1, null ); //Melinda's streamlog
		StreamLog streamlog4 = new StreamLog(30, s1, null ); //Melinda's another streamlog
		StreamLog streamlog5 = new StreamLog(250, s1, null ); //Melinda's another streamlog
		logRepo.save(streamlog1);	logRepo.save(streamlog2);
		logRepo.save(streamlog3);logRepo.save(streamlog4);
		logRepo.save(streamlog5);		

		LOGGER.info("----------------------------Database populated successfully!");	        
    }
}
