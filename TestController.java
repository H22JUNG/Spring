package com.goodee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.service.CommentService;
import com.goodee.vo.CommentVO;

@Controller
public class TestController {

	private CommentService service;

	public TestController(CommentService service) {
		this.service = service;
	}

	// 페이지 이동
	@GetMapping("/test1")
	public String move() {
		return "test1";
	}

	// 모든 커멘트를 가져오는 개체
	@GetMapping("/comment")
	@ResponseBody
	public List<CommentVO> getAllComment() {
		return service.getCommentAllList();
	}
	// @ResponseBody를 사용하면 결과값이 자동으로 JSON타입으로 변환됨
	// list가 json으로 변환되어 리턴됨

	// 커멘트를 저장
	/*@PostMapping("/comment")
	@ResponseBody
	public Map<String, String> setComment(@RequestBody CommentVO vo) {

		return service.setComment(vo);
	}*/

	// 위의 커리 두개 한번에 받아오기
	@PostMapping("/all")
	@ResponseBody
	public Map<String, Object> all(@RequestBody CommentVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("set", service.setComment(vo));
		map.put("list", service.getCommentAllList());
		return map;
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public int delete(@RequestBody Map<String, Integer> id) {
		System.out.println("컨트롤러:" + id);
		return service.setdelete(id);
	}
}
