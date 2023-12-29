package controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.MembersDAO;
import member.MembersDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import board.BoardDAO;
import board.BoardDTO;

// http://localhost:8181/JSP_MY/PROJECT/*.us
@WebServlet ("*.us")
public class MembersController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
   public MembersController() 
   {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.getWriter().append("Served at: ").append(request.getContextPath());
	
		request.setCharacterEncoding("UTF-8");
	
		String uri = request.getRequestURI(); 
		
		String path = uri.substring(uri.lastIndexOf("/")); 
		
		
		if (path.equals("/login.us")) 
		{
			System.out.println("login.us 요청 처리");
			
			String id = request.getParameter("id"); 
			String password = request.getParameter("password"); 
			
			MembersDTO dto = new MembersDTO(); 
			dto.setId(id); 
			dto.setPassword(password); 
			
			MembersDAO dao = new MembersDAO(); 
			
			MembersDTO user = new MembersDTO(); 
			
			user = dao.login(dto); 
			
			
			if (user == null) 
			{	//인증 실패
				System.out.println("인증 실패 했습니다.");
				response.sendRedirect("LoginForm.jsp"); 
				
			}
			
			
			else 
			{ // 인증 성공 
				System.out.println("인증 성공 했습니다. ");
				HttpSession session = request.getSession(); 
				session.setAttribute("id", user.getId()); 
				session.setAttribute("role", user.getRole()); 
				
				response.sendRedirect("LoginForm.jsp"); 
			}
		}
		
		
		else if (path.equals("/logout.us")) 
		{
			System.out.println("/logout.us 요청 처리 ");
			
			HttpSession session = request.getSession(); 
			
			session.invalidate(); 
			
			response.sendRedirect("http://localhost:8181/JSP_MY_PROJ"); 
		}
		
		
		else if (path.equals("/insertMembers.us")) 
		{
			System.out.println("insertMembers.us 요청 처리");
			
			String id = request.getParameter("id"); 
			String password = request.getParameter("password"); 
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String addr = request.getParameter("addr");
			String role = request.getParameter("role");
			
			MembersDTO dto = new MembersDTO(); 
			dto.setId(id); 
			dto.setPassword(password); 
			dto.setPhone(phone);
			dto.setEmail(email);
			dto.setAddr(addr);
			dto.setRole(role);
			dto.setId(id);
			
			MembersDAO dao = new MembersDAO(); 
			dao.insertMembers(dto); 			//insert 성공 
			
			
			
			response.sendRedirect("getMembersList.us"); 
			
		}
		
		else if (path.equals("/getMembersList.us")) 
		{		// DB의 레코드를 출력 하는 페이지 
			System.out.println("/getMembersList.us 요청");
			
			MembersDTO dto = new MembersDTO(); 
			
			MembersDAO dao = new MembersDAO (); 
			
			List<MembersDTO> membersList = new ArrayList<>(); 
			
			membersList = dao.getMembersList(dto); 
			
			HttpSession session = request.getSession(); 
			
			session.setAttribute("membersList", membersList); 
			
			response.sendRedirect("getMembersList.jsp"); 
			
			
		}
		
		else if (path.equals("/getMembers.us")) 
		{
			System.out.println("/getMembers.us 요청");
			
			
			String id = request.getParameter("id"); 
			
			MembersDTO dto = new MembersDTO(); 
			dto.setId(id); 
			
			MembersDAO dao = new MembersDAO(); 
			
			MembersDTO member = new MembersDTO(); 
			member = dao.getMembers(dto); 
			
			HttpSession session = request.getSession(); 
			
			session.setAttribute("member", member); 
			
			response.sendRedirect("getMembers.jsp"); 
			
			
		}
		
		else if (path.equals("/updateMembers.us")) 
		{
			System.out.println("/updateMembers.us 요청");

			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String addr = request.getParameter("addr");
			String role = request.getParameter("role");
			String id = request.getParameter("id"); 
			
			
			MembersDTO dto = new MembersDTO(); 
			dto.setPhone(phone);
			dto.setEmail(email);
			dto.setAddr(addr);
			dto.setRole(role);
			dto.setId(id); 
			
			MembersDAO dao = new MembersDAO (); 
			dao.updateMembers(dto); 
			
			response.sendRedirect("getMembersList.us");
					
		}
		
		else if (path.equals("/deleteMembers.us")) 
		{
			System.out.println("/deleteMembers.us 요청");
			
			String id = request.getParameter("id"); 
			
			MembersDTO dto = new MembersDTO(); 
			dto.setId(id); 
			
			MembersDAO dao = new MembersDAO(); 
			
			dao.deleteMembers(dto); 
			
			response.sendRedirect("getMembersList.us"); 

		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		doGet(request, response);
	}

}