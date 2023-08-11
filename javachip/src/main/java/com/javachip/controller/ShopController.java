package com.javachip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javachip.service.CartService;
import com.javachip.service.MileageService;
import com.javachip.service.Order_Service;
import com.javachip.vo.CartVO;
import com.javachip.vo.MileageVO;
import com.javachip.vo.Order_VO;
import com.javachip.vo.UserVO;

@Controller
@RequestMapping(value="/shop")
public class ShopController {
	
	@Autowired
	private CartService cs;
	@Autowired
	private Order_Service os;
	@Autowired
	private MileageService ms;
	
	@RequestMapping(value="/grid.do")
	public String grid() {
		return "shop/grid";
	}
	
	@RequestMapping(value="/details.do")
	public String details() {
		return "shop/details";
	}
	
	@RequestMapping(value="/checkout.do", method=RequestMethod.GET)
	public String checkout(
			HttpServletRequest req
		,	Model model
		,	String[] selCartList
			) {
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			return "redirect:/member/login.do";
		}
		
		int uNo = loginVO.getuNo();
		System.out.println("uNo::"+uNo);
		
		// 마일리지 조회
		int totalMileage = ms.selectTotalMileage(uNo);
		System.out.println("totalMileage::"+totalMileage);
		
		// 선택한 장바구니 모두 조회
		
		List<CartVO> orderList = new ArrayList<CartVO>();
		for(String items : selCartList) {
			System.out.println(items);
			int cNo = Integer.parseInt(items);
			System.out.println(cNo);
			CartVO order = cs.selectCartForOrder(cNo);
			System.out.println(order);
			orderList.add(order);
		}
		
		// 모델로 전달
		model.addAttribute("totalMileage", totalMileage);
		model.addAttribute("orderList", orderList);
		
		return "shop/checkout";
	}
	@RequestMapping(value="/checkout.do", method=RequestMethod.POST)
	public String checkout(
			HttpServletRequest req
		,	String oName
		,	String oPhone
		,	String oMail
		,	String point
		,	Model model
			) {
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			return "redirect:/member/login.do";
		}
		int uNo = loginVO.getuNo();
		// 사용할 적립금(마일리지)
		int usePoint = Integer.parseInt(point);
		System.out.println(point);
		if(usePoint != 0) {
			session.setAttribute("point", point);
			if(usePoint > ms.selectTotalMileage(uNo))
			{
				System.out.println("using point error");
				return "redirect:/";
			}
			MileageVO mv = new MileageVO();
			mv.setuNo(uNo);
			mv.setmMinus(usePoint);
			mv.setmNote("상품 결제");
			ms.minusMileage(mv);
			System.out.println("using point::"+point);
		}
				
		int totalPrice = 0;
		Order_VO ov = new Order_VO();
		ov.setuNo(loginVO.getuNo());
		ov.setoTotalPrice(totalPrice);
		ov.setoName(oName);
		// ov.setoAdd(oAdd);
		ov.setoPhone(oPhone);
		ov.setoPay("C");
		System.out.println(ov);
		int result = os.insertOrder(ov);
		if(result > 0) {
			System.out.println("주문 성공");
		}else {
			System.out.println("주문 에러");
			return "shop/checkout";
		}
		
		return "redirect:/";
	}
	
}
