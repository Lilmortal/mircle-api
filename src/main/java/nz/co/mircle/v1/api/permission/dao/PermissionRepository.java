package nz.co.mircle.v1.api.permission.dao;

import nz.co.mircle.v1.api.permission.model.Permission;
import nz.co.mircle.v1.api.socialMedia.model.SocialMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** CRUD operations on the permission table. */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
  Permission findById(Long id);

  Permission findBySocialMediaAndHasAccess(SocialMedia socialMedia, boolean hasAccess);
}
