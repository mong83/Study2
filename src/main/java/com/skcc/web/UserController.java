package com.skcc.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	private List<User> users = new ArrayList<User>();

	@PostMapping("/create")
	public String create(User user) {
		
		System.out.println("User :" + user);
		users.add(user);
		return "redirect:/list"; // UserController의 @GetMapping("/list") 호출
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users" , users);		
		return "list";          // templates아래의 list.html 호출
	}
}