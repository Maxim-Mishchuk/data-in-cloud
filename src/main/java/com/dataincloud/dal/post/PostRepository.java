package com.dataincloud.dal.post;

import com.dataincloud.core.exceptions.ResourceNotFoundException;
import com.dataincloud.core.post.IPostRepository;
import com.dataincloud.core.post.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostRepository implements IPostRepository {
    private final PostJpaRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Post create(Post post) {
        return modelMapper.map(
            postRepository.save(modelMapper.map(post, PostJpa.class)), Post.class
        );
    }

    @Override
    public List<Post> readAll() {
        return postRepository.findAllFetch().stream()
                .peek(postJpa -> postJpa.getUser().setPosts(null))
                .map(postJpa -> modelMapper.map(postJpa, Post.class))
                .toList();
    }

    @Override
    public Post readById(Long id) {
        PostJpa foundPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        foundPost.getUser().setPosts(null);

        return modelMapper.map(
                foundPost, Post.class
        );
    }

    @Override
    public Post update(Post post) {
        return modelMapper.map(
                postRepository.save(modelMapper.map(post, PostJpa.class)), Post.class
        );
    }

    @Override
    public Post delete(Long id) {
        Post deletedPost = modelMapper.map(
                postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found")), Post.class
        );
        postRepository.deleteById(id);
        return deletedPost;
    }
}
