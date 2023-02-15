package com.example.spring03.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring03.domain.EventPosts;
import com.example.spring03.dto.EventPostsCreateDto;
import com.example.spring03.dto.EventPostsUpdateDto;
import com.example.spring03.repository.EventPostsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor	// final 필드를 초기화하는 생성자
@Service	// spring 컨텍스트에 Service 컴포넌트로 등록
public class EventPostsService {

	private final EventPostsRepository eventPostsRepository;
	
	// 목록 보기
	public Page<EventPosts> read(Pageable pageable, String title/*, String category*/){
		log.info("read(title={})", title);
		return eventPostsRepository.findByOrderByIdDesc(pageable);
	}
	
	public List<EventPosts> read(){
		log.info("read()");
		return eventPostsRepository.findByOrderByIdDesc();
		
	}
	
	// 새글 작성
	public EventPosts create(EventPostsCreateDto dto) {
		log.info("create(dto={})", dto);
		EventPosts entity = eventPostsRepository.save(dto.toEntity());
		return entity;
	}

	// 특정 게시글 보기
    public EventPosts read(Integer id) {
        log.info("readf(id={})", id);
        EventPosts post = eventPostsRepository.findById(id).get();
        return post;
    }

	// 삭제
	public Integer delete(Integer id) {
		log.info("delete(id={})", id);
		eventPostsRepository.deleteById(id);
		return id;
	}
	
	@Transactional
	public Integer update(EventPostsUpdateDto dto) {
		log.info("update(dto={})", dto);
		EventPosts entity = eventPostsRepository.findById(dto.getId()).get();
		entity.update(dto.getTitle(), dto.getContent()/*, dto.getFilesId()*/);
		return entity.getId();
	}
	
//	public Page<EventPosts> search(String type, String keyword, Pageable pageable) {
//		log.info("search(type = {}, keyword ={})", type, keyword);
//		List<EventPosts> list = new ArrayList<>();
//
//		Page<EventPosts> page = new PageImpl<>(list, pageable, list.size());
//		switch (type) {
//		case "t":
//			page = eventPostsRepository.findByTitleContaining(keyword, pageable);
//			break;
//		case "c":
//			page = eventPostsRepository.findByContentContaining(keyword, pageable);
//			break;
//		case "a":
//			page = eventPostsRepository.findByAuthorContaining(keyword, pageable);
//			break;
//		case "tc":
//			page = eventPostsRepository.searchByKeyword(keyword, pageable);
//		}	
//
//		return page;
//	}
//	
//	// 작성자 별 게시글
//	public List<EventPosts> readByAuthor(String author) {
//        log.info("readByAuthor(author = {})", author);
//        return eventPostsRepository.findByAuthorContaining(author);
//    }
	
}
