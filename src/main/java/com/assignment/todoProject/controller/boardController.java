package com.assignment.todoProject.controller;

import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import com.assignment.todoProject.service.BoardService;
import com.assignment.todoProject.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

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
    public String main(Model model,
                       @RequestParam(value = "page", defaultValue = "0")
                       int page,
                       String searchKeyword) {
        Page<Board> paging = null;

        if(searchKeyword == null) {
            paging = this.boardService.getList(page);
        }else {
            paging = this.boardService.boardSearchList(searchKeyword, Pageable.ofSize(page));
        }
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
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/board/board-view/{id}")
    public String boardView(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("boardview",boardService.boardview(id));

        return "boardview";
    }

    // 게시글 수정 글 불러오기
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/board/modify/{id}")
    public String boardModify(Model model,
                              @PathVariable("id") Integer id) {

        model.addAttribute("boardModify",boardService.boardview(id));

        return "boardmodify";
    }
    //게시글 수정 하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/board/modifyDone/{id}")
    public String boardModifyDone(@PathVariable("id") Integer id, @Valid Board board, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "boardmodify";
        }
        Board boardTemp = boardService.boardview(id);
        if (!boardTemp.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setModifiedDate(board.getModifiedDate());
        boardService.modify(boardTemp, id);

        return "redirect:/main";
    }

    // 게시글 삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.boardview(id);
        if (!board.getAuthor().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.boarddelete(board);

        return "redirect:/main";
    }

    // 추천 기능
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.boardview(id);
        Member member = this.memberService.getUser(principal.getName());
        this.boardService.vote(board, member);
        return String.format("redirect:/main/board/board-view/%s", id);
    }
}
