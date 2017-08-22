package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

/**
 * Created by tanj1 on 16/08/2017.
 */
@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private static final String AWS_ACCESS_KEY = System.getenv("AWS_ACCESS_KEY");

    private static final String AWS_SECRET_KEY = System.getenv("AWS_SECRET_KEY");

    private static final String AWS_BUCKET_NAME = "mircle";

    private static final String AWS_DEFAULT_PROFILE_IMAGE_KEY = "default/profile_image.png";

    @Override
    public URL getDefaultImage() throws AmazonServiceException {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        final AmazonS3 s3 = AmazonS3Client.builder().withRegion(Regions.AP_SOUTHEAST_2).withCredentials(credentialsProvider).build();
        URL imageUrl = s3.getUrl(AWS_BUCKET_NAME, AWS_DEFAULT_PROFILE_IMAGE_KEY);
        return imageUrl;
    }

    @Override
    public String uploadProfileImage(String image, String id) throws FileNotFoundException, AmazonServiceException {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        FileInputStream stream = new FileInputStream(image);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_BUCKET_NAME, id, stream, objectMetadata);

        final AmazonS3 s3 = AmazonS3Client.builder().withRegion(Regions.AP_SOUTHEAST_2).withCredentials(credentialsProvider).build();
        PutObjectResult imageUrl = s3.putObject(putObjectRequest);
        return imageUrl.getETag();
    }
}
