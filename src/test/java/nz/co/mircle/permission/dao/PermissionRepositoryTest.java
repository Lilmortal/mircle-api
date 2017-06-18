package nz.co.mircle.permission.dao;

import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.socialMedia.model.SocialMedia;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Permission repository test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PermissionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void givenSocialMediaAndHasAccessReturnPermission() {
        SocialMedia socialMedia = new SocialMedia("facebook", "facebook_logo");
        boolean hasAccess = true;

        Permission permission = new Permission(socialMedia, hasAccess);
        entityManager.persist(permission);
        entityManager.flush();

        Permission result = permissionRepository.findBySocialMediaAndHasAccess(socialMedia, hasAccess);

        assertThat(result.getSocialMedia()).isEqualTo(socialMedia);
        assertThat(result.isHasAccess()).isEqualTo(hasAccess);
    }
}
