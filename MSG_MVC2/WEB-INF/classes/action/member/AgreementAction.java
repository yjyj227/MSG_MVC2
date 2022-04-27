package action.member;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import action.CommandAction;

public class AgreementAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		return "/member/Agreement.jsp";
	}

}
