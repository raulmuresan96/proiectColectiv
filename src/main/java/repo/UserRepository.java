package repo;

import model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Raul on 23/11/2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
