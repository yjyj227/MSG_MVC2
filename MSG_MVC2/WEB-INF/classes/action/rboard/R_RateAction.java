package action.rboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class R_RateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateAction의 mem_id=>"+mem_id);
		
		BoardDAO avgPro=new BoardDAO();
		double avg51=avgPro.avgStar(51);
		double avg52=avgPro.avgStar(52);
		double avg53=avgPro.avgStar(53);
		double avg54=avgPro.avgStar(54);
		double avg55=avgPro.avgStar(55);
		double avg56=avgPro.avgStar(56);
		double avg57=avgPro.avgStar(57);
		double avg58=avgPro.avgStar(58);
		
		request.setAttribute("avg51", avg51);
		request.setAttribute("avg52", avg52);
		request.setAttribute("avg53", avg53);
		request.setAttribute("avg54", avg54);
		request.setAttribute("avg55", avg55);
		request.setAttribute("avg56", avg56);
		request.setAttribute("avg57", avg57);
		request.setAttribute("avg58", avg58);
		
		BoardDAO sumPro=new BoardDAO();
		int sum51=sumPro.countLikes(51);
		int sum52=sumPro.countLikes(52);
		int sum53=sumPro.countLikes(53);
		int sum54=sumPro.countLikes(54);
		int sum55=sumPro.countLikes(55);
		int sum56=sumPro.countLikes(56);
		int sum57=sumPro.countLikes(57);
		int sum58=sumPro.countLikes(58);
		
		request.setAttribute("sum51", sum51);
		request.setAttribute("sum52", sum52);
		request.setAttribute("sum53", sum53);
		request.setAttribute("sum54", sum54);
		request.setAttribute("sum55", sum55);
		request.setAttribute("sum56", sum56);
		request.setAttribute("sum57", sum57);
		request.setAttribute("sum58", sum58);
		
		
		int v_number=0;
		
		if (request.getParameter("v_number")!=null) {
			v_number=Integer.parseInt(request.getParameter("v_number"));
			System.out.println("뭔가 넘어온 v_number=>"+v_number);
		}
		System.out.println("v_number가 null일 때=>"+v_number);
		
		request.setAttribute("v_number", v_number);
		
		
		
		BoardDAO dbPro=new BoardDAO();
		
		BoardDTO article51=dbPro.getMyRate(mem_id, 51);
		System.out.println("article51=>"+article51);
		request.setAttribute("article51", article51);
		
		BoardDTO article52=dbPro.getMyRate(mem_id, 52);
		System.out.println("article52=>"+article52);
		request.setAttribute("article52", article52);
		
		BoardDTO article53=dbPro.getMyRate(mem_id, 53);
		System.out.println("article53=>"+article53);
		request.setAttribute("article53", article53);
		
		BoardDTO article54=dbPro.getMyRate(mem_id, 54);
		System.out.println("article54=>"+article54);
		request.setAttribute("article54", article54);
		
		BoardDTO article55=dbPro.getMyRate(mem_id, 55);
		System.out.println("article55=>"+article55);
		request.setAttribute("article55", article55);
		
		BoardDTO article56=dbPro.getMyRate(mem_id, 56);
		System.out.println("article56=>"+article56);
		request.setAttribute("article56", article56);
		
		BoardDTO article57=dbPro.getMyRate(mem_id, 57);
		System.out.println("article57=>"+article57);
		request.setAttribute("article57", article57);
		
		BoardDTO article58=dbPro.getMyRate(mem_id, 58);
		System.out.println("article58=>"+article58);
		request.setAttribute("article58", article58);
		
		return "/board/ring/R_Rate.jsp";
	}

}