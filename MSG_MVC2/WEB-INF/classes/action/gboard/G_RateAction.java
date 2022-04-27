package action.gboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class G_RateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateAction의 mem_id=>"+mem_id);
		
		BoardDAO avgPro=new BoardDAO();
		double avg41=avgPro.avgStar(41);
		double avg42=avgPro.avgStar(42);
		double avg43=avgPro.avgStar(43);
		double avg44=avgPro.avgStar(44);
		double avg45=avgPro.avgStar(45);
		double avg46=avgPro.avgStar(46);
		double avg47=avgPro.avgStar(47);
		double avg48=avgPro.avgStar(48);
		
		request.setAttribute("avg41", avg41);
		request.setAttribute("avg42", avg42);
		request.setAttribute("avg43", avg43);
		request.setAttribute("avg44", avg44);
		request.setAttribute("avg45", avg45);
		request.setAttribute("avg46", avg46);
		request.setAttribute("avg47", avg47);
		request.setAttribute("avg48", avg48);
		
		BoardDAO sumPro=new BoardDAO();
		int sum41=sumPro.countLikes(41);
		int sum42=sumPro.countLikes(42);
		int sum43=sumPro.countLikes(43);
		int sum44=sumPro.countLikes(44);
		int sum45=sumPro.countLikes(45);
		int sum46=sumPro.countLikes(46);
		int sum47=sumPro.countLikes(47);
		int sum48=sumPro.countLikes(48);
		
		request.setAttribute("sum41", sum41);
		request.setAttribute("sum42", sum42);
		request.setAttribute("sum43", sum43);
		request.setAttribute("sum44", sum44);
		request.setAttribute("sum45", sum45);
		request.setAttribute("sum46", sum46);
		request.setAttribute("sum47", sum47);
		request.setAttribute("sum48", sum48);
		
		
		int v_number=0;
		
		if (request.getParameter("v_number")!=null) {
			v_number=Integer.parseInt(request.getParameter("v_number"));
			System.out.println("뭔가 넘어온 v_number=>"+v_number);
		}
		System.out.println("v_number가 null일 때=>"+v_number);
		
		request.setAttribute("v_number", v_number);
		
		
		
		BoardDAO dbPro=new BoardDAO();
		
		BoardDTO article41=dbPro.getMyRate(mem_id, 41);
		System.out.println("article41=>"+article41);
		request.setAttribute("article41", article41);
		
		BoardDTO article42=dbPro.getMyRate(mem_id, 42);
		System.out.println("article42=>"+article42);
		request.setAttribute("article42", article42);
		
		BoardDTO article43=dbPro.getMyRate(mem_id, 43);
		System.out.println("article43=>"+article43);
		request.setAttribute("article43", article43);
		
		BoardDTO article44=dbPro.getMyRate(mem_id, 44);
		System.out.println("article44=>"+article44);
		request.setAttribute("article44", article44);
		
		BoardDTO article45=dbPro.getMyRate(mem_id, 45);
		System.out.println("article45=>"+article45);
		request.setAttribute("article45", article45);
		
		BoardDTO article46=dbPro.getMyRate(mem_id, 46);
		System.out.println("article46=>"+article46);
		request.setAttribute("article46", article46);
		
		BoardDTO article47=dbPro.getMyRate(mem_id, 47);
		System.out.println("article47=>"+article47);
		request.setAttribute("article47", article47);
		
		BoardDTO article48=dbPro.getMyRate(mem_id, 48);
		System.out.println("article48=>"+article48);
		request.setAttribute("article48", article48);
		
		return "/board/ghibli/G_Rate.jsp";
	}

}