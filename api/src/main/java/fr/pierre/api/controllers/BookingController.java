package fr.pierre.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.pierre.api.services.BookService;
import fr.pierre.api.services.BookingService;
import fr.pierre.apirest.entities.Booking;

@Controller
@RequestMapping(value = "/booking")
public class BookingController {
	
	@Autowired
	public BookingService serviceBooking;
	@Autowired
	public BookService serviceBook;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getBookByIbn(ModelMap model) {
		List<Long> ids = new ArrayList();
		List<Booking> user = serviceBooking.getByUserId();
		for (int i =0; i < user.size(); i++){
			ids.add(user.get(i).getCopy().getId());
		}
	    model.addAttribute("ids", ids);
	    model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("booking/home", model);
	}
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model, @RequestParam(name="copyid", required = true) Long copyid) {
		serviceBooking.create(copyid);
	    return new ModelAndView("redirect:/", model);
	}
}
