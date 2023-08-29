package com.javachip.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javachip.service.AddressService;
import com.javachip.service.CartService;
import com.javachip.service.HelpService;
import com.javachip.service.MileageService;
import com.javachip.service.Order_DetailService;
import com.javachip.service.Order_Service;
import com.javachip.service.ProductService;
import com.javachip.service.ReviewService;
import com.javachip.vo.AddressVO;
import com.javachip.vo.CartVO;
import com.javachip.vo.MileageVO;
import com.javachip.vo.Order_DetailVO;
import com.javachip.vo.Order_VO;
import com.javachip.vo.PageMaker;
import com.javachip.vo.ProductVO;
import com.javachip.vo.QnaVO;
import com.javachip.vo.ReviewVO;
import com.javachip.vo.SearchVO;
import com.javachip.vo.UserVO;

@Controller
@RequestMapping(value="/shop")
public class ShopController {
	
	@Autowired
	private CartService cs;
	@Autowired
	private Order_Service os;
	@Autowired
	private Order_DetailService ods;
	@Autowired
	private MileageService ms;
	@Autowired
	private ProductService ps;
	@Autowired
	private ReviewService rs;
	@Autowired
	private PageMaker pm;
	@Autowired
	private AddressService as;
	@Autowired
	private HelpService hs;
	
	@RequestMapping(value="/grid.do")
	public String grid(
			Model model
		,	SearchVO searchVO
		) {
		int cnt = ps.totalProduct(searchVO); // 총 상품 개수
		searchVO.setPerPageNum(12);			 // 페이지 당 상품 개수
		searchVO.calcStart();				 // 쿼리용 계산함수
		//System.out.println("totalProduct::"+cnt);
		//System.out.println("page::"+searchVO.getPage());
		//System.out.println("startNum::"+searchVO.getStartNum());
		if(searchVO.getSort() == null || searchVO.getSort().equals("")) {
			searchVO.setSort("new");
		}
		pm.setSearchVO(searchVO);
		pm.setTotalCount(cnt);
		List<ProductVO> productList = ps.selectAllProduct(searchVO);
		model.addAttribute("productList", productList);
		model.addAttribute("pm", pm);
		return "shop/grid";
	}
	
	@RequestMapping(value="/details.do")
	public String details(
			HttpServletRequest req
		,	Model model
		,	int pNo
			) {
		ProductVO pv = ps.selectOneProduct(pNo);
		System.out.println(pv);
		
		// searchVO 설정 고정
		SearchVO searchVO = new SearchVO();
		searchVO.setSearchType("pNo");
		searchVO.setSearchValue(String.valueOf(pNo));
		
		// 리뷰 조회 + 작성 가능한지
		List<ReviewVO> reviewList = rs.selectReview(searchVO);
		
		boolean canReview = false;
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO!=null) {
			int uNo = loginVO.getuNo();
			List<Order_VO> orderList = os.selectUserOrder(uNo);
			for(Order_VO items : orderList) {
				List<Order_DetailVO> odList = ods.selectOrderDetail(items.getoNo());
				for(Order_DetailVO dItems : odList) {
					if(dItems.getpNo() == pNo) {
						canReview = true;
					}
				}
			}
		}
		
		// QnA 조회
		List<QnaVO> qnaList = hs.selectQnaList(searchVO);
		
		
		model.addAttribute("pv", pv);
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("canReview", canReview);
		
		model.addAttribute("qnaList",qnaList);
		return "shop/details";
	}
	
	@RequestMapping(value="/reviewWrite.do")
	@ResponseBody
	public int reviewWrite(
				HttpServletRequest req
			,	int pNo
			,	String rContents
			) {
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			return -1;
		}
		
		int uNo = loginVO.getuNo();
		System.out.println("uNo::"+uNo);
		System.out.println("rContents::"+rContents);
		
		ReviewVO rv = new ReviewVO();
		rv.setuNo(uNo);
		rv.setpNo(pNo);
		rv.setrContents(rContents);
		
		int result = rs.insertReview(rv);
		if(result != 1) {
			return 2;
		}
		return 1;
	}
	
	@RequestMapping(value="deleteReview.do")
	@ResponseBody
	public int deleteReview(
			int rNo
			) {
		int result = rs.deleteOneReview(rNo);
		return result;
	}
	
	@RequestMapping(value="updateReview.do")
	@ResponseBody
	public int updateReview(
			ReviewVO rv
			) {
		System.out.println(rv);
		int result = rs.updateReview(rv);
		return result;
	}
	
	@RequestMapping(value="/addCart.do")
	@ResponseBody
	public int addCart(
			HttpServletRequest req
			,	int pNo
			,	int cCount
			) {
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			return -1;
		}
		
		int uNo = loginVO.getuNo();
		System.out.println("uNo::"+uNo);
		System.out.println("pNo::"+pNo);
		System.out.println("cCount::"+cCount);
		
				
		CartVO cv = new CartVO();
		cv.setuNo(uNo);
		cv.setpNo(pNo);
		cv.setcCount(cCount);
		
		int checkCart = cs.checkDupCart(cv);
		if(checkCart > 0) {
			// 중복
			return 2;
		}
		int result = cs.addCart(cv);
		if(result != 1) {
			// 오류
			System.out.println(result);
			return 0;
		}
		return result;
	}
	
	@RequestMapping(value="/buyNow.do")
	public void buyNow(
			HttpServletRequest req
			,	Model model
			,	int pNo
			,	int cCount
			,	HttpServletResponse res
			,	RedirectAttributes rttr
			) throws IOException {
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = res.getWriter();
		
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			pw.append("<script>alert('로그인이 필요한 서비스입니다.');location.href='"+req.getContextPath()+"/member/login.do';</script>");
			pw.flush();
			return;
		}
		int uNo = loginVO.getuNo();
		System.out.println("uNo::"+uNo);
		
		// 장바구니에 존재하는 지 검사
		List<CartVO> cart = cs.selectCartByUno(uNo);
		for(CartVO items : cart) {
			if(items.getpNo() == pNo) {
				pw.append("<script>alert('이미 장바구니에 있는 상품입니다.');location.href='"+req.getContextPath()+"/mypage/cart.do';</script>");
				pw.flush();
				return;
			}
		}
		int totalMileage = ms.selectTotalMileage(uNo);
		System.out.println("totalMileage::"+totalMileage);
		
		CartVO cv = new CartVO();
		cv.setuNo(uNo);
		cv.setpNo(pNo);
		cv.setcCount(cCount);
		int result = cs.addCart(cv);
		if(result <= 0) {
			pw.append("<script>alert('오류가 발생했습니다.');location.href='details.do?pNo='"+pNo+";</script>");
			pw.flush();
			return;
		}
		int cNo = cv.getcNo();
		
		/*
		 * List<CartVO> orderList = new ArrayList<CartVO>(); 
		 * CartVO order = cs.selectCartForOrder(cNo);
		 * orderList.add(order);
		 * model.addAttribute("totalMileage", totalMileage);
		 * model.addAttribute("orderList", orderList);
		 */
		
		pw.append("<form id='buyNow' method='get' action='checkout.do'>"
				+ "<input type='text' name='selCartList' value='"+cNo+"'>"
				+ "</form>"
				+ "<script>window.onload=function(){document.getElementById('buyNow').submit();}</script>");
		pw.flush();
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
		System.out.println(selCartList);
		
		// 주소 목록 출력
		AddressVO addvo = as.addressSelect(uNo);
		System.out.println("Addr1:"+addvo.getAddr1());
		
		// 마일리지 조회
		int totalMileage = 0;
		if(ms.selectTotalMileage(uNo) != 0) {
			totalMileage = ms.selectTotalMileage(uNo);
		}
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
		model.addAttribute("addvo", addvo);
		
		return "shop/checkout";
	}
	@RequestMapping(value="/checkout.do", method=RequestMethod.POST)
	public String checkout(
			HttpServletRequest req
		,	String point
		,	String total
		,	String[] cNo
		,	Model model
			) {
		HttpSession session = req.getSession();
		UserVO loginVO = (UserVO)session.getAttribute("login");
		if(loginVO==null) {
			return "redirect:/member/login.do";
		}
		int uNo = loginVO.getuNo();
		System.out.println("uNo::"+uNo);
		
		System.out.println("total::"+total);
		int totalPrice = Integer.parseInt(total);
		
		
		Order_VO ov = new Order_VO();
		ov.setuNo(uNo);
		ov.setoTotalPrice(totalPrice);
		ov.setoPay("C");
		System.out.println(ov);
		
		int result = os.insertOrder(ov);
		int oNo = ov.getoNo();
		
		if(result == 0) {
			
			// 구매한 장바구니를 주문 상세로
			for(String items : cNo) {
				int cartNo = Integer.parseInt(items);
				CartVO cv = cs.selectCartForOrder(cartNo);
				Order_DetailVO odvo = new Order_DetailVO();
				
				odvo.setoNo(oNo);
				odvo.setpNo(cv.getpNo());
				odvo.setdCount(cv.getcCount());
				
				ods.insertOrderDetail(odvo);
				
				cs.deleteOneCart(cartNo);
			}
			
			// 사용할 적립금(마일리지)
			int usePoint = Integer.parseInt(point);
			System.out.println("point::"+point);
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
			
			// 적립될 마일리지
			MileageVO insertMV = new MileageVO();
			insertMV.setuNo(uNo);
			double addMileage = totalPrice*0.05;
			insertMV.setmPlus((int)addMileage);
			insertMV.setmNote("상품 결제를 통한 적립");
			ms.plusMileage(insertMV);
			System.out.println("주문 성공");
		}else {
			System.out.println("주문 에러");
			return "shop/checkout";
		}
		return "redirect:/";
	}
	
	// 배송지 팝업
	@RequestMapping(value="/addressPopup.do")
    public String addressSelect() {
		return "shop/addressPopup";
	}
	
	
}
