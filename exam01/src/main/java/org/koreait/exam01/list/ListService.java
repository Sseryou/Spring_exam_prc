package org.koreait.exam01.list;


import org.koreait.exam01.board.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    //BoardDao의 기능을 사용하기 위해 의존성 주입
    private BoardDao boardDao;

    @Autowired
    public void setBoardDao(BoardDao boardDao){
        this.boardDao = boardDao;
    }





}
