package nz.co.mircle.userSocialMedia.dao;

import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD operations on the user social media table.
 */
@Repository
public interface UserSocialMediaRepository extends CrudRepository<UserSocialMedia, Long> {
}
