package com.delivery4you.ipwa02kuriersendungsverwaltung.dao;

import com.delivery4you.ipwa02kuriersendungsverwaltung.model.User;
import jakarta.persistence.*;

public class UserDAO
{
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("courier");

	/**
	 * Searches for a User with the given name and returns it if found. If not found null will be returned.
	 * @param name
	 * @return User or null
	 */
	public User findByName(String name) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT u FROM User u WHERE u.name = :name");
		query.setParameter("name", name);
		query.setMaxResults(1);
		User selectedUser = null;
		try {
			selectedUser = (User) query.getSingleResult();
		} catch (NoResultException e) {
			// Username not found in the database
		}
		em.close();
		return selectedUser;
	}

	public void merge(User user)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(user);
		t.commit();
	}
}
