package fr.pierre.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import fr.pierre.apirest.entities.Booking;

public class BookingProcessor implements ItemProcessor<Booking, Booking> {

    @Autowired
    public JavaMailSender emailSender;
	
	@Override
	public Booking process(final Booking bookingInput) throws Exception {
		
		Booking bookingOutput = null;
		
		bookingOutput = new Booking();
		
		bookingOutput.setId(bookingInput.getId());
		bookingOutput.setBooking_date(bookingInput.getBooking_date());
		bookingOutput.setCopy(bookingInput.getCopy());
		bookingOutput.setDelay(bookingInput.getDelay());
		bookingOutput.setUser(bookingInput.getUser());
		bookingOutput.setRecall(bookingInput.getRecall() + 1);
		
		System.out.println(bookingInput.getUser() + "---------------------------------------");
		System.out.println(bookingInput.getCopy().getBook().getTitle() + "---------------------------------------");
		sendEmailToUser(bookingInput.getUser().getEmail(), bookingInput.getCopy().getBook().getTitle());
		
		return bookingOutput;
	}
	
	private void sendEmailToUser(String email, String title) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Rendering book named : " + title.toString());
        message.setText("Hello, You have received this automated email to warn you that the rental date has passed.");
        
        this.emailSender.send(message);
	}
}
