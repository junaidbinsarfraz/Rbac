package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.rbac.model.AcessType;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AcessType.
 * @see com.rbac.model.AcessType
 */
public class AcessTypeHome {

	private static final Log log = LogFactory.getLog(AcessTypeHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(AcessType transientInstance) {
		log.debug("persisting AcessType instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(AcessType instance) {
		log.debug("attaching dirty AcessType instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcessType instance) {
		log.debug("attaching clean AcessType instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AcessType persistentInstance) {
		log.debug("deleting AcessType instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcessType merge(AcessType detachedInstance) {
		log.debug("merging AcessType instance");
		try {
			AcessType result = (AcessType) sessionFactory.getCurrentSession().merge(detachedInstance);
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
			List<AcessType> results = (List<AcessType>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.AcessType").list();
			log.debug("get all AcessTypes successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get all AcessTypes failed", re);
			throw re;
		}
	}
}
