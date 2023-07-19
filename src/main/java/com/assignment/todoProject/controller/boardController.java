package com.assignment.todoProject.controller;

import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import com.assignment.todoProject.service.BoardService;
import com.assignment.todoProject.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import java.security.Principal;

@Controller
@RequestMapping(value = "/main")
public class boardController {

    @Autowired
    private BoardService boardService;

    private final MemberService memberService;

    public boardController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 메인 페이지
    @GetMapping()
    public String main(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);

        return "boardList";
    }

    // 글쓰기 페이지
    @GetMapping("/board/write")
    public String boardWrite() {

        return  "boardwrite";
    }

    // 글쓰기 작성 처리 폼
    @PostMapping(value = "/board/write-done")
    public String boardWritePro(@Valid Board board,
                                BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/main/board/write";
        }
        Member member = this.memberService.getUser(principal.getName());
        this.boardService.boardwrite(board.getTitle(), board.getContent(), member);

        return "redirect:/main";
    }

    // 특정 글 불러오기
    @GetMapping(value = "/board/board-view/{id}")
    public String boardView(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("boardview",boardService.boardview(id));

        return "boardview";
    }

    @GetMapping("/board/board-delete")
    public String boardDelete(Integer id) {

        boardService.boarddelete(id);

        return "redirect:/main";
    }
}
