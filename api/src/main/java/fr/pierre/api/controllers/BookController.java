package fr.pierre.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.pierre.api.services.BookService;
import fr.pierre.apirest.entities.Book;

@Controller
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	public BookService serviceBook;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getBookByIbn(ModelMap model) {
	    model.addAttribute("testAllBook", serviceBook.getAllBook());
	    return new ModelAndView("book/book", model);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView postBookByIbn(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
	    model.addAttribute("testAllBook", serviceBook.getAllBook());
	    model.addAttribute("testBook", serviceBook.getBookByIbn(ibn));
	    return new ModelAndView("book/book", model);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createBook(ModelMap model) {
	    return new ModelAndView("book/create", model);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createBook(ModelMap model, @ModelAttribute("book") Book book, @RequestParam(name="date", required = true) String date) {
		serviceBook.create(book, date);
	    model.addAttribute("testAllBook", serviceBook.getAllBook());
	    return new ModelAndView("redirect:/book/", model);
	}
}
