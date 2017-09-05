package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import java.io.IOException;
import java.net.URL;
import org.springframework.web.multipart.MultipartFile;

/** Created by tanj1 on 16/08/2017. */
public interface ProfileImageService {
  URL getDefaultImage() throws AmazonServiceException;

  URL getUserProfileImageUrl(String key);

  URL uploadProfileImageToS3(MultipartFile profileImage, String emailAddress)
      throws IOException, AmazonServiceException;

  void deleteProfileImage(URL key);
}
