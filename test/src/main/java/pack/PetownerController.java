package pack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PetownerController {
	private final PetownerService petownerService;
	
	@GetMapping("/signup")
	public String signupForm() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute PetownerDTO petownerDTO) {
		System.out.println("컨트롤러 dto: " + petownerDTO);
		petownerService.signup(petownerDTO);
		return "login";
	}
	
	@PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("email") String petownerEmail) {
        System.out.println("petownerEmail = " + petownerEmail);
        String checkResult = petownerService.emailCheck(petownerEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }
	
	// --------------
	
	@GetMapping("/{petownerId}")
    public String findById(@PathVariable Long petownerId, Model model) {
        PetownerDTO petownerDTO = petownerService.findById(petownerId);
        model.addAttribute("petowner", petownerDTO);
        return "info";
    }
	
	// --------------
	
	@GetMapping("/login")
	public String loginForm() {
        return "login";
    }
	
	@PostMapping("/login")
    public String login(@ModelAttribute PetownerDTO petownerDTO, HttpSession session, Model model) {
        PetownerDTO loginResult = petownerService.login(petownerDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getEmail());
            System.out.println("컨트롤러: 로그인 성공");
            System.out.println("이메일: " + loginResult.getEmail());
            System.out.println("비번: " + loginResult.getPassword());
            model.addAttribute("petowner", loginResult);
            return "info";
        } else {
            // login 실패
        	System.out.println("컨트롤러: 로그인 실패ㄴ");
            return "login";
        }
    }
	
	// ------------
	
	@GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String petownerEmail = (String) session.getAttribute("loginEmail");
        PetownerDTO petownerDTO = petownerService.updateForm(petownerEmail);
        model.addAttribute("updatePetowner", petownerDTO);
        return "update";
    }

	@PostMapping("/update")
	public String update(@ModelAttribute PetownerDTO petownerDTO, HttpSession session) {
	    petownerService.update(petownerDTO);
	    session.setAttribute("petownerId", petownerDTO.getPetownerId());
	    return "redirect:/info";
	}
	
	// --------------
	
	@GetMapping("/info")
	public String info(HttpSession session, Model model) {
	    Long petownerId = (Long) session.getAttribute("petownerId");
	    
	    PetownerDTO petownerDTO = petownerService.findById(petownerId);
	    model.addAttribute("petowner", petownerDTO);
	    return "info";
	}

	// ------------
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
