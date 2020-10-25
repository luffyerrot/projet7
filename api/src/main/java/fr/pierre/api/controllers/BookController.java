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
	    model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("book/home", model);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView postBookByIbn(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
	    model.addAttribute("book", serviceBook.getBookByIbn(ibn));
	    if (serviceBook.getBookByIbn(ibn).getCopies() != null) {
		    model.addAttribute("copyNum", serviceBook.getBookByIbn(ibn).getCopies().size());
	    } else {
		    model.addAttribute("copyNum", 0);
	    }
	    model.addAttribute("copies", serviceBook.getBookByIbn(ibn).getCopies());
	    return new ModelAndView("book/detail", model);
	}
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model) {
	    return new ModelAndView("book/create", model);
	}
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.POST)
	public ModelAndView create(ModelMap model, @ModelAttribute("book") Book book, @RequestParam(name="date", required = true) String date) {
		serviceBook.create(book, date);
		model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("redirect:/book/", model);
	}
	
	@RequestMapping(value = "/admin/update", method = RequestMethod.GET)
	public ModelAndView update(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
	    model.addAttribute("bookIbn", serviceBook.getBookByIbn(ibn));
	    return new ModelAndView("book/update", model);
	}
	
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public ModelAndView update(ModelMap model, @ModelAttribute("book") Book book) {
		serviceBook.update(book, book.getIbn());
	    model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("redirect:/book/", model);
	}
	
	@RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
	public ModelAndView delete(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
		serviceBook.delete(ibn);
	    model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("redirect:/book/", model);
	}
}
