package it.siw.persistence;

import it.siw.persistence.dao.EventCategoryDAO;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.GuestDAO;
import it.siw.persistence.dao.OrderDAO;
import it.siw.persistence.dao.SellDAO;
import it.siw.persistence.dao.TicketCategoryDAO;
import it.siw.persistence.dao.TicketDAO;
import it.siw.persistence.dao.UserDAO;
import it.siw.persistence.dao.WishlistDAO;

public abstract class DAOFactory {

    public static final int POSTGRES = 1;

    public static DAOFactory getDaoFactory(int wichFactory) {
	switch (wichFactory) {
	case 1:
	    return PostgresDAOFactory.getInstance();
	default:
	    return null;
	}
    }

    public abstract void destroyDataSource();

    public abstract EventDAO getEventDAO();

    public abstract TicketDAO getTicketDAO();

    public abstract TicketCategoryDAO getTicketCategoryDAO();

    public abstract EventCategoryDAO getEventCategoryDAO();

    public abstract UserDAO getUserDAO();

    public abstract WishlistDAO getWishlistDAO();

    public abstract OrderDAO getOrderDAO();

    public abstract SellDAO getSellDAO();

    public abstract GuestDAO getGuestDAO();

}
