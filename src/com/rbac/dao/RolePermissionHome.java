package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.rbac.model.RolePermission;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class RolePermission.
 * @see com.rbac.model.RolePermission
 */
public class RolePermissionHome {

	private static final Log log = LogFactory.getLog(RolePermissionHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(RolePermission transientInstance) {
		log.debug("persisting RolePermission instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RolePermission instance) {
		log.debug("attaching dirty RolePermission instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RolePermission instance) {
		log.debug("attaching clean RolePermission instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RolePermission persistentInstance) {
		log.debug("deleting RolePermission instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RolePermission merge(RolePermission detachedInstance) {
		log.debug("merging RolePermission instance");
		try {
			RolePermission result = (RolePermission) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RolePermission findById(java.lang.Integer id) {
		log.debug("getting RolePermission instance with id: " + id);
		try {
			RolePermission instance = (RolePermission) sessionFactory.getCurrentSession().get("com.rbac.model.RolePermission", id);
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

	public List<RolePermission> findByExample(RolePermission instance) {
		log.debug("finding RolePermission instance by example");
		try {
			List<RolePermission> results = (List<RolePermission>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.RolePermission")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
