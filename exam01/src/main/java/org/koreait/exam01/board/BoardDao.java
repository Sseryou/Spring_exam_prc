package org.koreait.exam01.board;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component //Spring Bean 클래스로 등록
@RequiredArgsConstructor //@NotNull을 붙이거나, final을 붙여서 의존성 주입을 할 수 있게 해준다.
public class BoardDao {
    //2. Board의 DB에 접근하는 DAO(data access object를 만든다.)

    //@RequriredArgsConstructor에 의해 의존성 주입이 되어있는 상태.
    //여러번 불러오게 되면, 반복적으로 sql에 접근하게 되므로 의존성주입으로 한번만...
    private final JdbcTemplate jdbcTemplate;

    //게시글 추가
    public boolean insert(Board board){
        //sql 문장을 끊어서 읽게 시킬때는, 마지막에 한칸을 띄워 줘야 한다.
        //?에는 우리가 넣을 변수가 들어가게 된다.
        String sql = "INSERT INTO BOARD (ID, SUBJECT, CONTENT) " +
                "VALUES (SEQ_BOARD.nextval, ?, ?)";
        //jdbcTemplate.update는 몇 개의 테이블에 변경사항이 적용되었는지를 반환하게 된다.
        //sql 연산을 통해 데이터베이스를 갱신시켜 줄때 insert, delect, update를 사용하는 메서드

        int cnt = jdbcTemplate.update(sql, board.getSubject(), board.getContent());

        //아무튼 cnt가 0 이상이면, 무언가가 변경되었다는 뜻이 된다.(jdbcTempleat의 반환형은 int, 변경된 테이블 갯수)
        //이 메서드의 반환형은 논리값이므로, 논리형으로 변경한다.(변경된 테이블이 하나라도 있으면 참)
        return cnt > 0;
    }

    //4. 게시글을 조회할 수 있는 기능 구현
    //게시글 조회
    public Board get(Long id){

        try{
            //id가 없는 경우 예외가 발생한다. try-catch 사용
            String sql = "SELECT * FROM BOARD WHERE ID = ?";
            //jdbcTemplate.queryForObject는 한개의 행을 반환한다.
            //데이터가 null이면 EmptyResultDataAccessException이 발생
            Board board = jdbcTemplate.queryForObject(sql, this::boardMapper, id);
            //queryForObjects는 쿼리문 실행결과의 한개의 행을 반환하는데, this::boardMapper
            //에는 mapRow 라는 메서드가 생략된 결과가 들어가 있다.
            //mapRow는 인자로 ResultSet rs, int i 를 받는다. 반환은 boardMapper(rs, i)
            //sql이 쿼리문이 되고, this::boardMapper에 포함된 mapRow로,
            //ResultSet rs에 저장된 SELECT문의 결과를 대입하고,
            // 그 결과 중 인덱스 i 번의 결과를 mapRow에 추가로 넣는다.
            //SELECT문과 인덱스 i번째가 들어간 mapRow는, 다시 이것을 boardMapper(rs, i)로
            //반환하게 된다.
            //반환된 boardMapper는 아래의 boardMapper에 의해 board객체를 반환한다.
            //즉, this::boardMapper는 아래 구문을 실행해 board가 된다.
            //board는 테이블의 정보가 다 들어가 있다.
            //id는 String sql의 쿼리문 중 ?에 들어갈 변수이다.
            //id에 원하는 값을 입력하면 그 값이 들어간 sql 쿼리문이 실행될 것이다.

            return board;
        } catch (Exception e){
            e.printStackTrace();
            return null; //임시. 오류 페이지가 들어갈 예정
        }

    }

    //3. 테이블을 매핑할수 있는 구문 생성
    //ResultSet은 SELECT문의 결과를 저장하게 된다.
    //sql문으로 적절하지 않은 데이터가 들어갈 경우 sql예외발생, 예외전가를 시켜야 한다.
    //i는 인덱스 번호이다.


    public List<Board> gets(){

        String sql = "SELECT * FROM BOARD";
        List<Board> boards = jdbcTemplate.query(sql, this::boardMapper);
        return boards;

    }

    private Board boardMapper(ResultSet rs , int i) throws SQLException {
        //board에서 정보를 받아와서 테이블을 연동하기 위해 board 객체 생성
        Board board = new Board();
        //각 컬럼의 정보를 받아와서, set 형태로 board 객체에 넣어준다.
        board.setId(rs.getLong("ID"));
        board.setSubject(rs.getString("SUBJECT"));
        board.setContent(rs.getString("CONTENT"));
        //컬럼에서 받아온 시간(String)을 시간 형식으로 바꿔줄 필요가 있다. .toLocalDateTime()
        board.setRegDt(rs.getTimestamp("REGDT").toLocalDateTime());

        //수정 시간은 생성 시간 없이 생성 될 수 없어야 한다.
        //생성도 안했는데 수정을 할 수는 없지 않은가?
        //우선은 객체 생성만 해둔다.
        Timestamp modDt = rs.getTimestamp("MODDT");
        //생성 이후에 수정 시 수정한 시간을 받아오도록 구성
        if(modDt != null){
            //이렇게 하면 이 메서드는 최초 실행시에는 modDt가 null이기 때문에
            //이 구문은 실행되지 않는다.
            //하지만 같은 정보를 불러왔을 경우, rs.getTimestamp로 인해 값이 들어가 있으므로,
            //modDt가 생성 될 것이다.
            board.setModDt(modDt.toLocalDateTime());
        }
        //한번 거치고 나서 완성된 객체 반환.
        return board;
    }

    public Board delete(Long id){
        try{
            String sql = "DELETE * FROM BOARD WHERE ID = ?";
            Board board = jdbcTemplate.queryForObject(sql, this::boardMapper, id);
            return board;

        } catch (Exception e){
            e.printStackTrace();
            return null; //임시. 오류 페이지가 들어갈 예정
        }

    }








}
