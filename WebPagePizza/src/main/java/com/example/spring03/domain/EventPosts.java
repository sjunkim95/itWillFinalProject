package com.example.spring03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "EVENT_POSTS")
@SequenceGenerator(name = "EVENTPOSTS_SEQ_GEN", sequenceName = "EVENTPOSTS_SEQ",
				   initialValue = 1, allocationSize = 1)

public class EventPosts extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTPOSTS_SEQ_GEN")
	private Integer id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private String author;
	
	
	public EventPosts update(String title, String content) {
		
		this.title = title;
		this.content = content;
//		this.filesId = filesId;
		
		return this;
	}
}
