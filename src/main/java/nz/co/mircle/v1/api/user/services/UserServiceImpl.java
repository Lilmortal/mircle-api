package nz.co.mircle.v1.api.user.services;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageService;
import nz.co.mircle.v1.api.profileImage.services.ProfileImageServiceImpl;
import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** List of user services implementation that are used to call the repository. */
@Service
public class UserServiceImpl implements UserService {
  private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired private ProfileImageService profileImageService;

  @Autowired private UserRepository userRepository;

  @Override
  public void createUser(User user) {
    userRepository.save(user);
  }

  @Override
  public User findUser(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public User findUser(String emailAddress) {
    return userRepository.findByEmailAddress(emailAddress);
  }

  @Override
  public User setUserProfileImage(User user, URL profileImage) throws AmazonServiceException {
    user.getProfileImage().setUri(profileImage);
    userRepository.save(user);
    return user;
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
