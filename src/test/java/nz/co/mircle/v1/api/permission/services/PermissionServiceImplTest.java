package nz.co.mircle.v1.api.permission.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import nz.co.mircle.v1.api.permission.dao.PermissionRepository;
import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import nz.co.mircle.v1.api.permission.services.PermissionService;
import nz.co.mircle.v1.api.permission.services.PermissionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/** Permission service layer test. */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class PermissionServiceImplTest {
  private static final boolean HAS_ACCESS = true;

  @TestConfiguration
  static class PermissionServiceImplTestContextConfiguration {
    @Bean
    public PermissionService permissionService() {
      return new PermissionServiceImpl();
    }
  }

  @Autowired private PermissionService permissionService;

  @MockBean private PermissionRepository permissionRepository;

  @MockBean private SocialMedia socialMedia;

  @MockBean private Permission permission;

  @Before
  public void setup() {
    when(permission.getSocialMedia()).thenReturn(socialMedia);
    when(permission.isHasAccess()).thenReturn(HAS_ACCESS);

    when(permissionRepository.findBySocialMediaAndHasAccess(socialMedia, HAS_ACCESS))
        .thenReturn(permission);
  }

  @Test
  public void givenSocialMediaAndHasAccessReturnPermission() {
    Permission result = permissionService.findPermission(socialMedia, HAS_ACCESS);

    assertThat(result.getSocialMedia()).isEqualTo(socialMedia);
    assertThat(result.isHasAccess()).isEqualTo(HAS_ACCESS);
  }
}
