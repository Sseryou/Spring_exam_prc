package org.koreait.exam01.board;

import org.koreait.exam01.boards.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardWriteService {

    //BoardDao의 기능을 사용하기 위해 의존성 주입
    private BoardDao boardDao;

    @Autowired
    public void setBoardDao(BoardDao boardDao){
        this.boardDao = boardDao;
    }

    public void write(BoardForm boardForm){
        Board board = new Board();
        board.setSubject(boardForm.getSubject());
        board.setContent(boardForm.getContent());

        boardDao.insert(board);

    }

}
