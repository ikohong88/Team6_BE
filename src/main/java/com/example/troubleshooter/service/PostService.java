package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.CommentResponseDto;
import com.example.troubleshooter.dto.PostMessageDto;
import com.example.troubleshooter.dto.PostRequestDto;
import com.example.troubleshooter.dto.PostResponseDto;
import com.example.troubleshooter.entity.Comment;
import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.entity.User;
import com.example.troubleshooter.repository.PostRepository;
import com.example.troubleshooter.repository.UserRepository;
import com.example.troubleshooter.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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
    public List<PostResponseDto> posts(){
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
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
    public PostMessageDto create(PostRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        boolean ok = true;
        String message = "생성 성공";
        requestDto.setSolved(false);
        Post post = new Post(requestDto,userDetails);
        if(EmptyValue(requestDto)){
            ok = false;
            message = "생성 실패";
            throw new IllegalArgumentException("글을 입력해주세요.");
        }
        postRepository.save(post);
        return new PostMessageDto(ok,message);
    }

    //수정
    @Transactional
    public PostMessageDto update(Long postId, PostRequestDto requestDto, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        boolean ok = true;
        String message = "수정 성공";
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Long id = userDetails.getUser().getId();
        Long userId = post.getUserId();
        if(NotUser(id, userId)){
            ok = false;
            message = "수정 실패";
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        if(EmptyValue(requestDto)){
            ok = false;
            message = "수정 실패";
            throw new IllegalArgumentException("글을 입력해주세요.");
        }
        post.update(requestDto);
        return new PostMessageDto(ok,message);
    }

    //삭제
    @Transactional
    public PostMessageDto delete(Long postId, UserDetailsImpl userDetails){
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        boolean ok = true;
        String message = "삭제 성공";
        Long id = userDetails.getUser().getId();
        Long userId = postRepository.findById(postId).get().getUserId();
        if(NotUser(id, userId)){
            ok = false; message = "삭제 실패";
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        postRepository.deleteById(postId);
        return new PostMessageDto(ok,message);
    }

    public boolean NotUser(Long id, Long userId){
        return !id.equals(userId);
    }
    public boolean EmptyValue(PostRequestDto requestDto){
        return requestDto.getTitle().isEmpty() || requestDto.getContent().isEmpty();
    }
}
