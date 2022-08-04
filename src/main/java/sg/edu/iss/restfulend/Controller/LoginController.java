package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.iss.restfulend.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    UserRepository userRepo;
    
}
