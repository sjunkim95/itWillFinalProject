package com.example.spring03.dto;

import com.example.spring03.domain.EventPosts;

import lombok.Data;

@Data
public class EventPostsCreateDto {
	
	private String title;
	private String content;
	private String author;
//	private Long filesId;
//	private String category;
//	private Integer clickCount;	
//	private Integer likeCount;
//	private Integer dislikeCount;
	
	public EventPosts toEntity() {
		
		return EventPosts.builder()
				.title(title)
				.content(content)
				.author(author)
//				.category(category)
//				.filesId(filesId)
//				.clickCount(0)
//				.likeCount(0)
//				.dislikeCount(0)
				.build();
				
	}
	
}
