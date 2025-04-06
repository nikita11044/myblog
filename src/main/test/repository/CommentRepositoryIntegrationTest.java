package repository;

import com.myblog.entity.Comment;
import com.myblog.jpa.CommentJpaRepository;
import com.myblog.jpa.PostJpaRepository;
import com.myblog.repository.CommentRepository;
import configuration.TestDatabaseHelper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import configuration.TestWebConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {TestWebConfiguration.class})
@WebAppConfiguration
public class CommentRepositoryIntegrationTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private TestDatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        databaseHelper.clearAndResetDatabase();
        databaseHelper.createMockPostPlain();
    }

    private Comment createComment(String text) {
        var post = postJpaRepository.findById(1L);

        return post.map(value -> Comment.builder()
                .post(value)
                .text(text)
                .build()).orElse(null);
    }

    @Test
    void testSave_shouldSaveComment() {
        Comment comment = createComment("This is a comment");

        commentRepository.save(comment);

        Optional<Comment> savedComment = commentJpaRepository.findById(comment.getId());
        assertTrue(savedComment.isPresent());
        assertEquals("This is a comment", savedComment.get().getText());
    }

    @Test
    void testFindById_shouldReturnComment() {
        Comment comment = createComment("This is a comment");
        commentRepository.save(comment);

        Comment foundComment = commentRepository.findById(comment.getId());

        assertNotNull(foundComment);
        assertEquals("This is a comment", foundComment.getText());
    }

    @Test
    void testFindById_shouldThrowExceptionIfNotFound() {
        assertThrows(EntityNotFoundException.class, () -> commentRepository.findById(999L));
    }

    @Test
    void testDeleteById_shouldDeleteComment() {
        Comment comment = createComment("This is a comment to be deleted");
        commentRepository.save(comment);

        commentRepository.deleteByIdAndPostId(comment.getId(), 1L);

        Optional<Comment> deletedComment = commentJpaRepository.findById(comment.getId());
        assertFalse(deletedComment.isPresent(), "Comment should be deleted from the database.");
    }
}
