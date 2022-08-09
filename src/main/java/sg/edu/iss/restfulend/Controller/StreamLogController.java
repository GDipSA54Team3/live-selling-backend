package sg.edu.iss.restfulend.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Repository.*;
import java.text.DecimalFormat;



@CrossOrigin
@RestController
@RequestMapping("/api/likes")
public class StreamLogController {
	
	private static final DecimalFormat df = new DecimalFormat("0.0");

    @Autowired
    StreamLogRepository logRepo;

  
    @GetMapping("/avgstreamlikes")
    public String getAverageLikes() {    	
    	Double avgLikes = logRepo.getAverageRating();     
        if (avgLikes != null) {
        	int avgLikesRounded = (int)Math.round(avgLikes);
            return String.valueOf(avgLikesRounded);
        }      
        	//no rating yet        	
            return "0";
    }    
    @GetMapping("/userlikes/{userId}")    
    public String getUserLikes(@PathVariable("userId") String userId) { 
    	Double avgUserLikes = logRepo.getAvgLikesByUserId(userId);
        if (avgUserLikes != null) {
        	int avgUserLikesRounded = (int)Math.round(avgUserLikes);
            return String.valueOf(avgUserLikesRounded);
        }   
        	//the user has no rating yet        	
            return "0";        
    }    
}
