package action.mboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class M_RateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateAction의 mem_id=>"+mem_id);
		
		BoardDAO avgPro=new BoardDAO();
		double avg31=avgPro.avgStar(31);
		double avg32=avgPro.avgStar(32);
		double avg33=avgPro.avgStar(33);
		double avg34=avgPro.avgStar(34);
		double avg35=avgPro.avgStar(35);
		double avg36=avgPro.avgStar(36);
		double avg37=avgPro.avgStar(37);
		double avg38=avgPro.avgStar(38);
		
		request.setAttribute("avg31", avg31);
		request.setAttribute("avg32", avg32);
		request.setAttribute("avg33", avg33);
		request.setAttribute("avg34", avg34);
		request.setAttribute("avg35", avg35);
		request.setAttribute("avg36", avg36);
		request.setAttribute("avg37", avg37);
		request.setAttribute("avg38", avg38);
		
		BoardDAO sumPro=new BoardDAO();
		int sum31=sumPro.countLikes(31);
		int sum32=sumPro.countLikes(32);
		int sum33=sumPro.countLikes(33);
		int sum34=sumPro.countLikes(34);
		int sum35=sumPro.countLikes(35);
		int sum36=sumPro.countLikes(36);
		int sum37=sumPro.countLikes(37);
		int sum38=sumPro.countLikes(38);
		
		request.setAttribute("sum31", sum31);
		request.setAttribute("sum32", sum32);
		request.setAttribute("sum33", sum33);
		request.setAttribute("sum34", sum34);
		request.setAttribute("sum35", sum35);
		request.setAttribute("sum36", sum36);
		request.setAttribute("sum37", sum37);
		request.setAttribute("sum38", sum38);
		
		
		int v_number=0;
		
		if (request.getParameter("v_number")!=null) {
			v_number=Integer.parseInt(request.getParameter("v_number"));
			System.out.println("뭔가 넘어온 v_number=>"+v_number);
		}
		System.out.println("v_number가 null일 때=>"+v_number);
		
		request.setAttribute("v_number", v_number);
		
		
		
		BoardDAO dbPro=new BoardDAO();
		
		BoardDTO article31=dbPro.getMyRate(mem_id, 31);
		System.out.println("article31=>"+article31);
		request.setAttribute("article31", article31);
		
		BoardDTO article32=dbPro.getMyRate(mem_id, 32);
		System.out.println("article32=>"+article32);
		request.setAttribute("article32", article32);
		
		BoardDTO article33=dbPro.getMyRate(mem_id, 33);
		System.out.println("article33=>"+article33);
		request.setAttribute("article33", article33);
		
		BoardDTO article34=dbPro.getMyRate(mem_id, 34);
		System.out.println("article34=>"+article34);
		request.setAttribute("article34", article34);
		
		BoardDTO article35=dbPro.getMyRate(mem_id, 35);
		System.out.println("article35=>"+article35);
		request.setAttribute("article35", article35);
		
		BoardDTO article36=dbPro.getMyRate(mem_id, 36);
		System.out.println("article36=>"+article36);
		request.setAttribute("article36", article36);
		
		BoardDTO article37=dbPro.getMyRate(mem_id, 37);
		System.out.println("article37=>"+article37);
		request.setAttribute("article37", article37);
		
		BoardDTO article38=dbPro.getMyRate(mem_id, 38);
		System.out.println("article38=>"+article38);
		request.setAttribute("article38", article38);
		
		return "/board/marvel/M_Rate.jsp";
	}

}