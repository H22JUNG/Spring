package com.goodee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.goodee.vo.TestVO;
import com.goodee.vo.UserVO;

@Controller
public class TestController {
	
	//전통 방식
	//index.jsp페이지에서 저장1을 눌렀을 시
	@GetMapping("/test1")
	public String test1(HttpServletRequest request) {
		//request 객체에서 session을 얻어옴
		HttpSession session = request.getSession();
		//session에 data1:데이타1이라고 하는 정보를 집어넣는다
		session.setAttribute("data1", "데이타1");
		// /WEB-INF/views/result1.jsp 로 이동
		return "result1";
		//세션에 "데이타1"이라는 정보 넣어서 '저장1'누르면 세션 저장, 결과페이지로 보내기
		
		//return "redirect:/result1"; 라고 하면 바로 @GetMapping("/result1")로 이동, output1페이지 띄워짐
	}
	
	//전통 방식
	//보기1을 눌렀을 시
	@GetMapping("/result1") //결과 뿌려줄 페이지
	public String result(HttpServletRequest request, Model model) {
		// request에서 session 객체를 뽑아옴
		HttpSession session = request.getSession();
		//세션의 getAttribute를 통해 data1의 데이터를 model로, 이름을 data1로 받음
		model.addAttribute("data1", session.getAttribute("data1")); //예제용, 원래 session데이터 프론트로 넘기지 않음
		// /WEB-INF/views/output1.jsp 로 이동
		return "output1";
		//'보기1' 누르면 test1에서 세션에 저장했던 정보 불러오기
	}
	
	
	
	
	//스프링 방식
	//index.jsp 페이지에서 저장2를 눌렀을 시
	@GetMapping("/test2")
	public String test2(HttpSession session, @RequestParam("test") String test) {
		//session에 data2:test라고 하는 정보를 집어넣음(index에서 url뒤에 붙였던 파라미터)
		session.setAttribute("data2", test);
		// /WEB-INF/views/result2.jsp 로 이동
		return "result2";
	}
	
	//스프링 방식
	//보기 2를 눌렀을 시
	@GetMapping("/result2")
	//세션의 내용을 @SessionAttribute를 활용해 바로 가져와 활용이 가능하다
	//session.setAttribute("data2", "데이타2"); 의 data2가 @SessionAttribute("data2")
	public String result2(@SessionAttribute String data2, Model model) {
		//@SessionAttribute("data2")에서 ("data2") 생략해도 됨
		//Session에 있는 data2를 model에 집어넣음
		//model.addAttribute("data2", data2);
		// /WEB-INF/views/output2.jsp 로 이동
		return "output2";
	}
	
	//------------문제---------------
	
	//단순히 Param 2개 받아서 띄우기
	/*@GetMapping("/test3")
	public String test3(HttpSession session,
						@RequestParam("test3")String test3,
						@RequestParam("test4")String test4) {
		session.setAttribute("data3", test3);
		session.setAttribute("data4", test4);
		return "redirect:/result3";
	}
	
	@GetMapping("/result3")
	public String result3(@SessionAttribute("data3") String data3,
			@SessionAttribute("data4") String data4, Model model) {
		model.addAttribute("data3", data3);
		model.addAttribute("data4");
		return "output3";
	}*/

	@GetMapping("/test3")
	public String test3(HttpSession session, @RequestParam Map<String, String> map1) {
		//map에서는 키:값으로 자동으로 파라미터 저장
		//Map은 @ModelAttribute로 저장할 수 없음
		//session.setAttribute("map", map);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("data5", "cccc");
		map2.put("data6", "dddd");
		//session.setAttribute("map2", map2);
		
		
		//맵 안에 맵, 그 맵 안에 data
		Map<String, Map<String, String>> mapMerge = new HashMap<String, Map<String, String>>();
		mapMerge.put("map1", map1);
		mapMerge.put("map2", map2);
		
		session.setAttribute("map", mapMerge);
		
		return "result3";
	}
	/*@GetMapping("/result3")
	//public String result3(@SessionAttribute("map") Map<String, String> map) {
	 * 
	 * // SessionAttribute 생략 조건
	// - 파라미터의 이름과 Session의 attribute name과 일치해야 한다.
	public String result3(@SessionAttribute Map<String, String> map) {
		return "output3";
		//map으로 받아서 test3,4로 바로 출력하기
	}*/
	@GetMapping("/result3")
	public String result3() {
		return "output3";
		//test3()에 의해 세션에 정보가 저장되어있기 때문에 별도로 받아오지 않아도 됨
		//request의 경우 재요청할때 정보가 사라지지만 session은 브라우저가 종료될 때 소멸되기 때문
	}
	
	//------------문제---------------
	
	// 신형 예제 2
	// @GetMapping("/test5")
	// @RequestParam 이름 생략
	// - 조건은 Parameter의 이름과 파라미터로 쓸 매개변수의 이름이 동일하면 생략 가능(대소문자 정확히 일치해야 함)
	// Request의 primitive 타입을 변경할 경우(값에 의한 복사가 일어나서 갱신 안됨)
	//public String test5(HttpSession session, @RequestParam String test1) {
	//		System.out.println("/test5.test1 : "+test1);
	//		session.setAttribute("test1", test1);
	//		return "result5";
	//}
	

	//	@GetMapping("/result5")
	//	public String result5(@SessionAttribute String test1) {
	//		System.out.println("/result5.test1 : "+test1);
	//		test1 = "zzzz";
	//		return "output5";
	//	}
	// 값이 안바뀜
	
	//만약에 수정하고 싶으면,,, 
//		@GetMapping("/result5")
//		public String result5(HttpSession session, @SessionAttribute String test1) {
//			System.out.println("/result5.test1 : "+test1);
//			//test1 = "zzzz";
//			// Session을 불러서 똑같은 이름으로 덮어 씌움
//			session.setAttribute("test1", "zzzz");
//			return "output5";
//		}
	
	
	
	
	// Request의 레퍼런스 타입으로 값을 변경할 경우
	//Map, List, 커맨드객체는 주소에 의한 참조가 일어나기 때문에 값이 갱신됨
	//기존에 있던 Session을 덮어씌우는 것
	
	// 세션의 값에 의한 복사 및 주소에 의한 참조
	/* - Session에 저장된 값이 기본값일 경우 해당 데이터는 @SessionAttribute에서 불러올 경우 값에 의한 복사가 일어난다.
	 * - @SessionAttribute를 통해 저장한 데이터는 Session에 저장된 값에 아무런 영향을 미치지 못하며
	 *   해당 값을 메서드 내에서 변경하였다 하더라도 세션값은 그대로 유지된다.
	 * - 하지만 Session에 저장된 값이 참조형(예를들어 Map, List, VO 객체)일 경우 주소에 의한 참조가 일어난다.
	 * - @SesssionAttribute를 통해 참조가 일어나고 메서드 안에서 갱신이 일어날 경우 이 데이터는 Session에
	 *   그대로 영향을 미친다.
	 * */
	@GetMapping("/test5")
	public String test5(HttpSession session, @RequestParam Map<String, String> test1) {
		System.out.println("/test5.test1 : "+test1);
		session.setAttribute("test1", test1);
		return "result5";
	}
	
	@GetMapping("result5")
	public String result5(@SessionAttribute Map<String, String> test1) {
		System.out.println("/result5.test1 : " +test1);
		test1.put("test1", "zzzz");
		
		//String도 참조형이지만 주소에 의한 참조가 아니라 값에 의한 복사가 일어남
		//Rapper클래스(String, Integer 등)는 오브젝트인건 맞지만 기본형처럼 값을 복사함
		String s = "aaaa";
		System.out.println(s.toString());
		TestVO vo = new TestVO();
		System.out.println(vo.toString());
		
		return "output5";
	}
	//값이 갱신
	
	
	//-----------------remove-------
	@GetMapping("/remove")
	public String remove(HttpSession session) {
		//session.removeAttribute("test1"); 이건 잘 안씀
		
		//기존 세션의 정보를 전부 날려버리고 세션을 초기화할 때 쓰는 메서드
		//JSESSIONID가 변경됨
		//사용 용도 : 로그인(기존 로그인 정보 날리기 위해), 로그아웃
		session.invalidate();
		return "remove";
	}
	
	//-----------------login-------
	@GetMapping("/login")
	public String login(HttpSession session, HttpServletRequest request) {
		//DB를 통해 user정보를 가져옴
		//유저 정보가 있는지 없는지를 통해 로그인 여부를 확인하고
		UserVO vo = new UserVO();
		vo.setName("김근형");
		vo.setSerialKey("ffff");
		vo.setUserID("abcd");
		//유저 정보를 세션에 넣기 전 세션 초기화
		session.invalidate();
		// 세션을 재갱신
		session = request.getSession();
		
		session.setAttribute("user", vo);
		
		return "login";
	}
	
	//유효한 로그인 회원인지 여부를 >객체가 있는지 없는지 먼저 판단하고
	//있으면 정상적인 페이지를 보여주되 없으면 무단 사용자로 간주
	//session을 초기화 시키고 login페이지로 돌려보냄
	@GetMapping("/userInfo")
	public String getUserinfo(HttpSession session) {
		//만약 회원의 정보가 없다면?
		if(session.getAttribute("user") == null) {
			//세션을 초기화하고 로그인 창으로 돌려보냄
			session.invalidate();
			return "index";
		}
		//있으면 정상로직 실행
		return "userinfo";
	}
	
	
}
