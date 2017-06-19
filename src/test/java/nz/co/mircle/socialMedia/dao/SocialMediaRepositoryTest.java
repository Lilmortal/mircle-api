package nz.co.mircle.socialMedia.dao;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Social media repository test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SocialMediaRepositoryTest {
    private static final Long ID = Long.parseLong("1");

    private static final String SOCIAL_MEDIA_NAME = "Facebook";

    private static final String SOCIAL_MEDIA_LOGO = "Facebook_logo";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Test
    public void givenIdReturnSocialMedia() {
        SocialMedia socialMedia = new SocialMedia(SOCIAL_MEDIA_NAME, SOCIAL_MEDIA_LOGO);
        entityManager.persist(socialMedia);
        entityManager.flush();

        SocialMedia result = socialMediaRepository.findById(ID);
        assertThat(result.getName()).isEqualTo(socialMedia.getName());
        assertThat(result.getLogo()).isEqualTo(socialMedia.getLogo());
    }
}
