package nz.co.mircle.user.dao;

import nz.co.mircle.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jack on 01/06/2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(Long id);
}
