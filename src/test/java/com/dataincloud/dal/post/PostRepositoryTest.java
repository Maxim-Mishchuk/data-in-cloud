package com.dataincloud.dal.post;

import com.dataincloud.api.configuration.BasicConfiguration;
import com.dataincloud.api.configuration.RepositoryJpaConfiguration;
import com.dataincloud.core.exceptions.ResourceNotFoundException;
import com.dataincloud.core.post.Post;
import com.dataincloud.core.user.User;
import com.dataincloud.dal.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {BasicConfiguration.class, RepositoryJpaConfiguration.class})
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private Post testingPost;

    @BeforeEach
    void setUp() {
        User testingUser = new User();
        testingUser.setUsername("name");
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(2003, Calendar.NOVEMBER, 27);
        testingUser.setBirthDate(birthDate);

        testingPost = new Post();
        testingPost.setHeader("Header");
        testingPost.setDescription("Description");
        Calendar createdDate = Calendar.getInstance();
        createdDate.set(2021, Calendar.MAY, 9);
        testingPost.setCreatedDate(createdDate);

        User createdUser = userRepository.create(testingUser);
        testingPost.setUser(createdUser);
    }

    @Test
    void createPostWithExistingUser() {
        Post createdPost = postRepository.create(testingPost);

        assertThat(createdPost)
                .doesNotReturn(null, from(Post::getId))
                .returns(testingPost.getHeader(), from(Post::getHeader))
                .returns(testingPost.getDescription(), from(Post::getDescription))
                .returns(testingPost.getCreatedDate(), from(Post::getCreatedDate));
    }


    @Test
    void readAllPosts() {
        Post createdPost = postRepository.create(testingPost);

        List<Post> posts = postRepository.readAll();

        assertThat(posts)
                .isNotEmpty()
                .contains(createdPost);
    }

    @Test
    void readExistingPostById() {
        Post createdPost = postRepository.create(testingPost);

        Post readPost = postRepository.readById(createdPost.getId());

        assertThat(readPost)
                .isEqualTo(createdPost);
    }

    @Test
    void readNonExistentPostById() {
        Long nonExistentId = 0L;

        assertThatThrownBy(() -> postRepository.readById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateExistingPost() {
        Post createdPost = postRepository.create(testingPost);
        createdPost.setHeader("New header");

        Post updatedPost = postRepository.update(createdPost);

        assertThat(updatedPost)
                .isEqualTo(createdPost);
    }

    @Test
    void deleteExistingPost() {
        Post createdPost = postRepository.create(testingPost);

        postRepository.delete(createdPost.getId());

        assertThatThrownBy(() -> userRepository.readById(createdPost.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteNonExistentPost() {
        Long nonExistentId = 0L;

        assertThatThrownBy(() -> userRepository.delete(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}