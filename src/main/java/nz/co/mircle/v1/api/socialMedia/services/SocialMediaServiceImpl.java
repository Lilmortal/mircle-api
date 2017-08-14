package nz.co.mircle.v1.api.socialMedia.services;

import nz.co.mircle.v1.api.socialMedia.dao.SocialMediaRepository;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** List of social media services implementation that are used to call the repository. */
@Service
public class SocialMediaServiceImpl implements SocialMediaService {
  @Autowired private SocialMediaRepository socialMediaRepository;

  @Override
  public void createSocialMedia(SocialMedia socialMedia) {
    socialMediaRepository.save(socialMedia);
  }

  @Override
  public SocialMedia findSocialMedia(Long id) {
    return socialMediaRepository.findById(id);
  }

  @Override
  public SocialMedia findSocialMedia(String name) {
    return socialMediaRepository.findByName(name);
  }
}
