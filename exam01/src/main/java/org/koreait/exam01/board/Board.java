package org.koreait.exam01.board;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Board {
    //1. 게시판엔 무엇이 들어갈지 명시한다.

    private Long id; //게시글 번호
    private String subject; //제목
    private String content; //내용
    private LocalDateTime regDt; //등록 일시
    private LocalDateTime modDt; //수정 일시

}
