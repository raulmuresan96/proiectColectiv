package controller;

import com.sun.javafx.css.converters.PaintConverter;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repo.LocationRepository;
import repo.ReportRepository;
import repo.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by Ciprian on 11/25/2017.
 */
@RestController
public class ReportRestController {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;


    @RequestMapping(value = "/API/user/day", method = RequestMethod.POST)
    public boolean checkIfDayStarted(@RequestBody User user){

        Date date = new Date();
        return findByUserAndStartDate(user, date) != null;
    }

    @RequestMapping("/API/user/day/start")
    public void startDay(@RequestBody Report report){
        report.setStartDate(new Date());
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

    @RequestMapping(value = "/API/report/statistic", method = RequestMethod.POST)
    public List<Report> generateStatistics(@RequestBody ReportsStatistics reportsStatistics){
        List<Report> reports = new ArrayList<>();
        //checks if a Report is in the requested interval
        Predicate<Report> predicate = (entity) -> (entity.getStartDate().compareTo(reportsStatistics.getStartDate()) > 0) &&
                (entity.getEndDate().compareTo(reportsStatistics.getEndDate()) < 0);
        reportRepository.findAllByUserUserId(reportsStatistics.getIdUser()).forEach(key ->{
            if(predicate.test(key)){
                reports.add(key);
            }
        });
        return reports;
    }

    //TODO: Probably this method should exist in a Service
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
