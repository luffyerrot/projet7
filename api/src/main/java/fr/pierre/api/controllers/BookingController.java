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
import fr.pierre.api.services.BookingService;
import fr.pierre.api.services.UserService;
import fr.pierre.api.entities.User;

@Controller
@RequestMapping(value = "/booking")
public class BookingController {

	@Autowired
	public UserService serviceUser;
	@Autowired
	public BookingService serviceBooking;
	@Autowired
	public BookService serviceBook;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getBookByIbn(ModelMap model) {
	    model.addAttribute("books", serviceBook.getAllBook());
	    return new ModelAndView("booking/home", model);
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView getAllBooking(ModelMap model) {
	    model.addAttribute("bookings", serviceBooking.getAllBookingNotRendered());
	    model.addAttribute("bookingshystoriques", serviceBooking.getAllBookingRendered());
	    return new ModelAndView("booking/adminhome", model);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelMap model, @RequestParam(name="ibn", required = true) Long ibn) {
		model.addAttribute("userbookingcopy", serviceBooking.userBookingCopyId());
		model.addAttribute("copies", serviceBook.getBookByIbn(ibn).getCopies());
		return new ModelAndView("booking/detail", model);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(ModelMap model, @RequestParam(name="id", required = true) Long id) {
		if (!serviceBooking.userBookingCopyId().contains(id)) {
			serviceBooking.create(id);
		}
	    return new ModelAndView("redirect:/", model);
	}
	
	@RequestMapping(value = "/extend", method = RequestMethod.GET)
	public ModelAndView extend(ModelMap model, @RequestParam(name="id", required = true)Long id, @ModelAttribute("user") User user) {
		serviceBooking.extend(id);
	    model.addAttribute("bookings", serviceBooking.getByUserId());
	    return new ModelAndView("redirect:/", model);
	}
	

	@RequestMapping(value = "/admin/rendering", method = RequestMethod.GET)
	public ModelAndView delete(ModelMap model, @RequestParam(name="id", required = true) Long id) {
		serviceBooking.changeRendering(id);
		model.addAttribute("bookings", serviceBooking.getAllBookingNotRendered());
	    model.addAttribute("bookingshystoriques", serviceBooking.getAllBookingRendered());
	    return new ModelAndView("redirect:/booking/admin", model);
	}
}
