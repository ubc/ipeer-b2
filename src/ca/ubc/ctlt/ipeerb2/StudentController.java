package ca.ubc.ctlt.ipeerb2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

	@RequestMapping("/helloworld")
	public ModelAndView helloWord() {
		String message = "Hello World, Spring 3.0!";
		return new ModelAndView("helloworld", "message", message);
	}
}
