package service;

import com.myblog.dto.comment.CommentDTO;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentDTO commentDTO;
    private Comment comment;
    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder().id(1L).title("Test post").build();
        comment = Comment.builder().id(1L).post(post).text("This is a comment").build();

        commentDTO = CommentDTO.builder()
                .id(1L)
                .postId(1L)
                .text("This is a new comment text")
                .build();
    }

    @Test
    void create_ShouldSaveComment() {
        when(postRepository.findById(commentDTO.getPostId())).thenReturn(post);

        commentService.create(commentDTO);

        verify(postRepository, times(1)).findById(commentDTO.getPostId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void update_ShouldModifyExistingComment() {
        when(commentRepository.findById(commentDTO.getId())).thenReturn(comment);

        commentService.update(commentDTO);

        assertEquals("This is a new comment text", comment.getText());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void delete_ShouldRemoveComment() {
        doNothing().when(commentRepository).deleteByIdAndPostId(commentDTO.getId(), post.getId());

        commentService.delete(commentDTO.getId(), post.getId());

        verify(commentRepository, times(1)).deleteByIdAndPostId(commentDTO.getId(), post.getId());
    }
}
