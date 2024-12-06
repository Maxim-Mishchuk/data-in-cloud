package com.dataincloud.api.controllers.post;

import com.dataincloud.services.post.PostService;
import com.dataincloud.services.post.dto.BasicPostDto;
import com.dataincloud.services.post.dto.PostCreateDto;
import com.dataincloud.services.post.dto.PostDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostDto create(@Valid @RequestBody PostCreateDto newPost) {
        return postService.create(newPost);
    }

    @GetMapping
    public List<BasicPostDto> getAll() {
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PutMapping
    public PostDto update(@Valid @RequestBody PostDto editedPost) {
        return postService.update(editedPost);
    }

    @DeleteMapping("/{id}")
    public PostDto delete(@PathVariable Long id) {
        return postService.deleteById(id);
    }
}
