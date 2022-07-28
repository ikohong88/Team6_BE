package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.*;
import com.example.troubleshooter.entity.Comment;
import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.entity.User;
import com.example.troubleshooter.repository.PostRepository;
import com.example.troubleshooter.repository.UserRepository;
import com.example.troubleshooter.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //조회
    @Transactional
    public List<PostResponseDto> posts(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Post> posts = postRepository.findAllByOrderByIdDesc(pageable);
        List<PostResponseDto> result = new ArrayList<>();
        for(Post post : posts){
            User user = userRepository.findNicknameById(post.getUserId());
            PostResponseDto responseDto = new PostResponseDto(post,user);
            result.add(responseDto);
        }
        return result;
    }

    //읽기
    @Transactional
    public PostResponseDto post(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        User user = userRepository.findNicknameById(post.getUserId());
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        if(comments.size() > 0){
            for(Comment comment : comments){
                User commentUser = userRepository.findNicknameById(comment.getUserId());
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment,commentUser);
                commentResponseDtos.add(commentResponseDto);
            }
        }
        return new PostResponseDto(post,user,commentResponseDtos);
    }

    //작성
    @Transactional
    public RestApi create(PostRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        Post post = new Post(requestDto,userDetails);
        if(EmptyValue(requestDto)){
            throw new IllegalArgumentException("글을 입력해주세요.");
        }
        postRepository.save(post);
        String Message = "작성 성공";
        HttpStatus httpStatus = HttpStatus.OK;
        return new RestApi(Message, httpStatus);
    }

    //pickedComment patch
    @Transactional
    public RestApi pickedComment(Long postId, PickedCommentDto pickedCommentDto, UserDetailsImpl userDetails){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Long id = userDetails.getUser().getId();
        Long userId = post.getUserId();
        if(NotUser(id, userId)) throw new IllegalArgumentException("작성자가 아닙니다.");
        post.patch(pickedCommentDto);
        String Message = "답변이 선택되었습니다.";
        HttpStatus httpStatus = HttpStatus.OK;
        return new RestApi(Message, httpStatus);
    }

    //수정
    @Transactional
    public RestApi update(Long postId, PostRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Long id = userDetails.getUser().getId();
        Long userId = post.getUserId();
        if(NotUser(id, userId)) throw new IllegalArgumentException("작성자가 아닙니다.");
        if(EmptyValue(requestDto)) throw new IllegalArgumentException("글을 입력해주세요.");
        post.update(requestDto);
        String Message = "수정 성공";
        HttpStatus httpStatus = HttpStatus.OK;
        return new RestApi(Message, httpStatus);
    }

    //삭제
    @Transactional
    public RestApi delete(Long postId, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        Long id = userDetails.getUser().getId();
        Long userId = postRepository.findById(postId).get().getUserId();
        if(NotUser(id, userId)) throw new IllegalArgumentException("작성자가 아닙니다.");
        postRepository.deleteById(postId);
        String Message = "삭제 성공";
        HttpStatus httpStatus = HttpStatus.OK;
        return new RestApi(Message, httpStatus);
    }

    public boolean NotUser(Long id, Long userId){
        return !id.equals(userId);
    }
    public boolean EmptyValue(PostRequestDto requestDto){
        return requestDto.getTitle().isEmpty() || requestDto.getContent().isEmpty();
    }
}
