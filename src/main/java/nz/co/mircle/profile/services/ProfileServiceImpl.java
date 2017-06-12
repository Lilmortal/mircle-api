package nz.co.mircle.profile.services;

import nz.co.mircle.profile.dao.ProfileRepository;
import nz.co.mircle.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jack on 01/06/2017.
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }
}
