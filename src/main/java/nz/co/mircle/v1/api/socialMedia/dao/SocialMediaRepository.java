package nz.co.mircle.v1.api.socialMedia.dao;

import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** CRUD operations on the social media table. */
@Repository
public interface SocialMediaRepository extends CrudRepository<SocialMedia, Long> {
  SocialMedia findById(Long id);

  SocialMedia findByName(String name);
}
