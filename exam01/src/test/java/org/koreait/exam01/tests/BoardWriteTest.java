package org.koreait.exam01.tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardWriteTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("제목, 내용 필수체크")
    public void validationTest1() throws Exception{
        //mockMvc.perform(post("/board/write"))
                //.andDo(print())
                //.andExpect(status().isOk());
        //perform은 요청을 전송하는 역할을 한다.
        //andDo(print())로 요청/응답 전체 메세지를 확인하고
        //andExpect(status().isOk());를 이용해서 응답하는가(isOk) 확인
        //status는 상태 코드를 반환하고, isOk는 200번대 코드를 반환하는가를 확인한다.

        String body = mockMvc.perform(post("/board/write"))
                .andReturn().getResponse().getContentAsString();

        //body에는, 요청을 post 방식으로 전송하고(post),
        //페이지 내용이 들어가 있는 응답 형식의 반환을 받는 내용이 들어가 있다.

        //제목 유효성 검사 체크
        assertTrue(body.contains("제목을 입력하세요."));

        //내용 유효성 검사 체크
        assertTrue(body.contains("내용을 입력하세요."));

    }

    @Test
    @DisplayName("게시글 작성 성공시 /board/list로 이동")
    public void writeSuccessTest() throws Exception{
        mockMvc.perform(post("/board/write")
                .param("subject", "제목3")
                .param("content", "내용3"))
                .andDo(print())
                .andExpect(redirectedUrl("/board/list"));
        //.param : 설정한 name을 확인하고, 그 값으로 직접 작성한  values를 넣는다.
        //redirectedUrl : 지정한 경로로 이동하는지 확인한다.

    }




}
