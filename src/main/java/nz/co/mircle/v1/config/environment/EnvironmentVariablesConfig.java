package nz.co.mircle.v1.config.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentVariablesConfig {
  @Value("${AWS_ACCESS_KEY}")
  private String awsAccessKey;

  @Value("${AWS_SECRET_KEY}")
  private String awsSecretKey;

  @Value("${JWT_SECRET_KEY}")
  private String jwtSecretKey;

  public String getAwsAccessKey() {
    return awsAccessKey;
  }

  public void setAwsAccessKey(String awsAccessKey) {
    this.awsAccessKey = awsAccessKey;
  }

  public String getAwsSecretKey() {
    return awsSecretKey;
  }

  public void setAwsSecretKey(String awsSecretKey) {
    this.awsSecretKey = awsSecretKey;
  }

  public String getJwtSecretKey() {
    return jwtSecretKey;
  }

  public void setJwtSecretKey(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }
}
