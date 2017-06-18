package nz.co.mircle.permission.dao;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Permission repository test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PermissionRepositoryTest {
    private static final String SOCIAL_MEDIA_NAME = "facebook";

    private static final String SOCIAL_MEDIA_LOGO = "facebook_logo";

    private static final boolean HAS_ACCESS = true;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    @MockBean
    private SocialMedia socialMedia;

    @Before
    public void setup() {
        when(socialMedia.getName()).thenReturn(SOCIAL_MEDIA_NAME);
        when(socialMedia.getLogo()).thenReturn(SOCIAL_MEDIA_LOGO);
    }

    @Test
    public void givenSocialMediaAndHasAccessReturnPermission() {
        Permission permission = new Permission(socialMedia, HAS_ACCESS);
        entityManager.persist(permission);
        entityManager.flush();

        Permission result = permissionRepository.findBySocialMediaAndHasAccess(socialMedia, HAS_ACCESS);

        assertThat(result.getSocialMedia()).isEqualTo(socialMedia);
        assertThat(result.isHasAccess()).isEqualTo(HAS_ACCESS);
    }
}
