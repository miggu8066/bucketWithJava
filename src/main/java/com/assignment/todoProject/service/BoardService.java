package com.assignment.todoProject.service;

import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import com.assignment.todoProject.repository.BoardRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    // 글 작성 폼 처리
    public void boardwrite(String title, String content, Member user) {

        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setAuthor(user);
        this.boardRepository.save(b);

    }
    // 글 목록 처리
    public List<Board> boardListAll() {
        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기 처리
    public Board boardview(Integer id) {

        return boardRepository.findById(id).get();
    }

    // 글 삭제 처리
    public void boarddelete(Integer id) {

        boardRepository.deleteById(id);
    }

    // 페이징 처리
    public Page<Board> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.boardRepository.findAll(pageable);
    }

}
