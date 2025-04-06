package service;

import com.myblog.dto.post.PostDTO;
import com.myblog.dto.post.PostRequestDTO;
import com.myblog.entity.Post;
import com.myblog.mapper.PostMapper;
import com.myblog.repository.PostRepository;
import com.myblog.service.FileService;
import com.myblog.service.PostService;
import com.myblog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private FileService fileService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostRequestDTO postRequestDTO;
    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Content")
                .likesCount(0)
                .build();

        postRequestDTO = PostRequestDTO.builder()
                .id(1L)
                .title("Updated Title")
                .text("Updated Content")
                .build();

        postDTO = PostDTO.builder()
                .id(1L)
                .title("Test Title")
                .text("Test Content")
                .build();
    }

    @Test
    void create_ShouldSavePost() {
        when(postMapper.toEntity(any(PostRequestDTO.class))).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post.getId());

        Long postId = postService.create(postRequestDTO);

        assertNotNull(postId);
        assertEquals(1L, postId);
        verify(tagService, times(1)).createMultipleFromString(postRequestDTO.getTagsAsString());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getById_ShouldReturnPostDTO() {
        when(postRepository.findById(1L)).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO result = postService.getById(1L);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void getByTagName_ShouldReturnPagedPosts() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Post> postPage = new PageImpl<>(List.of(post), pageable, 1);

        when(postRepository.findAll(pageable)).thenReturn(postPage);
        when(postMapper.toDTO(any(Post.class))).thenReturn(postDTO);

        Page<PostDTO> result = postService.getByTagName("", 1, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(postRepository, times(1)).findAll(pageable);
    }

    @Test
    void update_ShouldModifyExistingPost() {
        when(postRepository.findById(1L)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post.getId());

        postService.update(postRequestDTO);

        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getText());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void updateLikes_ShouldIncreaseLikeCount() {
        when(postRepository.findById(1L)).thenReturn(post);
        post.setLikesCount(0);

        postService.updateLikes(1L, true);
        assertEquals(1, post.getLikesCount());

        postService.updateLikes(1L, false);
        assertEquals(0, post.getLikesCount());

        verify(postRepository, times(2)).save(post);
    }

    @Test
    void delete_ShouldRemovePost() {
        when(postRepository.findById(1L)).thenReturn(post);
        doNothing().when(fileService).deleteFile(post.getImagePath());
        doNothing().when(postRepository).deleteById(1L);

        postService.delete(1L);

        verify(fileService, times(1)).deleteFile(any());
        verify(postRepository, times(1)).deleteById(1L);
    }
}
