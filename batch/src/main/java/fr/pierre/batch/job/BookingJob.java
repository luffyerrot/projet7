package fr.pierre.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import fr.pierre.apirest.entities.Booking;
import fr.pierre.batch.listener.JobCompletionNotificationListener;
import fr.pierre.batch.processor.BookingProcessor;
import fr.pierre.batch.reader.BookingReader;
import fr.pierre.batch.writer.BookingWriter;

@Configuration
@EnableBatchProcessing
public class BookingJob {

	@Autowired
  	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private Environment environment;
  
  	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
  
	@Bean
	public ItemReader<Booking> bookingReader() {
        return new BookingReader(this.environment.getRequiredProperty("booking.url") + "getdate/", restTemplate());
    }
	
	@Bean
	public BookingProcessor bookingProcessor() {
	  return new BookingProcessor();
	}
	
	@Bean
	public ItemWriter<Booking> writer(Environment environment, RestTemplate restTemplate) {
	  return new BookingWriter(environment.getRequiredProperty("booking.url") + "update/", restTemplate);
	}
	
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	  return jobBuilderFactory.get("importUserJob")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .flow(step1)
	    .end()
	    .build();
	}

	@Bean
	public Step step1(BookingWriter writer) {
	  return stepBuilderFactory.get("step1")
	    .<Booking, Booking> chunk(10)
	    .reader(bookingReader())
	    .processor(bookingProcessor())
	    .writer(writer)
	    .build();
	}
}
