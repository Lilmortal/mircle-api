package nz.co.mircle.v1.api.userSocialMedia.services;

import nz.co.mircle.v1.api.userSocialMedia.model.UserSocialMedia;

/** Lists of services that can be used to call the user social media repository. */
public interface UserSocialMediaService {
  void createUserSocialMedia(UserSocialMedia userSocialMedia);
}
