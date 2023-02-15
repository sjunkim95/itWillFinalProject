package com.example.spring03.dto;

//PostUpdateDTO 생성

import com.example.spring03.domain.EventPosts;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EventPostsUpdateDto {

	private Integer id;
	private String title;
	private String content;
//	private Long filesId;
	
	public EventPosts toEntity() {		
		return EventPosts.builder()
				.id(id)
				.title(title)
				.content(content)
//				.filesId(filesId)
				.build();
	}	
}
