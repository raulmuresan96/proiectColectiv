package controller;

import model.User;
import repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Raul on 23/11/2017.
 */

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @RequestMapping("/add")
    public void addUsers(){
        User user = new User();
        user.setFirstName("abc");
        user.setSurname("xyz");
        userRepository.save(user);
    }
}
