package repo;

import model.Role;
import model.User;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.Application;

import static org.junit.Assert.*;

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

    @Before
    public void setUpUsers(){
        users = new User[3];
        users[0] = new User("surname1", "firstname1", Role.EMPLOYEE, "email1", "pass1", true);
        users[1] = new User("surname2", "firstname2", Role.ADMIN, "email2", "pass2", false);
        users[2] = new User("surname3", "firstname3", Role.EMPLOYEE, "email3", "pass3", true);

        entityManager.persist(users[0]);
        entityManager.persist(users[1]);
    }

//    repo tests todo:
//      - long count()
//      - E save(E ent)
//      - void delete(E ent)
//      - void delete(int id)
//      - void deleteAll()
//      - boolean exists(Integer id)
//      - Iterable<E> findAll()
//      - E findOne(Integer id)
//      - repo specific methods

    @Test
    public void findUserByEmailAndPasswordActive() {
        assertNotNull(userRepository.findByEmailAndPasswordAndActive("email1", "pass1", true));
        assertNull(userRepository.findByEmailAndPasswordAndActive("blabla", "bla", true));
    }

    @Test
    public void countUsers() {
        assertEquals(userRepository.count(), 2);
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
        User newUser = users[2];

        User addedUser = userRepository.save(newUser);
        assertEquals(addedUser, newUser);

        User foundUser = userRepository.findOne(addedUser.getUserId());
        assertEquals(foundUser, newUser);

        assertThat(userRepository.findAll(), Matchers.contains(users[0], users[1], users[2]));

        userRepository.delete(addedUser.getUserId());
        assertNull(userRepository.findOne(addedUser.getUserId()));
    }
}
