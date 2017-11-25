package controller;

import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repo.LocationRepository;

/**
 * Created by Iulian on 11/25/2017.
 */


@RestController
@RequestMapping("/API/locations")
public class LocationRestController {

    @Autowired
    LocationRepository locationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void addLocation(@RequestBody Location location){
        locationRepository.save(location);
    }
}