package nz.co.mircle.profile.dao;

import nz.co.mircle.profile.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jack on 01/06/2017.
 */
@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
