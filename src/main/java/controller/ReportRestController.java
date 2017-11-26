package controller;

import com.sun.javafx.css.converters.PaintConverter;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by Ciprian on 11/25/2017.
 */
@RestController
public class ReportRestController {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;


    @RequestMapping(value = "/API/user/day", method = RequestMethod.POST)
    public boolean checkIfDayStarted(@RequestBody User user){

        Date date = new Date();
        return findByUserAndStartDate(user, date) != null;
    }

    static class UserLocation{
        User user;
        Location location;
        public UserLocation(){

        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public UserLocation(User user, Location location){
            this.location = location;
            this.user = user;
        }
    }


    @RequestMapping("/API/user/day/start")
    public void startDay(@RequestBody UserLocation userLocation){

        Report report = new Report(userLocation.user, new Date(), userLocation.location);
        reportRepository.save(report);
    }

    @RequestMapping(value = "/API/user/day/finish", method = RequestMethod.POST)
    public void finishDay(@RequestBody User user){
        Report report = findByUserAndStartDate(user, new Date());
        Date date = new Date();
        report.setEndDate(date);
        report.setHours( 1.0 * (date.getTime() -report.getStartDate().getTime()) / 1000 / 3600);
        reportRepository.save(report);
    }

    @RequestMapping(value = "/API/report", method = RequestMethod.POST)
    public void updateReport(@RequestBody Report report){
        reportRepository.save(report);
    }

    private Report findByUserAndStartDate(User user, Date date){
        for(Report report: reportRepository.findAllByUserUserId(user.getUserId())){
            if(report.getStartDate().getDay() == date.getDay() &&
                    report.getStartDate().getMonth() == date.getMonth() &&
                    report.getStartDate().getYear() == date.getYear()
                    )
            {
                return report;
            }
        }
        return null;
    }
}
