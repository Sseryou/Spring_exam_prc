package org.koreait.exam01.controllers;


import jakarta.validation.Valid;
import org.koreait.exam01.board.Board;
import org.koreait.exam01.board.BoardDao;
import org.koreait.exam01.board.BoardWriteService;
import org.koreait.exam01.board.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


//6. 신호를 받아서 페이지를 이동시켜주기 위해 controller를 설계한다.
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardDao boardDao;

    //Controller에서 BoardWriteService를 사용하기 위한 의존성 주입
    @Autowired
    private BoardWriteService service;

    @GetMapping("/list") // /board/list 경로의 get 신호 받을시 실행
    public String boardList(Model model){
        List<Board> boards = new ArrayList<>();

        boards = boardDao.gets();
        model.addAttribute("boards", boards);

        // list 페이지로 보낸다.
        return "board/list";
    }

    @GetMapping("/write") // /board/write 경로의 get 신호 받을시 실행
    public String write(Model model){
        //BoardForm을 사용하기위한 객체 생성
        BoardForm boardForm = new BoardForm();

        //Attribute에 boardForm을 주입한다.
        model.addAttribute("boardForm", boardForm);

        // write 페이지로 보낸다.
        return "board/write";
    }

    @PostMapping("/write")
    public String writePs(@Valid BoardForm boardForm, Errors errors){
        //post 정보를 보내면 list 페이지로 보낸다.
        //@Valid에서 들어오는 값이 null은 아닌지, 잘못된 값이 들어왔는지 같은 기본적인 검증만 진행한다.
        if(errors.hasErrors()){
            //에러 발생 시, 작성 화면으로 되돌려보낸다.
            return "board/write";
        }
        service.write(boardForm);

        return "redirect:/board/list";
    }


    @GetMapping("/info") // /board/info 경로의 get 신호 받을시 실행
    public String info(Long id, Model model){
        //BoardForm을 사용하기위한 객체 생성
        Board board = boardDao.get(id);

        //Attribute에 boardForm을 주입한다.
        model.addAttribute("board", board);

        // write 페이지로 보낸다.
        return "board/info";
    }

    @PostMapping("/info")
    public String infoPs(@Valid Long id, Errors errors){

        if(errors.hasErrors()){
            //에러 발생 시, 작성 화면으로 되돌려보낸다.
            return "board/list";
        }


        return "board/info";
    }






}
