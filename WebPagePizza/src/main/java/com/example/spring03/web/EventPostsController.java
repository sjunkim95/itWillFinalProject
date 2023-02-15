package com.example.spring03.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring03.domain.EventPosts;
import com.example.spring03.dto.EventPostsCreateDto;
import com.example.spring03.dto.EventPostsUpdateDto;
import com.example.spring03.service.EventPostsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/view")
public class EventPostsController {

	private final EventPostsService eventPostsService;

	@GetMapping("/list") // 요청 URL/방식 매핑.
	public String read(Model model, /*String category,*/String title,
						@PageableDefault(page=0, size=10, sort="id", 
						direction=Direction.DESC) Pageable pageable) {
		log.info("read(title={})",title);

		// DB에서 포스트 목록 전체 검색.
		Page<EventPosts> list = eventPostsService.read(pageable, title);

		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		model.addAttribute("list", list); // 뷰에 전달하는 모델 데이터.
//		model.addAttribute("category", category) ;
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "/view/list";
		// View 이름 -> src/main/resources/templates/post/list.html
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/create") // GET 방식의 /post/create 요청을 처리하는 메서드.
	public void create() {
		log.info("create()");
		 // 컨트롤 러 메서드의 리턴 타입이 void인 경우 뷰의 이름은 요청 주소와 같음.
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create") // Post 방식의 /post/create 요청을 처리하는 메서드.
	public String create(EventPostsCreateDto dto, RedirectAttributes attrs) {
	    log.info("create(dto={})", dto);
	    
	    // 새 포스트 작성
	    EventPosts entity = eventPostsService.create(dto);
	    // 작성된 포스트의 번호(id)를 리다이렉트 되는 페이지로 전달.
	    attrs.addFlashAttribute("createdId", entity.getId());
	    
	 // PRG(Post-Redirect-Get) 패턴.
		return "redirect:/view/list";
	}

	@GetMapping({"/detail", "/modify"})
	public void detail(Integer id, Model model) {
	    log.info("detail(id={})", id);
	    log.info("modify(id={})", id);
	    
	    EventPosts post = eventPostsService.read(id);
	    
	    model.addAttribute("post", post);	    
	}
	
	@PostMapping("/delete")
	public String delete(Integer id, RedirectAttributes attrs) {
	    log.info("delete(id = {})", id);
	    
	    eventPostsService.delete(id);
	    attrs.addFlashAttribute("deletedId", id);

        return "redirect:/view/list"; //삭제한 게시판으로 이동
	}
	
	@PostMapping("/update")
    public String update(EventPostsUpdateDto dto, RedirectAttributes attrs) {
        log.info("update(dto={})", dto);
     
        Integer postId = eventPostsService.update(dto);
        attrs.addFlashAttribute("updatedId", postId);
        
        return "redirect:/view/detail?id=" + dto.getId();
	}
	
//	@GetMapping("/search")
//    public String search(String type, String keyword,
//            @PageableDefault(size = 1, sort = "id", direction = Direction.DESC) Pageable pageable, Model model) {
//        log.info("search(type={}, keyword={})", type, keyword);
//     
//        Page<EventPosts> list = eventPostsService.search(type, keyword, pageable);
//        int nowPage = list.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(nowPage - 4, 1);
//        int endPage = Math.min(nowPage + 6, list.getTotalPages());
//        
//        model.addAttribute("list", list);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("isSearchPage", "true");
//        model.addAttribute("type", type);
//        model.addAttribute("keyword", keyword);
//
//        return "/view/search";
        
//	}
	
}
