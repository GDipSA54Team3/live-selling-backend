package sg.edu.iss.restfulend.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Repository.*;
import java.text.DecimalFormat;



@CrossOrigin
@RestController
@RequestMapping("/api/rating")
public class RatingController {
	
	private static final DecimalFormat df = new DecimalFormat("0.0");

    @Autowired
    RatingRepository ratingRepo;
    UserRepository userRepo;
  
    @GetMapping("/avgrating")
    public String getAverageRating() {    	
    	Double avgrating = ratingRepo.getAverageRating();     
        if (avgrating != null) {
        	int ratingRounded = (int)Math.round(avgrating);
            return String.valueOf(ratingRounded);
        }      
        	//no rating yet        	
            return "0";
    }    
    @GetMapping("/userrating/{userId}")    
    public String getUserRating(@PathVariable("userId") String userId) { 
    	Double rating = ratingRepo.getAvgRatingByUserId(userId);
        if (rating != null) {
        	int ratingRounded = (int)Math.round(rating);
            return String.valueOf(ratingRounded);
        }   
        	//the user has no rating yet        	
            return "0";        
    }    
}
