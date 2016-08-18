package it.siw.service;

import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.siw.model.Event;
import it.siw.model.Organizer;
import it.siw.persistence.DAOFactory;
import it.siw.persistence.dao.EventDAO;

public class SearchService {
	
	
	public SearchService()
	{}
	/***/
	public void getEventById(String data, JsonObject result)
	{
		if(data==null)
			return;
		
		DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		EventDAO eventDAO= factory.getEventDAO();
		Gson gson= new Gson();
		Event event=gson.fromJson(data, Event.class);
		
		if(eventDAO.findById(event.getId())!=null)
		{
			result.addProperty("result", "SUCCESS");
		}
		else
		{
			result.addProperty("result", "FAIL");
			result.addProperty("reason", "This id event not exist ");
		}
	}
	/***/
	public void  getEventbyfindGuest(String data, JsonObject result)
	{
		if(data==null)
			return ;
		DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		EventDAO eventDAO= factory.getEventDAO();
		Gson gson= new Gson();
		Event event=gson.fromJson(data, Event.class);
		for(int i=0;i<event.getGuests().size();i++)
		{
			if(eventDAO.findByGuest(event.getGuests().get(i).getName())!=null)
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "This id event not exist ");
			}
		}
	}
	public void  geEventfindTicket(String data, JsonObject result)
	{
		if(data == null)
			return ;
		DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		EventDAO eventDAO= factory.getEventDAO();
		Gson gson= new Gson();
		Event event=gson.fromJson(data, Event.class);
		for(int i=0;i<event.getTicket().size();i++)
		{
			if(eventDAO.findTicket(event.getTicket().get(i).getId())!=null)
		
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "" + event.getTicket().get(i).getId()+ "This id ticket not exist ");
			}
		}
	}
	public void getEventfindLastId(String data,JsonObject result)
	{
		if(data == null)
			return;
		
		DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		EventDAO eventDAO= factory.getEventDAO();
		Gson gson= new Gson();
		Event event=gson.fromJson(data, Event.class);
		if(eventDAO.findLastId(event.getOrganizer())!=null)
		{
			result.addProperty("result", "SUCCESS");
		}
		else
		{
			result.addProperty("result", "FAIL");
			result.addProperty("reason", "" + event.getOrganizer() + "This  user not exist ");
		}
	}
	public void getEventFindByName(String data,JsonObject result)
	{
		if(data == null)
			return;
		
		DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
		EventDAO eventDAO= factory.getEventDAO();
		Gson gson= new Gson();
		Event event=gson.fromJson(data, Event.class);
		if(eventDAO.findByName(event.getName())!=null)
		{
			result.addProperty("result", "SUCCESS");
		}
		else
		{
			result.addProperty("result", "FAIL");
			result.addProperty("reason", "" + event.getName() +"This  event not exist ");
		}
	}
//	  public void getfindByName(String data,JsonObject result)
//		{
//			if(data == null)
//				return;
//			
//			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
//			EventDAO eventDAO= factory.getEventDAO();
//			Gson gson= new Gson();
//			Event event=gson.fromJson(data, Event.class);
//			if(eventDAO.findByName(event.getName(), event.get, limit))
//			{
//				result.addProperty("result", "SUCCESS");
//			}
//			else
//			{
//				result.addProperty("result", "FAIL");
//				result.addProperty("reason", "This id event not exist ");
//			}
//		  
//	  }

	    public void getEventFindByDate(String data,JsonObject result)
		{
			if(data == null)
				return;
			
			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
			EventDAO eventDAO= factory.getEventDAO();
			Gson gson= new Gson();
			Event event=gson.fromJson(data, Event.class);
			if(eventDAO.findByDate(event.getDate())!=null)
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "This data event not exist ");
			}
	    }

//	    public Map<Integer, Event> findByDate(String data,JsonObject result)
//		{
//			if(data == null)
//				return;
//			
//			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
//			EventDAO eventDAO= factory.getEventDAO();
//			Gson gson= new Gson();
//			Event event=gson.fromJson(data, Event.class);
//			if(eventDAO.findByName(event.getName())!=null)
//			{
//				result.addProperty("result", "SUCCESS");
//			}
//			else
//			{
//				result.addProperty("result", "FAIL");
//				result.addProperty("reason", "This name's event not exist ");
//			}
//	    }

	    public void getEventFindByPrice(String data,JsonObject result)
		{
			if(data == null)
				return;
			
			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
			EventDAO eventDAO= factory.getEventDAO();
			Gson gson= new Gson();
			Event event=gson.fromJson(data, Event.class);
			if(eventDAO.findByPrice(event.getTicket().get(1).getPrice(),event.getTicket().get(1).getPrice())!=null)
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "This price event not exist ");
			}
	    }
//
//	    public Map<Integer, Event> findByPrice(Long min, Long max, Integer offset, Integer limit)
//	    {
//	    	EventDAO eventDAO= DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
//			return eventDAO.findByPrice(min, max,offset,limit);
//	    }

	    public void getEventFindByLocation(String data,JsonObject result)
		{
			if(data == null)
				return;
			
			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
			EventDAO eventDAO= factory.getEventDAO();
			Gson gson= new Gson();
			Event event=gson.fromJson(data, Event.class);
			if(eventDAO.findByLocation(event.getLocation())!=null)
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "" +event.getLocation() + "This location event not exist ");
			}
	    }

	//    public Map<Integer, Event> findByLocation(String location, Integer offset, Integer limit)
//	    {
//	    	EventDAO eventDAO= DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
//			return eventDAO.findByLocation(location,offset,limit);
//	    }

	    public void getEventFindByGuest(String data,JsonObject result)
		{
			if(data == null)
				return;
			
			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
			EventDAO eventDAO= factory.getEventDAO();
			Gson gson= new Gson();
			Event event=gson.fromJson(data, Event.class);
			for(int i=0;i<event.getGuests().size();i++)
			{
				if(eventDAO.findByGuest(event.getGuests().get(1).getName())!=null)
				{
					result.addProperty("result", "SUCCESS");
				}
				else
				{
					result.addProperty("result", "FAIL");
					result.addProperty("reason", "" +event.getGuests().get(1).getName() + "This guest  not exist ");
				}
			}
	    }

//	    public Map<Integer, Event> findByGuest(String guest, Integer offset, Integer limit)
//	    {
//	    	EventDAO eventDAO= DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
//			return eventDAO.findByGuest(guest,offset,limit);
//	    	
//	    }

	    public void getEventFindByCategory(String data,JsonObject result)
		{
			if(data == null)
				return;
			
			DAOFactory factory=DAOFactory.getDaoFactory(DAOFactory.POSTGRES);
			EventDAO eventDAO= factory.getEventDAO();
			Gson gson= new Gson();
			Event event=gson.fromJson(data, Event.class);
			
			if(eventDAO.findByCategory(event.getCategory().getName())!=null)
			{
				result.addProperty("result", "SUCCESS");
			}
			else
			{
				result.addProperty("result", "FAIL");
				result.addProperty("reason", "" +event.getCategory().getName() + "This category  not exist ");
			}
			
	    }

//	    public Map<Integer, Event> findByCategory(String category, Integer offset, Integer limit)
//	    {
//	    	EventDAO eventDAO= DAOFactory.getDaoFactory(DAOFactory.POSTGRES).getEventDAO();
//			return eventDAO.findByCategory(category, offset, limit);
//	    }
}
