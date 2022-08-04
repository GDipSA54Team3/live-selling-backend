package sg.edu.iss.restfulend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.restfulend.Model.LoginBag;
import sg.edu.iss.restfulend.Model.User;
import sg.edu.iss.restfulend.Repository.UserRepository;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<User> loginCheck(@RequestBody LoginBag loginUser, HttpSession session) {
        if (userRepo.findUserByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword()) != null) {
            User user = userRepo.findUserByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
            session.setAttribute("loggedInUser", user);
            return new ResponseEntity<>((User) session.getAttribute("loggedInUser"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logoutUser(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public ResponseEntity<User> getLoggedInUser(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null){
            return new ResponseEntity<>((User) session.getAttribute("loggedInUser"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
