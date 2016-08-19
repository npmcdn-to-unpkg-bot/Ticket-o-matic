package it.siw.persistence;

import com.zaxxer.hikari.HikariDataSource;

import it.siw.persistence.dao.EventCategoryDAO;
import it.siw.persistence.dao.EventDAO;
import it.siw.persistence.dao.OrderDAO;
import it.siw.persistence.dao.UserDAO;
import it.siw.persistence.dao.WishlistDAO;
import it.siw.persistence.dao.implementation.EventCategoryDaoJDBC;
import it.siw.persistence.dao.implementation.EventDaoJDBC;
import it.siw.persistence.dao.implementation.OrderDAOJDBC;
import it.siw.persistence.dao.implementation.UserDaoJDBC;
import it.siw.persistence.dao.implementation.WishlistDAOJDBC;

/**
 * Concrete Postgres DAO factory implementation
 */
public class PostgresDAOFactory extends DAOFactory {

	public static final String DRIVER = "org.postgresql.Driver";

	private static PostgresDAOFactory postgres;

	private static String DBURL = "jdbc:postgresql://192.168.1.103/tom";
	private static String USERNAME = "postgres";
	private static String PASSWORD = "postgres";

	private static HikariDataSource datasource;

	public static PostgresDAOFactory getInstance() {
		if (postgres == null) {
			postgres = new PostgresDAOFactory();
		}
		return postgres;
	}

	private PostgresDAOFactory() {
		try {
			Class.forName(DRIVER).newInstance();
			datasource = new HikariDataSource();
			datasource.setJdbcUrl(DBURL);
			datasource.setUsername(USERNAME);
			datasource.setPassword(PASSWORD);
			datasource.setMaxLifetime(100L);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.println("PostgresDAOFactory.class: failed to load JDBC driver\n" + e);
			e.printStackTrace();
		}
	}

	@Override
	public EventDAO getEventDAO() {
		return new EventDaoJDBC(datasource);
	}

	@Override
	public UserDAO getUserDAO() {
		return new UserDaoJDBC(datasource);
	}

	@Override
	public EventCategoryDAO getEventCategoryDAO() {
		return new EventCategoryDaoJDBC(datasource);
	}

	@Override
	public WishlistDAO getWishlistDAO() {
		return new WishlistDAOJDBC(datasource);
	}

	@Override
	public OrderDAO getOrderDAO() {
		return new OrderDAOJDBC(datasource);
	}
}
