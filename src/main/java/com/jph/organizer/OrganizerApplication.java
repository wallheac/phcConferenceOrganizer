package com.jph.organizer;

import com.jph.organizer.domain.*;
import com.jph.organizer.rest.PanelSubmissionAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class OrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizerApplication.class, args);
	}

}
