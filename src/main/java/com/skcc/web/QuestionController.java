package com.skcc.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skcc.domain.Question;
import com.skcc.domain.QuestionRepository;
import com.skcc.domain.Result;
import com.skcc.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {

		System.out.println("여기" + HttpSessionUtills.isLoginUser(session));

		if (!HttpSessionUtills.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {

		if (!HttpSessionUtills.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtills.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);

		return "redirect:/";

	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Optional<Question> tempquestion = questionRepository.findById(id);
		Question question = tempquestion.get();
		model.addAttribute("question", question);

		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		Optional<Question> tempquestion = questionRepository.findById(id);
		Question question = tempquestion.get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
		
		


	}
	
	
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtills.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtills.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		
		return Result.ok();
	}
	


	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {

		
		Optional<Question> tempquestion = questionRepository.findById(id);
		Question question = tempquestion.get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);	
				

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id , Model model, HttpSession session) {

		Optional<Question> tempquestion = questionRepository.findById(id);
		Question question = tempquestion.get();
		Result result = valid(session, question);
		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		
		questionRepository.deleteById(id);
		return "redirect:/";
		
		
	}

}
