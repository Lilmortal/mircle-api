package nz.co.mircle.v1.api.userSocialMedia.services;

import nz.co.mircle.v1.api.userSocialMedia.dao.UserSocialMediaRepository;
import nz.co.mircle.v1.api.userSocialMedia.model.UserSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** List of user social media services implementation that are used to call the repository. */
@Service
public class UserSocialMediaServiceImpl implements UserSocialMediaService {
  @Autowired private UserSocialMediaRepository userSocialMediaRepository;

  @Override
  public void createUserSocialMedia(UserSocialMedia userSocialMedia) {
    userSocialMediaRepository.save(userSocialMedia);
  }
}
