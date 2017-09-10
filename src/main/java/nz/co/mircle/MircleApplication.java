package nz.co.mircle;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** This application is used to create the Mircle CRUD API endpoints. */
@SpringBootApplication
public class MircleApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(MircleApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public EnvironmentVariablesConfig environmentVariablesConfig() {
    return new EnvironmentVariablesConfig();
  }

  @Bean
  public AmazonS3 s3() {
    BasicAWSCredentials credentials =
        new BasicAWSCredentials(
            environmentVariablesConfig().getAwsAccessKey(),
            environmentVariablesConfig().getAwsSecretKey());
    AWSStaticCredentialsProvider credentialsProvider =
        new AWSStaticCredentialsProvider(credentials);
    final AmazonS3 s3 =
        AmazonS3Client.builder()
            .withRegion(Regions.AP_SOUTHEAST_2)
            .withCredentials(credentialsProvider)
            .build();
    return s3;
  }
}
