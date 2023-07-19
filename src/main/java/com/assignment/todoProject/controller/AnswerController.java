package com.assignment.todoProject.controller;

import com.assignment.todoProject.entity.Answer;
import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import com.assignment.todoProject.service.AnswerService;
import com.assignment.todoProject.service.BoardService;
import com.assignment.todoProject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/answer")
@Controller
public class AnswerController {

    private final BoardService boardService;
    private final AnswerService answerService;
    private final MemberService memberService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid Answer answer,
                               BindingResult bindingResult,
                               @RequestParam String content, Principal principal) {
        Board board = this.boardService.boardview(id);
        Member member = this.memberService.getUser(principal.getName());

        this.answerService.create(board, answer.getContent(), member);
        return String.format("redirect:/main/board/board-view/%s", id);
    }
}
