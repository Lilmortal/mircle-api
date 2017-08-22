package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by tanj1 on 16/08/2017.
 */
public interface ProfileImageService {
    URL getDefaultImage() throws AmazonServiceException;

    String uploadProfileImage(String image, String id) throws FileNotFoundException, AmazonServiceException;
}
