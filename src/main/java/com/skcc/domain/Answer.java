package com.skcc.domain;



import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity {
	
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
	@JsonProperty
	private Question question;
		
	@Lob
	private String contents;
	
	
	public Answer() {
		
	}

	public Answer(User writer , Question question, String contents) {
	
		this.writer = writer;
		this.question = question;
		this.contents = contents;
	}


	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	

	@Override
	public String toString() {
		return "Answer [" + super.toString() + ", writer=" + writer +  ", contents=" + contents + "]";
	}


	public boolean isSameWriter(User loginUser) {
		return loginUser.equals(this.writer);
	}



}
