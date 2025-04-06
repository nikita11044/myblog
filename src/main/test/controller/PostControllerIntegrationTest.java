package controller;

import configuration.TestDatabaseHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import configuration.TestWebConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitConfig(classes = {TestWebConfiguration.class})
@WebAppConfiguration
class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestDatabaseHelper databaseHelper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        databaseHelper.clearAndResetDatabase();
        databaseHelper.createMockPostWithComment();
    }

    private MockMultipartFile getMockFile() {
        return new MockMultipartFile("image", "test.jpg", "image/jpeg", "image content".getBytes());
    }

    @Test
    void getPosts_shouldReturnHtmlWithPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paging"));
    }

    @Test
    void getPost_shouldReturnPostDetails() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("title", "Test title"));
    }

    @Test
    void addPost_shouldCreatePostAndRedirect() throws Exception {
        mockMvc.perform(multipart("/posts")
                        .file(getMockFile())
                        .param("title", "New Post")
                        .param("text", "New post content")
                        .param("tagsAsString", "tag1,tag2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/2"));
    }

    @Test
    void editPost_shouldUpdatePostAndRedirect() throws Exception {
       mockMvc.perform(multipart("/posts/1")
                        .file(getMockFile())
                        .param("title", "Updated Title")
                        .param("text", "Updated content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }


    @Test
    void deletePost_shouldRemovePostAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void addComment_shouldCreateCommentAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/1/comments")
                        .param("text", "This is a comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void deleteComment_shouldRemoveCommentAndRedirect() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }
}
