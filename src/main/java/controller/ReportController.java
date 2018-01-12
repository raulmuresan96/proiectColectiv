package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.ReportService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Ciprian on 11/25/2017.
 */
@RestController
public class ReportController {

    @Autowired
    private ReportService service;

    @RequestMapping(value = "/API/user/day", method = RequestMethod.POST)
    public Report checkIfDayStarted(@RequestBody User user){
        return service.checkIfDayStarted(user);
    }

    @RequestMapping(value = "/API/user/day/isFinished", method = RequestMethod.POST)
    public boolean checkIfDayFinished(@RequestBody User user){
        return service.checkIfDayFinished(user);
    }

    @RequestMapping(value = "/API/user/day/start",  method = RequestMethod.POST)
    public void startDay(@RequestBody Report report){
        service.startDay(report);
    }

    @RequestMapping(value = "/API/user/day/finish", method = RequestMethod.POST)
    public void finishDay(@RequestBody User user){
        service.finishDay(user);
    }

    @RequestMapping(value = "/API/report", method = RequestMethod.POST)
    public void updateReport(@RequestBody Report report){
        service.updateReport(report);
    }

    //@PathVariable(value="someID") String id,

    @RequestMapping(value = "/API/report/{idUser}", method = RequestMethod.GET)
    public Iterable<Report> getAllReportsByUser(@PathVariable(value="idUser") int idUser){
        return service.findAllByUserId(idUser);
    }

    @RequestMapping(value = "/API/report/statistic", method = RequestMethod.POST)
    public List<Report> generateStatistics(@RequestBody ReportsStatistics reportsStatistics){
        return service.generateStatistics(reportsStatistics);
    }

}
