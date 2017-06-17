package nz.co.mircle.userSocialMedia.dao;

import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jacktan on 17/06/17.
 */
public interface UserSocialMediaRepository extends CrudRepository<UserSocialMedia, Long> {
}
