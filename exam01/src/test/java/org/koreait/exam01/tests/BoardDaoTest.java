package org.koreait.exam01.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.exam01.board.Board;
import org.koreait.exam01.board.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


//5. 게시글을 추가할수 있는 기능과 게시판을 조회할 수 있는 기능을 구현 했으니 테스트를 실시한다.
@SpringBootTest //스프링부트에서 지원하는 테스트를 사용한다.
 //이 애노테이션이 붙은 테스트는 실행 결과가 일시적이 된다.
//즉, 객체든 뭐든 생성할 때만 임시로 생성되었다가 테스트가 끝이 나면 소멸하게 된다.
@Transactional
public class BoardDaoTest {

    //BoardDAO를 받아와야 한다.
    //기존 객체를 활용하기 위해 @AutoWired 사용, 의존성 주입
    //본래는, @Bean에서 객체 유형을 찾아서 인식하는 애노테이션이지만,
    //스프링부트로 만들게 되면 이미 org 밑의 하위 클래스/패키지는 인식범위 안에 들어와 있으므로
    //@Bean 없이도 BoardDao 형 클래스를 찾아서 의존성을 주입하게 된다.
    @Autowired
    private BoardDao boardDao;

    Board board = new Board();





    @Test
    @DisplayName("글 작성 테스트!")
    public void insertTest(){
        //테스트에 사용하기 위한 Board 객체를 만든다.
        //이 테스트의 결과는 @Transactional에 의해, 일시적이게 된다. 이 상태에서 무엇을 저장/변경하든
        //다른 데이터베이스 등.. 에 영향을 미치지 않게 된다.

        board.setSubject("제목1!");
        board.setContent("내용1!");

        //여기서 true가 반환된다는 것은, 테이블의 변경 결과가 있다는 뜻이다.
        boolean result = boardDao.insert(board);

        //true 반환시 테스트 성공
        assertTrue(result);
    }

    @Test
    @DisplayName("글 조회 테스트!")
    public void getTest(){

        board.setSubject("제목2!");
        board.setContent("내용2!");
        boardDao.insert(board);
        assertDoesNotThrow(() -> {
            boardDao.get(7l); //시퀀스 7번째의 반환값이 오류가 발생하지 않는가?
            //이 테스트는 실행할 때 시퀀스 값을 확인해야 한다. dbeaver에서 확인
        });


    }




}
