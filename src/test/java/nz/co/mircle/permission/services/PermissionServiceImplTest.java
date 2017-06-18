package nz.co.mircle.permission.services;

import nz.co.mircle.permission.dao.PermissionRepository;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Permission service layer test.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class PermissionServiceImplTest {
    private static final SocialMedia SOCIAL_MEDIA = new SocialMedia("facebook", "facebook_logo");

    private static final boolean HAS_ACCESS = true;

    @MockBean
    private PermissionRepository permissionRepository;

    @TestConfiguration
    static class PermissionServiceImplTestContextConfiguration {
        @Bean
        public PermissionService permissionService() {
            return new PermissionServiceImpl();
        }
    }

    @Autowired
    private PermissionService permissionService;

    @Before
    public void setup() {
        Permission permission = new Permission(SOCIAL_MEDIA, HAS_ACCESS);
        when(permissionRepository.findBySocialMediaAndHasAccess(SOCIAL_MEDIA, HAS_ACCESS)).thenReturn(permission);
    }

    @Test
    public void givenSocialMediaAndHasAccessReturnPermission() {
        Permission result = permissionService.findPermission(SOCIAL_MEDIA, HAS_ACCESS);

        assertThat(result.getSocialMedia()).isEqualTo(SOCIAL_MEDIA);
        assertThat(result.isHasAccess()).isEqualTo(HAS_ACCESS);
    }
}
