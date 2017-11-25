package controller;

import model.Role;
import model.User;
import org.springframework.web.bind.annotation.*;
import repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Raul on 23/11/2017.
 */

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/API/user")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void addUser(@RequestBody User user){
        userRepository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id){
        User userToDisable = userRepository.findOne(id);
        userToDisable.setActive(false);
        userRepository.save(userToDisable);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User verifyUser(@RequestBody User user){
        return userRepository.findByEmailAndPasswordAndActive(user.getEmail(),user.getPassword(),true);
    }



}
