package org.ics.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ics.ejb.TrainingSession;
import org.ics.facade.FacadeLocal;

/**
 * Servlet implementation class CRUD
 */
@WebServlet("/TrainingSessionServlet/*")
public class TrainingSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	FacadeLocal facade;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrainingSessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String pathInfo = request.getPathInfo();
		if
		(pathInfo==null||pathInfo.equals("/")) {
			List<TrainingSession> sessions = facade.findAllTrainingSessions();
			sendAsJson(response, sessions); 			
			return;	
		}
		String[] splits = pathInfo.split("/");
		if(splits.length!=2) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String id = splits[1];
		TrainingSession session = facade.findBySessionId(Integer.parseInt(id));
		if(session==null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "session doesnt exist");
			
		}else {
		sendAsJson(response,session);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo(); 
		if(pathInfo == null || pathInfo.equals("/")){ 
			BufferedReader reader = request.getReader();
			TrainingSession t = parseJsonTrainingSession(reader);
			if(facade.alreadyExists(t.getInstructor(), t.getStartTime())==true) {
				response.sendError(HttpServletResponse.SC_CONFLICT); 
				return;
			}
			try { 
				t = facade.createTrainingSession(t); 
			}catch(Exception e) {
				System.out.println("duplicate key"); 
				} 
			sendAsJson(response, t); 
			}
		}
	

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo(); 
		if(pathInfo == null || pathInfo.equals("/")){ 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
			return; 
			}
		String[] splits = pathInfo.split("/"); 
		if(splits.length != 2) { 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
			return; 
			} 
		String id = splits[1]; 
		BufferedReader reader = request.getReader(); 
		TrainingSession t = parseJsonTrainingSession(reader); 
		if(facade.findByMemberId(Long.parseLong(id))!=null) {
			try { 
				t = facade.updateTrainingSession(t); 
				sendAsJson(response, t); 
			}catch(Exception e) { 
				System.out.println("facade Update Error"); 
				} 
		}else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "member doesnrt exist");
			return;
			
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo(); 
		if(pathInfo == null || pathInfo.equals("/")){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return; 
			} 
		String[] splits = pathInfo.split("/"); 
		if(splits.length != 2) { 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
			return; 
			} 
		String id = splits[1]; 
		TrainingSession t = facade.findBySessionId(Integer.parseInt(id)); 
		if (t == null) { 
			response.sendError(HttpServletResponse.SC_NOT_FOUND ); 
			return;
		}else {
			try {
				facade.deleteTrainingSession(Integer.parseInt(id));   
			}catch(Exception e){
				System.out.println(e.getMessage());
				response.sendError(HttpServletResponse.SC_CONFLICT ); 
				return;
			}
			
		}
	} 
		
	
	private void sendAsJson(HttpServletResponse response, TrainingSession t)throws IOException{
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json"); 
		if (t != null) { 
		
			out.print("{\"instructor\":"); 
			out.print("\"" + t.getInstructor() + "\"");
			out.print(",\"length\":"); 
			out.print("\"" +t.getLength()+"\"");
			out.print(",\"roomNumber\":"); 
			out.print("\"" +t.getRoomNumber()+"\"");
			out.print(",\"sessionId\":"); 
			out.print("\"" +t.getSessionId()+"\"");
			out.print(",\"startTime\":"); 
			out.print("\"" +t.getStartTime()+"\"");
			out.print(",\"type\":"); 
			out.print("\"" +t.getType()+"\"}"); 
			} else { 
				out.print("[ ]"); 
				} 
		out.flush(); } 
	private void sendAsJson(HttpServletResponse response, List<TrainingSession> sessions) throws IOException { 
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json"); 
		if (sessions != null) { 
			JsonArrayBuilder array = Json.createArrayBuilder(); 
			for (TrainingSession t : sessions) { 
				JsonObjectBuilder o = Json.createObjectBuilder(); 
				o.add("sessionId", t.getSessionId()); 
				o.add("instructor", t.getInstructor()); 
				o.add("roomNumber", t.getRoomNumber()); 
				o.add("type", t.getType()); 

				o.add("startTime", t.getStartTime().toString()); 
				array.add(o); 
				} 
			JsonArray jsonArray = array.build(); 
			out.print(jsonArray); 
			} else { 
				out.print("[]"); 
				} 
			out.flush(); } 
	private TrainingSession parseJsonTrainingSession(BufferedReader br) { 
		JsonReader jsonReader = null;    
		JsonObject jsonRoot = null;    
		jsonReader = Json.createReader(br);    
		jsonRoot = jsonReader.readObject();    
		TrainingSession t = new TrainingSession();    
		t.setInstructor(jsonRoot.getString("instructor")); 
		t.setType(jsonRoot.getString("type"));    
		t.setRoomNumber(jsonRoot.getString("roomNumber"));    
		String sDate = jsonRoot.getString("startTime");
		sDate = sDate+":00.000";
		DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date d2 = null;
		try {
			d2 = d.parse(sDate);
		} catch (ParseException e) {
			
			
		}
		t.setStartTime(d2);
		return t; 
		} 
	

}