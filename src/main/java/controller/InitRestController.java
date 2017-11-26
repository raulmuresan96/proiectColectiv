package controller;

import model.Location;
import model.Report;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repo.LocationRepository;
import repo.ReportRepository;
import repo.UserRepository;

import java.util.Date;

/**
 * Created by Raul on 25/11/2017.
 */


@RestController
@RequestMapping("/API/init")
public class InitRestController {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportRepository reportRepository;

    @RequestMapping(method = RequestMethod.GET)
    public void getAllLocations(){
        userRepository.save(new User("Muresan", "Raul", Role.EMPLOYEE, "raul@gmail.com", "abc", true));
        userRepository.save(new User("Muresan", "Alexandra", Role.EMPLOYEE, "ale@gmail.com", "abc", true));
        userRepository.save(new User("Pavel", "Cosmin", Role.EMPLOYEE, "cosmin@gmail.com", "abc", true));
        userRepository.save(new User("Suciu", "Alex", Role.EMPLOYEE, "alex@gmail.com", "abc", true));

        locationRepository.save(new Location(10, 15, "Fortech CBC"));
        locationRepository.save(new Location(0, 0, "Delegatie"));
        locationRepository.save(new Location(0, 0, "De acasa"));
        locationRepository.save(new Location(20, 25, "Fortech Liberty"));

        reportRepository.save(new Report(userRepository.findOne(1), new Date(), new Date(), 0.1, locationRepository.findOne(1)));
        reportRepository.save(new Report(userRepository.findOne(2), new Date(), new Date(), 0.1, locationRepository.findOne(1)));
        reportRepository.save(new Report(userRepository.findOne(3), new Date(), new Date(), 0.1, locationRepository.findOne(2)));


    }


}