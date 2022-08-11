package sg.edu.iss.restfulend.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sg.edu.iss.restfulend.Model.ChannelStream;
import sg.edu.iss.restfulend.Model.User;
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
    
    /* Setting up for StreamLog writing
    
    @PostMapping("/newStreamLog/{channelName}")
    public ResponseEntity<StreamLog> addNewLog(@RequestBody User newUser, @PathVariable("channelName") String channelName ) {
        try {
        	User user = userRepo.save(new User(newUser.getFirstName(), newUser.getLastName(), newUser.getAddress(), newUser.getUsername(), newUser.getPassword(), newUser.getIsVerified()));
        	ChannelStream channel = channelRepo.save(new ChannelStream(channelName, user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
     
     */
    
}
