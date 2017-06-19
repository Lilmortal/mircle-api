package nz.co.mircle.user.dao;

import nz.co.mircle.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * User repository test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    private static final Long ID = Long.parseLong("1");

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    private static final String FIRST_NAME = "First_name";

    private static final String LAST_NAME = "last_name";

    private static final String GENDER = "M";

    private static final String EMAIL_ADDRESS = "test@test.com";

    private static final String PHONE_NUMBER = "12345";

    private static final LocalDate BIRTH_DATE = LocalDate.now();

    private static final LocalDateTime CREATED_ON = LocalDateTime.now();

    private static final LocalDateTime LAST_LOGGED_IN = LocalDateTime.now();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenIdReturnAUser() {
        User user = new User(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, GENDER, EMAIL_ADDRESS, PHONE_NUMBER, BIRTH_DATE, CREATED_ON, LAST_LOGGED_IN);
        entityManager.persist(user);
        entityManager.flush();

        User result = userRepository.findById(ID);
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
        assertThat(result.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(result.getLastName()).isEqualTo(LAST_NAME);
        assertThat(result.getGender()).isEqualTo(GENDER);
        assertThat(result.getEmailAddress()).isEqualTo(EMAIL_ADDRESS);
        assertThat(result.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(result.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(result.getCreatedOn()).isEqualTo(CREATED_ON);
        assertThat(result.getLastLoggedIn()).isEqualTo(LAST_LOGGED_IN);
    }
}
