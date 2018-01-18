package service;

import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Iterable<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public Location addLocation(Location location){
        return locationRepository.save(location);
    }

}
