package nz.co.mircle.v1.api.userSocialMedia.dao;

import nz.co.mircle.v1.api.userSocialMedia.model.UserSocialMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** CRUD operations on the user social media table. */
@Repository
public interface UserSocialMediaRepository extends CrudRepository<UserSocialMedia, Long> {}
