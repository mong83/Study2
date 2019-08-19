package com.skcc.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skcc.domain.User;
import com.skcc.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	private UserRepository userRepository;
	

	@PostMapping("")
	public String create(User user) {
		
		System.out.println("User :" + user);		
		userRepository.save(user);
		return "redirect:/users"; // UserController의 @GetMapping("/list") 호출
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users" , userRepository.findAll());		
		return "/user/list";          // templates아래의 list.html 호출
	}
	
	@GetMapping("/form")
	public String form() {				
		return "/user/form";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id , Model model) {
		
		Optional<User> tempUser = userRepository.findById(id);
		User user = tempUser.get();
		
		model.addAttribute("user" , user);
		
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String updateForm(@PathVariable Long id , User newUser) {
		
		Optional<User> tempUser = userRepository.findById(id);
		User user = tempUser.get();
		
		user.update(newUser);
		userRepository.save(user);
		
		return "redirect:/users";
		
	}
}