package nz.co.mircle.v1.api.user.dao;

import nz.co.mircle.v1.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/** CRUD operations on the user table. */
@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
  User findById(Long id);

  User findByEmailAddress(String emailAddress);

  //List<User> findByFriendId(Long id);

  void deleteById(Long id);
}
