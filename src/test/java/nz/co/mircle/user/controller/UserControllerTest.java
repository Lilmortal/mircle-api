package nz.co.mircle.user.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nz.co.mircle.user.model.User;
import nz.co.mircle.user.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/** User controller test */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
  private static final String FIRST_NAME = "first_name";

  @Autowired private MockMvc mvc;

  @MockBean private UserService userService;

  @Test
  public void createAUser() throws Exception {
    User user = new User();
    user.setFirstName(FIRST_NAME);

    doNothing().when(userService).createUser(user);

    Gson gson = new Gson();
    String json = gson.toJson(user);
    mvc.perform(post("/user/create").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }
}
