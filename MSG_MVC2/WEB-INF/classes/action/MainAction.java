package action;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;

public class MainAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		return "/Main.jsp";
	}

}
