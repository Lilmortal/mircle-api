package nz.co.mircle.v1.api.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import nz.co.mircle.v1.api.profileImage.model.ProfileImage;
import nz.co.mircle.v1.api.user.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * User repository test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    private static final Long ID = Long.parseLong("1");

    private static final String EMAIL_ADDRESS = "test@test.com";

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    private static final String FIRST_NAME = "First_name";

    private static final String SURNAME = "surname";

    private static final String GENDER = "M";

    private static final String PHONE_NUMBER = "12345";

    private static final LocalDateTime BIRTH_DATE = LocalDateTime.now();

    private static final String OCCUPATION = "Engineer";

    private static final LocalDateTime CREATED_ON = LocalDateTime.now();

    private static final LocalDateTime LAST_LOGGED_IN = LocalDateTime.now();

    private static final boolean LOGGED_IN = false;

    private ProfileImage profileImage;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() throws MalformedURLException {
        URL url = new URL("http://test.com");
        profileImage = new ProfileImage(url, "", "", true);
    }

    @After
    public void cleanUp() {
        entityManager.flush();
    }

    @Test
    public void givenIdReturnAUser() {
        User user = populateUser();
        entityManager.persist(user);

        User result = userRepository.findById(ID);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenUsernameReturnAUser() {
        User user = populateUser();
        entityManager.persist(user);

        User result = userRepository.findByUsername(USERNAME);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenEmailAddressReturnAUser() {
        User user = populateUser();
        entityManager.persist(user);

        User result = userRepository.findByEmailAddress(EMAIL_ADDRESS);
        assertThat(result).isEqualTo(user);
    }

    private User populateUser() {
        User user = new User(
                        EMAIL_ADDRESS,
                        PASSWORD,
                        FIRST_NAME,
                        SURNAME,
                        GENDER,
                        PHONE_NUMBER,
                        BIRTH_DATE,
                        OCCUPATION,
                        CREATED_ON,
                        LAST_LOGGED_IN,
                        LOGGED_IN,
                        profileImage);
        user.setUsername(USERNAME);
        return user;
    }
}
