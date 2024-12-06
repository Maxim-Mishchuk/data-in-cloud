package com.dataincloud.services.post;

import com.dataincloud.core.post.Post;
import com.dataincloud.api.configuration.BasicConfiguration;
import com.dataincloud.dal.post.PostRepository;
import com.dataincloud.services.post.dto.PostCreateDto;
import com.dataincloud.services.post.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = BasicConfiguration.class)
class PostServiceTest {

    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, modelMapper);
    }

    @Test
    void canCreatePost() {
        PostCreateDto newPostDto = new PostCreateDto();
        newPostDto.setHeader("Header");
        newPostDto.setDescription("Description");

        Post newPost = modelMapper.map(newPostDto, Post.class);

        when(postRepository.create(newPost)).thenAnswer(invocationOnMock -> {
            Post createdPost = invocationOnMock.getArgument(0);
            createdPost.setId(1L);
            return createdPost;
        });

        PostDto createdPost = postService.create(newPostDto);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository)
                .create(postCaptor.capture());

        assertThat(createdPost)
                .returns(newPostDto.getHeader(), from(PostDto::getHeader))
                .returns(newPostDto.getDescription(), from(PostDto::getDescription));
    }

    @Test
    void canGetAllPosts() {
        postService.getAll();

        verify(postRepository).readAll();
    }

    @Test
    void canGetPostById() {
        Long id = 1L;

        when(postRepository.readById(id)).thenAnswer(invocationOnMock -> {
            Post returnedPost = new Post();
            returnedPost.setId(id);
            return returnedPost;
        });

        PostDto returnedPost = postService.getById(id);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(postRepository).readById(idCaptor.capture());

        assertThat(returnedPost)
                .returns(idCaptor.getValue(), from(PostDto::getId));
    }

    @Test
    void canUpdatePost() {
        PostDto editedPostDto = new PostDto();
        editedPostDto.setId(1L);
        editedPostDto.setHeader("Header");
        editedPostDto.setDescription("Description");

        Post editedPost = modelMapper.map(editedPostDto, Post.class);

        when(postRepository.update(editedPost)).thenReturn(editedPost);

        postService.update(editedPostDto);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).update(postCaptor.capture());
    }

    @Test
    void canDeletePostById() {
        Long id = 1L;

        when(postRepository.delete(id)).thenReturn(new Post());

        postService.deleteById(id);

        verify(postRepository).delete(id);
    }
}