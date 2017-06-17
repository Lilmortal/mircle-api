package nz.co.mircle.userSocialMedia.services;

import nz.co.mircle.userSocialMedia.dao.UserSocialMediaRepository;
import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of user social media services implementation that are used to call the repository.
 */
@Service
public class UserSocialMediaServiceImpl implements UserSocialMediaService {
    private UserSocialMediaRepository userSocialMediaRepository;

    @Autowired
    public UserSocialMediaServiceImpl(UserSocialMediaRepository userSocialMediaRepository) {
        this.userSocialMediaRepository = userSocialMediaRepository;
    }

    @Override
    public void createUserSocialMedia(UserSocialMedia userSocialMedia) {
        userSocialMediaRepository.save(userSocialMedia);
    }
}
