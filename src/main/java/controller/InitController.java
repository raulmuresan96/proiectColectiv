package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.InitService;

/**
 * Created by Raul on 25/11/2017.
 */

@RestController
@RequestMapping("/API/init")
public class InitController {

    @Autowired
    private InitService service;

    @RequestMapping(method = RequestMethod.GET)
    public void init(){
        service.init();
    }
}
