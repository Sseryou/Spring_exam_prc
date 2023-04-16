package org.koreait.exam01.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardForm {
    //제목과 내용을 저장하고, html에서 사용하기 위한 클래스

    @NotBlank(message = "제목을 입력하세요.")
    private String subject;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

}
