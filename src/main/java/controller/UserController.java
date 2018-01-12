package controller;

import model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import service.UserService;

/**
 * Created by Raul on 23/11/2017.
 */

@RestController
@RequestMapping("/API/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getUsers(){
        return service.getUsers();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void addUser(@RequestBody User user){
        service.addUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id){
        service.deleteUser(id);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User verifyUser(@RequestBody User user){
        User toReturnUser = service.verifyUser(user);
        if(toReturnUser == null){
            toReturnUser = new User();
            toReturnUser.setUserId(-1);
        }
        return toReturnUser;
    }

}
