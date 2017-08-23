package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.user.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by tanj1 on 16/08/2017.
 */
@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private final Logger LOG = LoggerFactory.getLogger(ProfileImageServiceImpl.class);

    private static final String AWS_BUCKET_NAME = "mircle";

    private static final String AWS_DEFAULT_PROFILE_IMAGE_KEY = "default/profile_image.png";

    @Value("${AWS_ACCESS_KEY}")
    String AWS_ACCESS_KEY;

    @Value("${AWS_SECRET_KEY}")
    String AWS_SECRET_KEY;

    @Override
    public URL getDefaultImage() throws AmazonServiceException {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider =
                new AWSStaticCredentialsProvider(credentials);

        final AmazonS3 s3 =
                AmazonS3Client.builder()
                        .withRegion(Regions.AP_SOUTHEAST_2)
                        .withCredentials(credentialsProvider)
                        .build();
        URL imageUrl = s3.getUrl(AWS_BUCKET_NAME, AWS_DEFAULT_PROFILE_IMAGE_KEY);
        return imageUrl;
    }

    @Override
    public URL uploadProfileImage(MultipartFile uploadedFile)
            throws IOException, AmazonServiceException {
        //LOG.info(uploadedFile.getOriginalFilename() + " " + uploadedFile.getName() + " " + uploadedFile.getContentType() + " " + uploadedFile.getInputStream());
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider =
                new AWSStaticCredentialsProvider(credentials);

        InputStream stream = uploadedFile.getInputStream();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadedFile.getBytes().length);

        String key = System.currentTimeMillis() + "/" + uploadedFile.getOriginalFilename();
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(AWS_BUCKET_NAME, key, stream, objectMetadata);

        final AmazonS3 s3 =
                AmazonS3Client.builder()
                        .withRegion(Regions.AP_SOUTHEAST_2)
                        .withCredentials(credentialsProvider)
                        .build();
        s3.putObject(putObjectRequest);

        URL imageUrl = s3.getUrl(AWS_BUCKET_NAME, key);
        return imageUrl;
    }
}
