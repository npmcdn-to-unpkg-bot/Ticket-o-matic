package it.siw.persistence.dao;

import it.siw.model.User;

public interface UserDAO {

    public boolean create(User modelObject);

    public User findWishlist(Integer id);

    public User findById(Integer id);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findOrder(Integer id);

    public User findReview(Integer id);

    public User findSells(Integer id);

    public User findGift(Integer id);

    public boolean update(User modelObject);

    public void delete(User modelObject);
}
