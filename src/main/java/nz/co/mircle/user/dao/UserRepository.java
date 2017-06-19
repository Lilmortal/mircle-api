package nz.co.mircle.user.dao;

import nz.co.mircle.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * CRUD operations on the user table.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(Long id);
}
