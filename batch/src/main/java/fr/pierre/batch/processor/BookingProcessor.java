package fr.pierre.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import fr.pierre.apirest.entities.Booking;

public class BookingProcessor implements ItemProcessor<Booking, Booking> {
	
	@Autowired
	JavaMailSenderImpl emailSender;
	
	@Override
	public Booking process(final Booking bookingInput) throws Exception {
		
		if(bookingInput.getRecall() < 8) {
			Booking bookingOutput = null;
			bookingOutput = new Booking();
			
			bookingOutput.setId(bookingInput.getId());
			bookingOutput.setBooking_date(bookingInput.getBooking_date());
			bookingOutput.setCopy(bookingInput.getCopy());
			bookingOutput.setDelay(bookingInput.getDelay());
			bookingOutput.setUser(bookingInput.getUser());
			bookingOutput.setRendering(bookingInput.getRendering());
			bookingOutput.setRecall(bookingInput.getRecall() + 1);

			sendEmailToUser(bookingInput.getUser().getEmail(), bookingInput.getCopy().getBook().getTitle());

			System.out.println("-------------------p " + bookingOutput);
			return bookingOutput;
		} else {
			return bookingInput;
		}
	}
	
	private void sendEmailToUser(String email, String title) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("redonne@alabiblio.depeche");
        message.setTo(email);
        message.setSubject("Rendering book named : " + title.toString());
        message.setText("Hello, You have received this automated email to warn you that the rental date has passed.");
        
        if (this.emailSender != null) {
            this.emailSender.send(message);
        } else {
        	System.out.println("Erreur lors de la configuration du mail");
        }
	}
}
