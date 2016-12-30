package app.controller;

import app.Application;
import app.model.Note;
import app.model.NoteDto;
import app.model.User;
import app.model.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by pyvov on 20.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MainControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "test_user";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private User user;

    private Long noteId;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.userRepository.deleteAll();

        User newUser = new User(userName, "password", "email@", new ArrayList<>());
        this.userRepository.save(newUser);
        this.user = userRepository.findByUsername(newUser.getUsername()).get();
        Note newNote1 = new Note("title1", "text1");
        Note newNote2 = new Note("title2", "text2");
        this.user.getNotes().add(newNote1);
        this.user.getNotes().add(newNote2);
        this.userRepository.save(user);
        this.noteId = 1L;
    }

    @Test
    public void createAlreadyExistingUser() throws Exception {
        mockMvc.perform(post("/api/users/")
                .content(this.json(new User(this.userName, "", "", new ArrayList<>())))
                .contentType(this.contentType))
                .andExpect(status().isConflict());
    }

    @Test
    public void createNoteUserNotFound() throws Exception {
        mockMvc.perform(post("/api/users/george")
                .content(this.json(new NoteDto("title1", "text1")))
                .contentType(this.contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getNotesUserNotFound() throws Exception {
        mockMvc.perform(get("/api/users/george"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getNotes() throws Exception {
        mockMvc.perform(get("/api/users/" + this.userName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(this.user.getNotes().get(0).getTitle())))
                .andExpect(jsonPath("$[0].text", is(this.user.getNotes().get(0).getText())))
                .andExpect(jsonPath("$[1].title", is(this.user.getNotes().get(1).getTitle())))
                .andExpect(jsonPath("$[1].text", is(this.user.getNotes().get(1).getText())));
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(this.contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(this.user.getUsername())))
                .andExpect(jsonPath("$[0].password", is(this.user.getPassword())))
                .andExpect(jsonPath("$[0].email", is(this.user.getEmail())));
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(post("/api/users/")
                .content(this.json(new User("user1", "password1", "email@1", new ArrayList<>())))
                .contentType(this.contentType))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserNullBody() throws Exception {
        mockMvc.perform(post("/api/users/")
                .content(this.json(new User()))
                .contentType(this.contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(put("/api/users/" + this.userName)
                .content(json(new User(this.userName, "password1", "email@1", new ArrayList<>())))
                .contentType(this.contentType))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + this.userName))
                .andExpect(status().isAccepted());
    }

    @Test
    public void createNote() throws Exception {
        mockMvc.perform(post("/api/users/" + this.userName)
                .content(this.json(new NoteDto("title3", "text3")))
                .contentType(this.contentType))
                .andExpect(status().isCreated());
    }

    @Test
    public void createNoteNullBody() throws Exception {
        mockMvc.perform(post("/api/users/" + this.userName)
                .content(this.json(new NoteDto()))
                .contentType(this.contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNote() throws Exception {
        mockMvc.perform(put("/api/users/" + this.userName + "/" + this.noteId)
                .content(this.json(new NoteDto("title11", "text11")))
                .contentType(this.contentType))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteNote() throws Exception {
        mockMvc.perform(delete("/api/users/" + this.userName + "/delete-note?notes=" + this.noteId))
                .andExpect(status().isAccepted());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
