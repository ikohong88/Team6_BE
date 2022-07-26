package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.CommentResponseDto;
import com.example.troubleshooter.dto.PostRequestDto;
import com.example.troubleshooter.dto.PostResponseDto;
import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.entity.User;
import com.example.troubleshooter.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 게시글 목록 조회
    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 게시글 상세정보 조회
    public PostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        List<CommentResponseDto> comments = post.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        return new PostResponseDto(post, comments);
    }

    // 게시글 작성
    public void writePost(PostRequestDto postRequestDto, User user) {
        postRepository.save(new Post(postRequestDto, user));
    }

    // 게시글 수정
    public void editPost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        if (post.getUser() == user) {
            post.edit(postRequestDto);
            postRepository.save(post);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 수정할 수 있습니다.");
        }
    }

    // 게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        if (post.getUser() == user) {
            postRepository.delete(post);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 삭제할 수 있습니다.");
        }
    }

}
