package nz.co.mircle.permission.controller;

import nz.co.mircle.AbstractController;
import nz.co.mircle.permission.model.Permission;
import nz.co.mircle.permission.services.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jacktan on 17/06/17.
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends AbstractController {
    private final Logger LOG = LoggerFactory.getLogger(PermissionController.class);

    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(name = "/create", method = RequestMethod.POST)
    public ResponseEntity createPermission(@RequestBody Permission permission) {
        LOG.info("Creating a permission...");

        try {
            permissionService.createPermission(permission);
            LOG.info("Permission created.");
        } catch (Exception e) {
            LOG.error("Permission failed");
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
