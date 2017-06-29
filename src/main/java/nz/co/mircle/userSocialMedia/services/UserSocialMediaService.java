package nz.co.mircle.userSocialMedia.services;

import nz.co.mircle.userSocialMedia.model.UserSocialMedia;

/** Lists of services that can be used to call the user social media repository. */
public interface UserSocialMediaService {
  void createUserSocialMedia(UserSocialMedia userSocialMedia);
}
