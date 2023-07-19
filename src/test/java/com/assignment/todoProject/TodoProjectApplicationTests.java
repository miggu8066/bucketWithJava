package com.assignment.todoProject;

import com.assignment.todoProject.entity.Board;
import com.assignment.todoProject.repository.BoardRepository;
import com.assignment.todoProject.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class TodoProjectApplicationTests {

	@Autowired
	private BoardService boardService;

	@Test
	void contextLoads() {

	}

}
