import model.Report;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import model.Location;
import model.Role;
import model.User;

import repo.LocationRepository;
import repo.ReportRepository;
import repo.UserRepository;

import start.Application;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {
    @Autowired
    TestEntityManager entityManager;

    @Autowired UserRepository userRepository;
    @Autowired LocationRepository locationRepository;
    @Autowired ReportRepository reportRepository;

    private User[] users;
    private Location[] locations;
    private Report[] reports;

    @Before
    public void setUp(){
        users = new User[3];
        users[0] = new User("surname1", "firstname1", Role.EMPLOYEE, "email1", "pass1", true);
        users[1] = new User("surname2", "firstname2", Role.ADMIN, "email2", "pass2", false);
        users[2] = new User("surname3", "firstname3", Role.EMPLOYEE, "email3", "pass3", true);

        users[0] = entityManager.persist(users[0]);
        users[1] = entityManager.persist(users[1]);

        locations = new Location[3];
        locations[0] = new Location(0, 0, "location1");
        locations[1] = new Location(1, 1, "location2");
        locations[2] = new Location(2, 2, "location3");

        locations[0] = entityManager.persist(locations[0]);
        locations[1] = entityManager.persist(locations[1]);

        reports = new Report[3];
        reports[0] = new Report(users[0], new Date(0), new Date(1), 1, locations[0]);
        reports[1] = new Report(users[1], new Date(10), new Date(11), 2, locations[1]);
        reports[2] = new Report(users[2], new Date(100), new Date(101), 3, locations[2]);

        reports[0] = entityManager.persist(reports[0]);
        reports[1] = entityManager.persist(reports[1]);
    }

    @Test
    public void countUsers() {
        assertEquals(userRepository.count(), 2);
    }

    @Test
    public void findUserByEmailAndPasswordActive() {
        assertNotNull(userRepository.findByEmailAndPasswordAndActive("email1", "pass1", true));
        assertNull(userRepository.findByEmailAndPasswordAndActive("blabla", "bla", true));
    }

    @Test
    public void findUserByActive() {
        Iterable<User> activeUsers = userRepository.findByActive(true);
        assertThat(activeUsers, Matchers.contains(users[0]));
        assertThat(activeUsers, Matchers.not(Matchers.contains(users[1])));

        Iterable<User> inactiveUsers = userRepository.findByActive(false);
        assertThat(inactiveUsers, Matchers.contains(users[1]));
        assertThat(inactiveUsers, Matchers.not(Matchers.contains(users[0])));
    }

    @Test
    public void crudScenarioUsers() {
        assertEquals(userRepository.count(), 2);

        User newUser = users[2];

        User addedUser = userRepository.save(newUser);
        assertEquals(addedUser, newUser);

        assertEquals(userRepository.count(), 3);

        User foundUser = userRepository.findOne(addedUser.getUserId());
        assertEquals(foundUser, newUser);

        assertThat(userRepository.findAll(), Matchers.contains(users[0], users[1], users[2]));

        userRepository.delete(addedUser.getUserId());
        assertNull(userRepository.findOne(addedUser.getUserId()));

        assertEquals(userRepository.count(), 2);
    }

    @Test
    public void updateUser() {
        User userToUpdate = new User("surname4", "firstname4", Role.EMPLOYEE, "email4", "pass4", false);
        userToUpdate.setUserId(users[0].getUserId());

        long countBeforeUpdate = userRepository.count();
        User updatedUser = userRepository.save(userToUpdate);
        long countAfterUpdate = userRepository.count();

        assertEquals(countBeforeUpdate, countAfterUpdate);

        assertEquals(updatedUser.getUserId(), userToUpdate.getUserId());
        assertEquals(updatedUser.getSurname(), userToUpdate.getSurname());
        assertEquals(updatedUser.getFirstName(), userToUpdate.getFirstName());
        assertEquals(updatedUser.getRole(), userToUpdate.getRole());
        assertEquals(updatedUser.getEmail(), userToUpdate.getEmail());
        assertEquals(updatedUser.getPassword(), userToUpdate.getPassword());
        assertEquals(updatedUser.getActive(), userToUpdate.getActive());

        User foundUpdatedUser = userRepository.findOne(users[0].getUserId());

        assertEquals(foundUpdatedUser.getUserId(), userToUpdate.getUserId());
        assertEquals(foundUpdatedUser.getSurname(), userToUpdate.getSurname());
        assertEquals(foundUpdatedUser.getFirstName(), userToUpdate.getFirstName());
        assertEquals(foundUpdatedUser.getRole(), userToUpdate.getRole());
        assertEquals(foundUpdatedUser.getEmail(), userToUpdate.getEmail());
        assertEquals(foundUpdatedUser.getPassword(), userToUpdate.getPassword());
        assertEquals(foundUpdatedUser.getActive(), userToUpdate.getActive());
    }

    @Test
    public void countLocations() {
        assertEquals(locationRepository.count(), 2);
    }

    @Test
    public void crudScenarioLocations() {
        assertEquals(locationRepository.count(), 2);

        Location newLocation = locations[2];

        Location addedLocation = locationRepository.save(newLocation);
        assertEquals(addedLocation, newLocation);

        assertEquals(locationRepository.count(), 3);

        Location foundLocation = locationRepository.findOne(addedLocation.getLocationId());
        assertEquals(foundLocation, newLocation);

        assertThat(locationRepository.findAll(), Matchers.contains(locations[0], locations[1], locations[2]));

        locationRepository.delete(addedLocation.getLocationId());
        assertNull(locationRepository.findOne(addedLocation.getLocationId()));

        assertEquals(locationRepository.count(), 2);
    }

    @Test
    public void countReports() {
        assertEquals(reportRepository.count(), 2);
    }

    @Test
    public void crudScenarioReports() {
        assertEquals(reportRepository.count(), 2);

        Report newReport = reports[2];

        Report addedReport = reportRepository.save(newReport);
        assertEquals(addedReport, newReport);

        assertEquals(locationRepository.count(), 3);

        Report foundReport = reportRepository.findOne(addedReport.getRaportId());
        assertEquals(foundReport, newReport);

        assertThat(reportRepository.findAll(), Matchers.contains(reports[0], reports[1], reports[2]));

        reportRepository.delete(addedReport.getRaportId());
        assertNull(reportRepository.findOne(addedReport.getRaportId()));

        assertEquals(reportRepository.count(), 2);
    }

    @Test
    public void findReportsByUserId() {
        Iterable<Report> reportsOfUser0 = reportRepository.findAllByUserUserId(users[0].getUserId());
        assertThat(reportsOfUser0, Matchers.iterableWithSize(1));
        assertThat(reportsOfUser0, Matchers.contains(reports[0]));

        Iterable<Report> reportsOfUser1 = reportRepository.findAllByUserUserId(users[1].getUserId());
        assertThat(reportsOfUser1, Matchers.iterableWithSize(1));
        assertThat(reportsOfUser1, Matchers.contains(reports[1]));

        Iterable<Report> reportsOfUser2 = reportRepository.findAllByUserUserId(users[2].getUserId());
        assertThat(reportsOfUser2, Matchers.emptyIterable());
    }
}
