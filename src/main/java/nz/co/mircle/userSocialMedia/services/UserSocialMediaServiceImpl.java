package nz.co.mircle.userSocialMedia.services;

import nz.co.mircle.userSocialMedia.dao.UserSocialMediaRepository;
import nz.co.mircle.userSocialMedia.model.UserSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jacktan on 17/06/17.
 */
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
