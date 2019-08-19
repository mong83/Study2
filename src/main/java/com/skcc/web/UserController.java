package com.skcc.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	
	
	@GetMapping("/loginForm")
	public String loginForm() {				
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId , String password , HttpSession sessoin) {
		
		
		User user = userRepository.findByUserId(userId);  // pk아닌 userId로 조회하려면 repository에  findByUserId 정의
		
		
		if(user==null) {
			return "redirect:/users/loginForm";
		}
		
		if(!password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}
		
		sessoin.setAttribute("user" , user);  // session에 login user정보 저장
		
		return "redirect:/";  // home 호출 
	}
	

	@GetMapping("/logout")
	public String loginout(HttpSession sessoin) {	
		sessoin.removeAttribute("user");      // session에 login user정보 삭제
		return "redirect:/";
	}
	
	
	@PostMapping("")
	public String create(User user) {
		
		System.out.println("User :" + user);		
		userRepository.save(user);
		return "redirect:/users"; // UserController의 @GetMapping("/list") 호출
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users" , userRepository.findAll());		
		return "/user/list";          //사용자리스트 , templates아래의 list.html 호출
	}
	
	@GetMapping("/form")
	public String form() {				
		return "/user/form";  //회원가입
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id , Model model) {
		
		Optional<User> tempUser = userRepository.findById(id);
		User user = tempUser.get();
		
		model.addAttribute("user" , user);
		
		return "/user/updateForm"; //회원정보수정
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