import model.Role;
import model.User;

import org.hamcrest.Matchers;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import repo.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import service.UserService;
import start.Application;

import java.util.ArrayList;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTests {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired private UserService userService;

    @MockBean private UserRepository userRepository;

    private User[] users;

    @Before
    public void setUp() {
        users = new User[3];
        users[0] = new User("surname1","firstname1", Role.EMPLOYEE, "email1", "pass1", true);
        users[1] = new User("surname2","firstname2", Role.EMPLOYEE, "email2", "pass2", true);
        users[2] = new User("surname3","firstname3", Role.ADMIN, "email3", "pass3", false);

        users[0].setUserId(0);
        users[1].setUserId(1);
        users[2].setUserId(2);

        ArrayList<User> activeUsers = new ArrayList<>(2);
        activeUsers.add(users[0]);
        activeUsers.add(users[1]);

        Mockito.when(userRepository.findByActive(true)).thenReturn(activeUsers);

        Mockito.when(userRepository.save(users[0])).thenReturn(users[0]);
        Mockito.when(userRepository.save(users[1])).thenReturn(users[1]);

        Mockito.when(userRepository.findOne(0)).thenReturn(users[0]);
        Mockito.when(userRepository.findOne(1)).thenReturn(users[1]);

        Mockito.when(userRepository.findByEmailAndPasswordAndActive(users[0].getEmail(), users[0].getPassword(), true)).thenReturn(users[0]);
        Mockito.when(userRepository.findByEmailAndPasswordAndActive(users[2].getEmail(), users[2].getPassword(), true)).thenReturn(null);
    }

    @Test
    public void getUsers() {
        Iterable<User> activeUsers = userService.getUsers();

        assertThat(activeUsers, Matchers.contains(users[0], users[1]));
        assertThat(activeUsers, Matchers.not(Matchers.contains(users[2])));
    }

    @Test
    public void addUser() {
        assertEquals(userService.addUser(users[0]), users[0]);
        assertEquals(userService.addUser(users[1]), users[1]);
    }

    @Test
    public void deleteUser() {
        User disabledUser = userService.deleteUser(0);

        assertEquals(disabledUser.getUserId(), 0);
        assertEquals(disabledUser.getSurname(), users[0].getSurname());
        assertEquals(disabledUser.getFirstName(), users[0].getFirstName());
        assertEquals(disabledUser.getRole(), users[0].getRole());
        assertEquals(disabledUser.getEmail(), users[0].getEmail());
        assertEquals(disabledUser.getPassword(), users[0].getPassword());
        assertFalse(disabledUser.getActive());
    }

    @Test
    public void verifyUser() {
        assertNotNull(userService.verifyUser(users[0]));
        assertNull(userService.verifyUser(users[2]));
    }


}
