package com.skcc.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skcc.domain.Answer;
import com.skcc.domain.AnswerRepository;
import com.skcc.domain.Question;
import com.skcc.domain.QuestionRepository;
import com.skcc.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId , String contents , HttpSession session) {
		
		if(!HttpSessionUtills.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User loginUser = HttpSessionUtills.getUserFromSession(session);
		Optional<Question> tempquestion = questionRepository.findById(questionId);
		Question question = tempquestion.get();
		
		Answer answer = new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		System.out.println("답변뭘까"+answer);
		return String.format("redirect:/questions/%d", questionId);
	}
		

}
