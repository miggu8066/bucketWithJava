package com.assignment.todoProject.service;

import com.assignment.todoProject.entity.Answer;
import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import com.assignment.todoProject.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Board board, String content, Member author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setBoard(board);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }
}
