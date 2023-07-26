package com.assignment.todoProject.repository;

import com.assignment.todoProject.entity.Answer;
import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.entity.Member;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Board findByTitle(String subject);
    Board findByTitleAndContent(String subject, String content);
    List<Board> findByTitleLike(String subject);
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);

}
