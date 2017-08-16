package nz.co.mircle.v1.api.profileImage.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tanj1 on 16/08/2017.
 */
@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private static final String AWS_ACCESS_KEY = System.getenv("AWS_ACCESS_KEY");

    private static final String AWS_SECRET_KEY = System.getenv("AWS_SECRET_KEY");

    private static final String AWS_BUCKET_NAME = "Mircle";

    @Override
    public String getDefaultImage() throws Exception {
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        String image;
        final AmazonS3 s3 = AmazonS3Client.builder().withRegion(Regions.AP_SOUTHEAST_2).withCredentials(credentialsProvider).build();
        try {
            S3Object o = s3.getObject(AWS_BUCKET_NAME, AWS_ACCESS_KEY);
            S3ObjectInputStream s3is = o.getObjectContent();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(s3is));
            while (true) {
                image = reader.readLine();
                if (image == null) break;
            }

            s3is.close();
        } catch (AmazonServiceException e) {
            throw new Exception(e.getErrorMessage());
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
        return image;
    }
}
