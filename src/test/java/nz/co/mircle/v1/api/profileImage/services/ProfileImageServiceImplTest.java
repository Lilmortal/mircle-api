package nz.co.mircle.v1.api.profileImage.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ProfileImageServiceImplTest {
  private static final String AWS_BUCKET_NAME = "mircle";

  private static final String AWS_DEFAULT_PROFILE_IMAGE_KEY = "default/profile_image.png";

  private static final String FILE_NAME = "test.png";

  private static final String EMAIL_ADDRESS = "test@test.com";

  private static final String KEY = EMAIL_ADDRESS + "/" + FILE_NAME;

  @Mock private AmazonS3 s3;

  @Mock private MultipartFile profileImage;

  @Mock private InputStream inputStream;

  @Mock private PutObjectResult putObjectResult;

  @InjectMocks private ProfileImageService service = new ProfileImageServiceImpl(s3);

  @Rule public MockitoRule rule = MockitoJUnit.rule();

  private URL url;

  private byte[] bytes;

  @Before
  public void setup() throws IOException {
    url = new URL("http://test.oom");
    bytes = new byte[100];

    when(profileImage.getInputStream()).thenReturn(inputStream);
    when(profileImage.getBytes()).thenReturn(bytes);
    when(profileImage.getOriginalFilename()).thenReturn(FILE_NAME);
  }

  @Test
  public void getDefaultImage() {
    when(s3.getUrl(AWS_BUCKET_NAME, AWS_DEFAULT_PROFILE_IMAGE_KEY)).thenReturn(url);

    URL result = service.getDefaultImage();
    assertThat(result).isEqualTo(url);
  }

  @Test
  public void uploadProfileImageToS3() throws IOException {
    when(s3.putObject(any(PutObjectRequest.class))).thenReturn(putObjectResult);
    doNothing().when(s3).setObjectAcl(AWS_BUCKET_NAME, KEY, CannedAccessControlList.PublicRead);
    when(s3.getUrl(AWS_BUCKET_NAME, KEY)).thenReturn(url);

    URL result = service.uploadProfileImageToS3(profileImage, EMAIL_ADDRESS);
    assertThat(result).isEqualTo(url);
  }
}
