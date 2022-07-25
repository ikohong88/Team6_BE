package com.example.troubleshooter.Integration;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    /**
     * <p>
     * 테스트용 로컬 DB에 유저 1, 유저 2, 유저 2이 쓴 게시글 데이터를 넣어놨습니다.<br>
     * 댓글 통합 테스트에서는 유저와 게시글 데이터는 건들 일이 없기 때문에 DB에 넣어놓고 사용했습니다.
     * </p>
     * <p>
     * 유저 1(id="1", nickname="nickname", password="password", user_id="user")<br>
     * 유저 2(id="2", nickname="other", password="other", user_id="other")<br>
     * 유저 2가 쓴 게시글(id="1", category="spring", title="게시글 제목입니다.", content="게시글 내용입니다.", solved="false", user_id="2")
     * </p>
     */

    @Nested
    @DisplayName("댓글 작성")
    class WriteComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Nested
            @DisplayName("유효성 검사")
            class Validation {

                @Test
                @DisplayName("댓글 내용 Null")
                void fail_validation_null() {
                    // given
                    long postId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content(null)
                            .build();

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .postForEntity(
                                    "/api/posts/" + postId + "/comments",
                                    commentRequest,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

                @Test
                @DisplayName("댓글 내용 Empty")
                void fail_validation_empty() {
                    // given
                    long postId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content("")
                            .build();

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .postForEntity(
                                    "/api/posts/" + postId + "/comments",
                                    commentRequest,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

                @Test
                @DisplayName("댓글 내용 Blank")
                void fail_validation_blank() {
                    // given
                    long postId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content("          ")
                            .build();

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .postForEntity(
                                    "/api/posts/" + postId + "/comments",
                                    commentRequest,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

            }

        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("댓글 내용 정상")
            void success() {
                // given
                long postId = 1;

                CommentRequest commentRequest = CommentRequest.builder()
                        .content("테스트용 댓글입니다.")
                        .build();

                // when
                ResponseEntity<SuccessResponse> response = testRestTemplate
                        .withBasicAuth("user", "password")
                        .postForEntity(
                                "/api/posts/" + postId + "/comments",
                                commentRequest,
                                SuccessResponse.class
                        );

                // then
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
            }

        }

    }

    @Nested
    @DisplayName("댓글 수정")
    class EditComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Nested
            @DisplayName("유효성 검사")
            class Validation {

                @Test
                @DisplayName("댓글 내용 Null")
                void fail_validation_null() {
                    // given
                    long commentId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content(null)
                            .build();

                    HttpEntity<CommentRequest> request = new HttpEntity<>(commentRequest);

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .exchange(
                                    "/api/comments/" + commentId,
                                    HttpMethod.PUT,
                                    request,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

                @Test
                @DisplayName("댓글 내용 Empty")
                void fail_validation_empty() {
                    // given
                    long commentId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content("")
                            .build();

                    HttpEntity<CommentRequest> request = new HttpEntity<>(commentRequest);

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .exchange(
                                    "/api/comments/" + commentId,
                                    HttpMethod.PUT,
                                    request,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

                @Test
                @DisplayName("댓글 내용 Blank")
                void fail_validation_blank() {
                    // given
                    long commentId = 1;

                    CommentRequest commentRequest = CommentRequest.builder()
                            .content("          ")
                            .build();

                    HttpEntity<CommentRequest> request = new HttpEntity<>(commentRequest);

                    // when
                    ResponseEntity<SuccessResponse> response = testRestTemplate
                            .withBasicAuth("user", "password")
                            .exchange(
                                    "/api/comments/" + commentId,
                                    HttpMethod.PUT,
                                    request,
                                    SuccessResponse.class
                            );

                    // then
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                }

            }

            @Test
            @DisplayName("댓글 작성자가 아닌 사람이 수정 시도")
            void fail_is_not_commenter() {
                // given
                long commentId = 1;

                CommentRequest commentRequest = CommentRequest.builder()
                        .content("수정된 댓글입니다.")
                        .build();

                HttpEntity<CommentRequest> request = new HttpEntity<>(commentRequest);

                // when
                ResponseEntity<SuccessResponse> response = testRestTemplate
                        .withBasicAuth("other", "other")
                        .exchange(
                                "/api/comments/" + commentId,
                                HttpMethod.PUT,
                                request,
                                SuccessResponse.class
                        );

                // then
                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            }

        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("댓글 작성자가 수정")
            void success() {
                // given
                long commentId = 1;

                CommentRequest commentRequest = CommentRequest.builder()
                        .content("수정된 댓글입니다.")
                        .build();

                HttpEntity<CommentRequest> request = new HttpEntity<>(commentRequest);

                // when
                ResponseEntity<SuccessResponse> response = testRestTemplate
                        .withBasicAuth("user", "password")
                        .exchange(
                                "/api/comments/" + commentId,
                                HttpMethod.PUT,
                                request,
                                SuccessResponse.class
                        );

                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
            }

        }

    }

    @Nested
    @DisplayName("댓글 삭제")
    class DeleteComment {

        @Nested
        @DisplayName("실패")
        class Fail {

            @Test
            @DisplayName("댓글 작성자가 아닌 사람이 삭제 시도")
            void fail_is_not_commenter() {
                // given
                long commentId = 1;

                // when
                ResponseEntity<SuccessResponse> response = testRestTemplate
                        .withBasicAuth("other", "other")
                        .exchange(
                                "/api/comments/" + commentId,
                                HttpMethod.DELETE,
                                null,
                                SuccessResponse.class
                        );

                // then
                assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            }

        }

        @Nested
        @DisplayName("성공")
        class Success {

            @Test
            @DisplayName("댓글 작성자가 삭제")
            void success() {
                // given
                long commentId = 2;

                // when
                ResponseEntity<SuccessResponse> response = testRestTemplate
                        .withBasicAuth("user", "password")
                        .exchange(
                                "/api/comments/" + commentId,
                                HttpMethod.DELETE,
                                null,
                                SuccessResponse.class
                        );

                // then
                assertEquals(HttpStatus.OK, response.getStatusCode());
            }

        }

    }

    @Getter
    @Builder
    static class CommentRequest {

        String content;

    }

    @NoArgsConstructor
    static class SuccessResponse {

        boolean ok;

        String result;

        SuccessResponse(String result) {
            ok = true;
            this.result = result;
        }

    }

}
