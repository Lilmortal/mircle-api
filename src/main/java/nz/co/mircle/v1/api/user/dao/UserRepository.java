package nz.co.mircle.v1.api.user.dao;

import nz.co.mircle.v1.api.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** CRUD operations on the user table. */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findById(Long id);

  User findByEmailAddress(String emailAddress);

  void deleteById(Long id);
}
