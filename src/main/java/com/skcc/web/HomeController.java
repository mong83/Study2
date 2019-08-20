package com.skcc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skcc.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("")
	public String home(Model model) {
		
		System.out.println("리스트출력"+questionRepository.findAll());
		model.addAttribute("questions", questionRepository.findAll());	
			
		return "index";
	}
}