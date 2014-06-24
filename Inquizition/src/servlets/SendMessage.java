package servlets;


import helpers.User;
import helpers.messaging.Message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String to;
	private String subject;
	private String text;
	private String res;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		to = (String) request.getParameter("to");
		int type = Integer.parseInt(request.getParameter("type"));
		User user = (User)request.getSession().getAttribute("user");
		int from = user.getID();
		subject = "";
		text = "";
		res = "";
		switch(type){
			case 1:
				BuildMessage(request);
				break;
			case 2:
				BuildRequest(user, request);
				break;
			case 3:
				BuildChallenge(user, request);
				break;
		}
		
		String result = res + " Failed";
		if(Message.sendMessage(from, to, subject, text))
			result = res + " Sent";
		
		//depending on sendMessage's value, prints one of two statements
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Result</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>" + result + "</h2>");
		out.println("</body>");
		out.println("</html>");
	}
	
	private void BuildMessage(HttpServletRequest request){
		subject = (String) request.getParameter("subject");
		text = (String) request.getParameter("text");
		res = "Message";		
	}
	
	private void BuildRequest(User user, HttpServletRequest request){
		subject = "Friend request from " + user.getUsername();
		text = "linki aq iqneba"; 	//TODO: put a link to friend request here
		res = "Friend Request";
	}
	
	private void BuildChallenge(User user, HttpServletRequest request){
		subject = "Challenge from " + user.getUsername();	//TODO: add the quiz name
		//TODO: this.
	}

}