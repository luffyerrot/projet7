package fr.pierre.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.pierre.api.services.ServiceTest;

@Controller
@RequestMapping(value = "/test")
public class ControllerTest {
	
	@Autowired
	public ServiceTest serviceTest;
	
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView getBookByIbn(ModelMap model) {
	    model.addAttribute("testAllBook", serviceTest.getAllBook());
	    return new ModelAndView("book", model);
	}
	
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ModelAndView postBookByIbn(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
	    model.addAttribute("testAllBook", serviceTest.getAllBook());
	    model.addAttribute("testBook", serviceTest.getBookByIbn(ibn));
	    return new ModelAndView("book", model);
	}
}
