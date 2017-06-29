package nz.co.mircle.socialMedia.services;

import nz.co.mircle.socialMedia.model.SocialMedia;

/** Lists of services that can be used to call the social media repository. */
public interface SocialMediaService {
  void createSocialMedia(SocialMedia socialMedia);

  SocialMedia findSocialMedia(Long id);

  SocialMedia findSocialMedia(String name);
}
