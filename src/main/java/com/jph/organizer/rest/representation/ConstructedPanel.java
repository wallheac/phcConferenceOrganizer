package com.jph.organizer.rest.representation;

import java.util.List;

public class ConstructedPanel {
	private String title;
	private List<Paper> papers;
	
	public ConstructedPanel(String title, List<Paper> papers) {
		this.title = title;
		this.papers = papers;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public List<Paper> getPapers(){
		return this.papers;
	}
}
