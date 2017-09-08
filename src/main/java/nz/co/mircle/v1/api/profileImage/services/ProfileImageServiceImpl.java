package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tanj1 on 16/08/2017.
 */
@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final String AWS_BUCKET_NAME = "mircle";

    private static final String AWS_DEFAULT_PROFILE_IMAGE_KEY = "default/profile_image.png";

    private AmazonS3 s3;

    @Autowired
    public ProfileImageServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public URL getDefaultImage() throws AmazonServiceException {
        URL imageUrl = s3.getUrl(AWS_BUCKET_NAME, AWS_DEFAULT_PROFILE_IMAGE_KEY);
        return imageUrl;
    }

    @Override
    public URL uploadProfileImageToS3(MultipartFile profileImage, String emailAddress)
            throws IOException, AmazonServiceException {
        InputStream stream = profileImage.getInputStream();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(profileImage.getBytes().length);

        String key = emailAddress + "/" + profileImage.getOriginalFilename();
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(AWS_BUCKET_NAME, key, stream, objectMetadata);

        s3.putObject(putObjectRequest);
        s3.setObjectAcl(AWS_BUCKET_NAME, key, CannedAccessControlList.PublicRead);

        URL imageUrl = getUserProfileImageUrl(key);
        return imageUrl;
    }

    @Override
    public URL getUserProfileImageUrl(String key) {
        URL imageUrl = s3.getUrl(AWS_BUCKET_NAME, key);
        return imageUrl;
    }

    @Override
    public void deleteProfileImage(URL key) throws UnsupportedEncodingException {
        String decodedKey = URLDecoder.decode(key.getPath(), "UTF-8");
        // The substring is needed to remove the "/" at the start of the key
        s3.deleteObject(AWS_BUCKET_NAME, decodedKey.substring(1));
    }
}
