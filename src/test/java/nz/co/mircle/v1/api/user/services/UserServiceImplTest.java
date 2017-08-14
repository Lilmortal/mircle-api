package nz.co.mircle.v1.api.user.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import nz.co.mircle.v1.api.user.dao.UserRepository;
import nz.co.mircle.v1.api.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/** User service layer test */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class UserServiceImplTest {
  private static final Long ID = Long.parseLong("1");

  private static final String FIRST_NAME = "first_name";

  @TestConfiguration
  static class UserServiceImplTestContextConfiguration {
    @Bean
    public UserService userService() {
      return new UserServiceImpl();
    }
  }

  @Autowired private UserService userService;

  @MockBean private User user;

  @MockBean private UserRepository userRepository;

  @Before
  public void setup() {
    when(user.getFirstName()).thenReturn(FIRST_NAME);
    when(userRepository.findById(ID)).thenReturn(user);
  }

  @Test
  public void givenIdReturnUser() {
    User result = userService.findUser(ID);
    assertThat(result.getFirstName()).isEqualTo(FIRST_NAME);
  }
}
