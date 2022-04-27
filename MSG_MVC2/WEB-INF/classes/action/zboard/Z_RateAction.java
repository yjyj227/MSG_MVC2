package action.zboard;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import beans.BoardDAO;
import beans.BoardDTO;

import action.CommandAction;

public class Z_RateAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session=request.getSession();
		String mem_id=(String)session.getAttribute("idKey"); //Object이기 때문에 String으로 형변환 해줘야함
		String mem_passwd=(String)session.getAttribute("pwKey");
		System.out.println("H_RateAction의 mem_id=>"+mem_id);
		
		BoardDAO avgPro=new BoardDAO();
		double avg21=avgPro.avgStar(21);
		double avg22=avgPro.avgStar(22);
		double avg23=avgPro.avgStar(23);
		double avg24=avgPro.avgStar(24);
		double avg25=avgPro.avgStar(25);
		double avg26=avgPro.avgStar(26);
		double avg27=avgPro.avgStar(27);
		double avg28=avgPro.avgStar(28);
		
		request.setAttribute("avg21", avg21);
		request.setAttribute("avg22", avg22);
		request.setAttribute("avg23", avg23);
		request.setAttribute("avg24", avg24);
		request.setAttribute("avg25", avg25);
		request.setAttribute("avg26", avg26);
		request.setAttribute("avg27", avg27);
		request.setAttribute("avg28", avg28);
		
		BoardDAO sumPro=new BoardDAO();
		int sum21=sumPro.countLikes(21);
		int sum22=sumPro.countLikes(22);
		int sum23=sumPro.countLikes(23);
		int sum24=sumPro.countLikes(24);
		int sum25=sumPro.countLikes(25);
		int sum26=sumPro.countLikes(26);
		int sum27=sumPro.countLikes(27);
		int sum28=sumPro.countLikes(28);
		
		request.setAttribute("sum21", sum21);
		request.setAttribute("sum22", sum22);
		request.setAttribute("sum23", sum23);
		request.setAttribute("sum24", sum24);
		request.setAttribute("sum25", sum25);
		request.setAttribute("sum26", sum26);
		request.setAttribute("sum27", sum27);
		request.setAttribute("sum28", sum28);
		
		
		int v_number=0;
		
		if (request.getParameter("v_number")!=null) {
			v_number=Integer.parseInt(request.getParameter("v_number"));
			System.out.println("뭔가 넘어온 v_number=>"+v_number);
		}
		System.out.println("v_number가 null일 때=>"+v_number);
		
		request.setAttribute("v_number", v_number);
		
		
		
		BoardDAO dbPro=new BoardDAO();
		
		BoardDTO article21=dbPro.getMyRate(mem_id, 21);
		System.out.println("article21=>"+article21);
		request.setAttribute("article21", article21);
		
		BoardDTO article22=dbPro.getMyRate(mem_id, 22);
		System.out.println("article22=>"+article22);
		request.setAttribute("article22", article22);
		
		BoardDTO article23=dbPro.getMyRate(mem_id, 23);
		System.out.println("article23=>"+article23);
		request.setAttribute("article23", article23);
		
		BoardDTO article24=dbPro.getMyRate(mem_id, 24);
		System.out.println("article24=>"+article24);
		request.setAttribute("article24", article24);
		
		BoardDTO article25=dbPro.getMyRate(mem_id, 25);
		System.out.println("article25=>"+article25);
		request.setAttribute("article25", article25);
		
		BoardDTO article26=dbPro.getMyRate(mem_id, 26);
		System.out.println("article26=>"+article26);
		request.setAttribute("article26", article26);
		
		BoardDTO article27=dbPro.getMyRate(mem_id, 27);
		System.out.println("article27=>"+article27);
		request.setAttribute("article27", article27);
		
		BoardDTO article28=dbPro.getMyRate(mem_id, 28);
		System.out.println("article28=>"+article28);
		request.setAttribute("article28", article28);
		
		return "/board/zzang/Z_Rate.jsp";
	}

}