package nz.co.mircle.socialMedia.dao;

import nz.co.mircle.socialMedia.model.SocialMedia;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD operations on the social media table.
 */
public interface SocialMediaRepository extends CrudRepository<SocialMedia, Long> {
    SocialMedia findById(Long id);

    SocialMedia findByTitle(String title);
}
