package com.skcc.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.domain.Answer;
import com.skcc.domain.AnswerRepository;
import com.skcc.domain.Question;
import com.skcc.domain.QuestionRepository;
import com.skcc.domain.Result;
import com.skcc.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId , String contents , HttpSession session) {
		
		if(!HttpSessionUtills.isLoginUser(session)) {
			return null;
		}

		User loginUser = HttpSessionUtills.getUserFromSession(session);
		Optional<Question> tempquestion = questionRepository.findById(questionId);
		Question question = tempquestion.get();
		
		Answer answer = new Answer(loginUser, question, contents);
		question.addAnswer();
		return answerRepository.save(answer);
	}
	

	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if (!HttpSessionUtills.isLoginUser(session)) {
			return Result.fail("로그인해야 합니다.");
		}
		
		Optional<Answer> tmepAnswer = answerRepository.findById(id);
		Answer answer = tmepAnswer.get();
		User loginUser = HttpSessionUtills.getUserFromSession(session);
		
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.deleteById(id);
		
		Optional<Question> tempquestion = questionRepository.findById(questionId);
		Question question = tempquestion.get();
		
	    question.deleteAnswer();
	    questionRepository.save(question);
		return Result.ok();
	}
	

}
