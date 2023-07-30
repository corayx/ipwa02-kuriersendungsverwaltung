package com.delivery4you.ipwa02kuriersendungsverwaltung.dao;

import com.delivery4you.ipwa02kuriersendungsverwaltung.model.Stamp;
import jakarta.persistence.*;

public class StampDAO
{
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("courier");

    public Stamp save(Stamp stamp)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        Stamp result = em.merge(stamp);
        t.commit();
        return result;
    }
}
