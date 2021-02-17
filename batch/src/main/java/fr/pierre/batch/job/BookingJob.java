package fr.pierre.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.pierre.apirest.entities.Booking;
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
	
	@Bean
	public Job importUserJob() {
	  return this.jobBuilderFactory.get("importUserJob")
			  .start(step1())
			  .build();
	}

	@Bean
	public Step step1() {
	  return this.stepBuilderFactory.get("step1")
	    .<Booking, Booking> chunk(10)
	    .reader(new BookingReader())
	    .processor(new BookingProcessor())
	    .writer(new BookingWriter())
	    .build();
	}
}
