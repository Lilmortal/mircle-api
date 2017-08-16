package nz.co.mircle.v1.api.socialMedia.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import nz.co.mircle.v1.api.socialMedia.dao.SocialMediaRepository;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.socialMedia.services.SocialMediaService;
import nz.co.mircle.v1.api.socialMedia.services.SocialMediaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/** Social media service layer test */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class SocialMediaServiceImplTest {
  private static final Long ID = Long.parseLong("1");

  private static final String SOCIAL_MEDIA_NAME = "Facebook";

  private static final String SOCIAL_MEDIA_LOGO = "Facebook_logo";

  @TestConfiguration
  static class SocialMediaServiceImplTestContextConfiguration {
    @Bean
    public SocialMediaService socialMediaService() {
      return new SocialMediaServiceImpl();
    }
  }

  @Autowired private SocialMediaService socialMediaService;

  @MockBean private SocialMediaRepository socialMediaRepository;

  @MockBean private SocialMedia socialMedia;

  @Before
  public void setup() {
    when(socialMedia.getName()).thenReturn(SOCIAL_MEDIA_NAME);
    when(socialMedia.getLogo()).thenReturn(SOCIAL_MEDIA_LOGO);

    when(socialMediaRepository.findById(ID)).thenReturn(socialMedia);
    when(socialMediaRepository.findByName(SOCIAL_MEDIA_NAME)).thenReturn(socialMedia);
  }

  @Test
  public void givenIdReturnSocialMedia() {
    SocialMedia result = socialMediaService.findSocialMedia(ID);
    assertThat(result.getName()).isEqualTo(SOCIAL_MEDIA_NAME);
    assertThat(result.getLogo()).isEqualTo(SOCIAL_MEDIA_LOGO);
  }

  @Test
  public void givenNameReturnSocialMedia() {
    SocialMedia result = socialMediaService.findSocialMedia(SOCIAL_MEDIA_NAME);
    assertThat(result.getName()).isEqualTo(SOCIAL_MEDIA_NAME);
    assertThat(result.getLogo()).isEqualTo(SOCIAL_MEDIA_LOGO);
  }
}
