package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/** Created by tanj1 on 16/08/2017. */
public interface ProfileImageService {
  URL getDefaultImage() throws AmazonServiceException;

  URL uploadProfileImageToS3(MultipartFile profileImage, String emailAddress)
          throws IOException, AmazonServiceException;

  URL getUserProfileImageLink(String key);
}
