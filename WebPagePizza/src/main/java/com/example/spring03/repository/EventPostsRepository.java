package com.example.spring03.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring03.domain.EventPosts;

public interface EventPostsRepository extends JpaRepository<EventPosts, Integer>{

	// select * from POSTS order by ID desc
	Page<EventPosts> findByOrderByIdDesc(Pageable pageable);
	
	Page<EventPosts> findByTitleContaining(String title, Pageable pageable);
	Page<EventPosts> findByContentContaining(String content, Pageable pageable);
//	Page<EventPosts> findByAuthorContaining(String author, Pageable pageable);
	Page<EventPosts> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
	
//	Page<EventPosts> findByCategoryIgnoreCaseContainingOrderByIdDesc(String category, Pageable pageable);
	
//	// 조회수 내림차순 검색
//	List<EventPosts> findByClickCountIsNotNullOrderByClickCountDesc();
	
	// 특정 아이디 게시글 목록
	List<EventPosts> findByAuthorContaining(String author);
	
	//  JPQL(Java Persistence Query Language)
//    // 검색
//	@Query(
//	        "select s from EVENT_POSTS s "
//	            + "where lower(s.title) like lower('%' || :keyword || '%') "
//	            + "or lower(s.content) like lower('%' || :keyword || '%') "
//	            + "order by s.id desc"
//	)
//	Page<EventPosts> searchByKeyword(@Param(value = "keyword") String keyword, Pageable pageable);

    List<EventPosts> findByOrderByIdDesc();
	
//	@Query(
//            "select s from EVENT_POSTS s where s.clickCount > 0 " 
//	        + "AND ROWNUM <= 7 order by s.clickCount desc")
//	List<EventPosts> clickCountOrder();
}
