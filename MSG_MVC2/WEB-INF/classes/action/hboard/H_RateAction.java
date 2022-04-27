package action.hboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class H_RateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateAction의 mem_id=>"+mem_id);
		
		BoardDAO avgPro=new BoardDAO();
		double avg11=avgPro.avgStar(11);
		double avg12=avgPro.avgStar(12);
		double avg13=avgPro.avgStar(13);
		double avg14=avgPro.avgStar(14);
		double avg15=avgPro.avgStar(15);
		double avg16=avgPro.avgStar(16);
		double avg17=avgPro.avgStar(17);
		double avg18=avgPro.avgStar(18);
		
		request.setAttribute("avg11", avg11);
		request.setAttribute("avg12", avg12);
		request.setAttribute("avg13", avg13);
		request.setAttribute("avg14", avg14);
		request.setAttribute("avg15", avg15);
		request.setAttribute("avg16", avg16);
		request.setAttribute("avg17", avg17);
		request.setAttribute("avg18", avg18);
		
		BoardDAO sumPro=new BoardDAO();
		int sum11=sumPro.countLikes(11);
		int sum12=sumPro.countLikes(12);
		int sum13=sumPro.countLikes(13);
		int sum14=sumPro.countLikes(14);
		int sum15=sumPro.countLikes(15);
		int sum16=sumPro.countLikes(16);
		int sum17=sumPro.countLikes(17);
		int sum18=sumPro.countLikes(18);
		
		request.setAttribute("sum11", sum11);
		request.setAttribute("sum12", sum12);
		request.setAttribute("sum13", sum13);
		request.setAttribute("sum14", sum14);
		request.setAttribute("sum15", sum15);
		request.setAttribute("sum16", sum16);
		request.setAttribute("sum17", sum17);
		request.setAttribute("sum18", sum18);
		
		
		int v_number=0;
		
		if (request.getParameter("v_number")!=null) {
			v_number=Integer.parseInt(request.getParameter("v_number"));
			System.out.println("뭔가 넘어온 v_number=>"+v_number);
		}
		System.out.println("v_number가 null일 때=>"+v_number);
		
		request.setAttribute("v_number", v_number);
		
		
		
		BoardDAO dbPro=new BoardDAO();
		
		BoardDTO article11=dbPro.getMyRate(mem_id, 11);
		System.out.println("article11=>"+article11);
		request.setAttribute("article11", article11);
		
		BoardDTO article12=dbPro.getMyRate(mem_id, 12);
		System.out.println("article12=>"+article12);
		request.setAttribute("article12", article12);
		
		BoardDTO article13=dbPro.getMyRate(mem_id, 13);
		System.out.println("article13=>"+article13);
		request.setAttribute("article13", article13);
		
		BoardDTO article14=dbPro.getMyRate(mem_id, 14);
		System.out.println("article14=>"+article14);
		request.setAttribute("article14", article14);
		
		BoardDTO article15=dbPro.getMyRate(mem_id, 15);
		System.out.println("article15=>"+article15);
		request.setAttribute("article15", article15);
		
		BoardDTO article16=dbPro.getMyRate(mem_id, 16);
		System.out.println("article16=>"+article16);
		request.setAttribute("article16", article16);
		
		BoardDTO article17=dbPro.getMyRate(mem_id, 17);
		System.out.println("article17=>"+article17);
		request.setAttribute("article17", article17);
		
		BoardDTO article18=dbPro.getMyRate(mem_id, 18);
		System.out.println("article18=>"+article18);
		request.setAttribute("article18", article18);
		
		return "/board/harry/H_Rate.jsp";
	}

}