package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import board.BoardDAO;
import board.BoardDTO;

// http://localhost:8181/JSP_MY_PROJECT/*.do
@WebServlet ("*.do")
public class Board_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Board_Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");
		
		System.out.println(" do 요청을 처리하는 controller 입니다. ");
		
		
		String uri = request.getRequestURI(); 
		System.out.println(uri);
		
		String path = uri.substring(uri.lastIndexOf("/")); 
		System.out.println(path);
		System.out.println("====================");
		
		
		if (path.equals("/insertBoard.do")) 
		{
			System.out.println("/insertBoard.do 요청");
			
			String title = request.getParameter("title"); 
			String write = request.getParameter("write"); 
			String content = request.getParameter("content"); 
			
			BoardDTO dto = new BoardDTO(); 
			dto.setTitle(title); 
			dto.setWrite(write); 
			dto.setContent(content); 
			
			BoardDAO dao = new BoardDAO(); 
			dao.insertBoard(dto); 			//insert 성공 
			
			
			
			response.sendRedirect("getBoardList.do"); 
			
					
		}
		
		else if (path.equals("/getBoardList.do")) 
		{		// DB의 레코드를 출력 하는 페이지 
			System.out.println("/getBoardList.do 요청");
			
			BoardDTO dto = new BoardDTO(); 
			
			BoardDAO dao = new BoardDAO (); 
			
			List<BoardDTO> boardList = new ArrayList<>(); 
			
			boardList = dao.getBoardList(dto); 
			
			HttpSession session = request.getSession(); 
			
			session.setAttribute("boardList", boardList); 
			
			response.sendRedirect("getBoardList.jsp"); 
			
			
		}
		
		else if (path.equals("/getBoard.do")) 
		{
			System.out.println("/getBoard.do 요청");
			
			
			int id = Integer.parseInt(request.getParameter("id")); 
			
			BoardDTO dto = new BoardDTO(); 
			dto.setId(id); 
			
			BoardDAO dao = new BoardDAO(); 
			
			BoardDTO board = new BoardDTO(); 
			board = dao.getBoard(dto); 
			
			HttpSession session = request.getSession(); 
			
			session.setAttribute("board", board); 
			
			response.sendRedirect("getBoard.jsp"); 
			
			
		}
		
		else if (path.equals("/updateBoard.do")) 
		{
			System.out.println("/updateBoard.do 요청");

			String title = request.getParameter("title"); 
			String write = request.getParameter("write"); 
			String content = request.getParameter("content"); 
			int id = Integer.parseInt(request.getParameter("id")); 
			
			
			BoardDTO dto = new BoardDTO(); 
			dto.setTitle(title); 
			dto.setWrite(write); 
			dto.setContent(content); 
			dto.setId(id); 
			
			BoardDAO dao = new BoardDAO (); 
			dao.updateBoard(dto); 
			
			response.sendRedirect("getBoardList.do");
					
		}
		
		else if (path.equals("/deleteBoard.do")) 
		{
			System.out.println("/deleteBoard.do 요청");
			
			String s_id = request.getParameter("id"); 
			int id = Integer.parseInt(s_id); 
			
			BoardDTO dto = new BoardDTO(); 
			dto.setId(id); 
			
			BoardDAO dao = new BoardDAO(); 
			
			dao.deleteBoard(dto); 
			
			response.sendRedirect("getBoardList.do"); 

		}
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}