package repo;

import model.Role;
import model.User;

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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    @Before
    public void setUp(){
        entityManager.persist(new User("surname", "lastname", Role.EMPLOYEE, "surname@example.com", "password", true));
        entityManager.persist(new User("surname2", "lastname2", Role.ADMIN, "surname2@example.com", "password", false));
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
        assertNotNull(userRepository.findByEmailAndPasswordAndActive("surname@example.com", "password", true));
        assertNull(userRepository.findByEmailAndPasswordAndActive("blabla", "bla", true));
    }
}
