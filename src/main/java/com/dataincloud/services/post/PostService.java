package com.dataincloud.services.post;

import com.dataincloud.core.post.IPostRepository;
import com.dataincloud.core.post.Post;
import com.dataincloud.services.post.dto.BasicPostDto;
import com.dataincloud.services.post.dto.PostCreateDto;
import com.dataincloud.services.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RequiredArgsConstructor
public class PostService {
    private final IPostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto create(PostCreateDto newPost) {
        return modelMapper.map(
                postRepository.create(modelMapper.map(newPost, Post.class)), PostDto.class
        );
    }

    public List<BasicPostDto> getAll() {
        return postRepository.readAll().stream()
                .map(post -> modelMapper.map(post, BasicPostDto.class))
                .toList();
    }

    public PostDto getById(Long id) {
        return modelMapper.map(
                postRepository.readById(id), PostDto.class
        );
    }

    public PostDto update(PostDto editedPost) {
        return modelMapper.map(
                postRepository.update(modelMapper.map(editedPost, Post.class)), PostDto.class
        );
    }

    public PostDto deleteById(Long id) {
        return modelMapper.map(
                postRepository.delete(id), PostDto.class
        );
    }
}