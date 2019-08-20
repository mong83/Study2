package com.skcc.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skcc.domain.Question;
import com.skcc.domain.QuestionRepository;
import com.skcc.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		
		System.out.println("여기"+HttpSessionUtills.isLoginUser(session));
		
		if(!HttpSessionUtills.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	
	@PostMapping("")
	public String create(String title, String contents,HttpSession session) {


		if(!HttpSessionUtills.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtills.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser.getUserId(),title,contents);
		questionRepository.save(newQuestion);	

		return "redirect:/";
		
	}
}
