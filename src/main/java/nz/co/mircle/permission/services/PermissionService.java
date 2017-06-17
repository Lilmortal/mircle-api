package nz.co.mircle.permission.services;

import nz.co.mircle.permission.model.Permission;
import org.springframework.stereotype.Service;

/**
 * Created by jacktan on 17/06/17.
 */
public interface PermissionService {
    void createPermission(Permission permission);
}
