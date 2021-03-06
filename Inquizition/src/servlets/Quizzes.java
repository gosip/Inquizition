package servlets;

import helpers.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Quizzes
 */
@WebServlet("/Quizzes")
public class Quizzes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Quizzes() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<QuizEntry> list = QuizManager.getAllQuizEntries();
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Quizzes</title>");
		out.println("<link rel = \"stylesheet\" type = \"text/css\" href = \"MessagesStyle.css\">");
		out.println("<body>");
		out.println("<div class=\"header\">");
		out.println("<h1>QUIZZES</h1>");
		out.println("</div>");
		out.println("<div class=\"compose\"> <a href = \"QuizCreation.html\">Create Your Own Quiz!</a> </div>");
		out.println("<div class=\"homepage\"> <a href = \"HomePage\">Return to HomePage</a> </div>");
		out.println("<table>");
        out.println("<tr>");
        out.println("<th id=\"name\">Name</th>");
        out.println("<th id=\"creator\">Creator</th>");
        out.println("<th id=\"times\">Times Taken</th>");
        out.println("<th id=\"score\">High Score</th>");
        out.println("<th id=\"champion\">Champion</th>");
        out.println("<th id=\"action\">Action</th>");
        out.println("</tr>");
        for(QuizEntry q : list){
        	out.println("<tr class=\"read\">");				
        	out.println("<td>" + q.getName() + " </td>");
        	out.println("<td> <a href=\"UserPage?other_id=" + q.getCreatorID() + "\">" + q.getCreator() + " </td>");
        	out.println("<td>" + q.getTimesTaken() + " </td>");
        	out.println("<td>" + q.getBestScore() + " </td>");
        	out.println("<td> <a href=\"UserPage?other_id=" + q.getChampionID() + "\">" + q.getChampion() + " </td>");
        	//link:
        	
        	out.println("<td class=\"centered\">");
        	out.println("<form method=\"get\" action='QuizDescription'>");
        	out.println("<button type=\"submit\">View</button>");
        	out.println("<input type=hidden name='id' value= " + q.getID() +  ">");
        	out.println("</form>");
            out.println("</td>");
            
			out.println("</tr>");
			
		}
        out.println("</table");
		out.println("</body>");
		out.println("</html>");
	}

}
