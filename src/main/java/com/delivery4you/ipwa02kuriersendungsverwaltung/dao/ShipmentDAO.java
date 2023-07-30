package com.delivery4you.ipwa02kuriersendungsverwaltung.dao;

import com.delivery4you.ipwa02kuriersendungsverwaltung.model.Shipment;
import com.delivery4you.ipwa02kuriersendungsverwaltung.model.Status;
import jakarta.persistence.*;

import java.util.List;

public class ShipmentDAO
{
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("courier");

	public List<Shipment> findAll() {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("select s from Shipment s");
		List<Shipment> allShipments = query.getResultList();
		em.close();
		return allShipments;
	}

	public List<Shipment> findByStatus(Status status) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT s FROM Shipment s WHERE s.status = :status");
		query.setParameter("status", status);
		List<Shipment> shipments = query.getResultList();
		em.close();
		return shipments;
	}

	public Shipment save(Shipment shipment) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Shipment result = em.merge(shipment);
		t.commit();
		return result;
	}
}
