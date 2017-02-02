package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rbac.model.AcessType;

/**
 * Home object for domain model class AcessType.
 * @see com.rbac.model.AcessType
 */
public class AcessTypeHome {

	private static final Log log = LogFactory.getLog(AcessTypeHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {

            SessionFactory sessionFactory = new Configuration().configure(
                    "hibernate.cfg.xml")
                    .buildSessionFactory();

            return sessionFactory;

        } catch (Exception e) {

            log.error("Initial SessionFactory creation failed." + e);
            throw new IllegalStateException("Initial Session Factory creation failed.");
        }
	}

	public void persist(AcessType transientInstance) {
		log.debug("persisting AcessType instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().persist(transientInstance);
			sessionFactory.getCurrentSession().getTransaction().commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(AcessType instance) {
		log.debug("attaching dirty AcessType instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			sessionFactory.getCurrentSession().getTransaction().commit();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcessType instance) {
		log.debug("attaching clean AcessType instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			sessionFactory.getCurrentSession().getTransaction().commit();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AcessType persistentInstance) {
		log.debug("deleting AcessType instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().delete(persistentInstance);
			sessionFactory.getCurrentSession().getTransaction().commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcessType merge(AcessType detachedInstance) {
		log.debug("merging AcessType instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			AcessType result = (AcessType) sessionFactory.getCurrentSession().merge(detachedInstance);
			sessionFactory.getCurrentSession().getTransaction().commit();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public AcessType findById(java.lang.Integer id) {
		log.debug("getting AcessType instance with id: " + id);
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			AcessType instance = (AcessType) sessionFactory.getCurrentSession().get("com.rbac.model.AcessType", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<AcessType> findByExample(AcessType instance) {
		log.debug("finding AcessType instance by example");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			List<AcessType> results = (List<AcessType>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.AcessType")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	


	public List<AcessType> getAllAcessTypes() {
		log.debug("get all AcessTypes");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			List<AcessType> results = (List<AcessType>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.AcessType").list();
			log.debug("get all AcessTypes successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get all AcessTypes failed", re);
			throw re;
		}
	}
}
