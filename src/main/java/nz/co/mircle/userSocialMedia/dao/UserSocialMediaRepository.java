package nz.co.mircle.userSocialMedia.dao;

import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD operations on the user social media table.
 */
public interface UserSocialMediaRepository extends CrudRepository<UserSocialMedia, Long> {
}
