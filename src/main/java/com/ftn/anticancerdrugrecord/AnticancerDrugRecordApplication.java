package com.ftn.anticancerdrugrecord;

import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import com.ftn.anticancerdrugrecord.util.InferOntologyFacts;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectPatientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration.class)
public class AnticancerDrugRecordApplication {

	@Autowired
	private InferOntologyFacts utility;

	@Autowired
	private InsertUtility insertUtility;

	@Autowired
	private SelectPatientUtility selectUtility;

	public static void main(String[] args) {
		SpringApplication.run(AnticancerDrugRecordApplication.class, args);
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {
			System.out.println("In CommandLineRunnerImpl ");
			utility.inferPersonFactsByReasoner(null);
		};
	}

	private void insertLifeQuality() {
		insertUtility.insertLifeQuality(LifeQuality.Same);
		insertUtility.insertLifeQuality(LifeQuality.Worse);
		insertUtility.insertLifeQuality(LifeQuality.MuchBetter);
	}

}
