package nz.co.mircle.socialMedia.services;

import nz.co.mircle.socialMedia.dao.SocialMediaRepository;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * List of social media services implementation that are used to call the repository.
 */
@Service
public class SocialMediaServiceImpl implements SocialMediaService {
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository) {
        this.socialMediaRepository = socialMediaRepository;
    }

    @Override
    public void createSocialMedia(SocialMedia socialMedia) {
        socialMediaRepository.save(socialMedia);
    }

    @Override
    public SocialMedia findSocialMedia(Long id) {
        return socialMediaRepository.findById(id);
    }

    @Override
    public SocialMedia findSocialMedia(String title) {
        return socialMediaRepository.findByTitle(title);
    }
}
