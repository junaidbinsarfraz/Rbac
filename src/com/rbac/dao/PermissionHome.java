package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.rbac.model.Permission;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Permission.
 * @see com.rbac.model.Permission
 */
public class PermissionHome {

	private static final Log log = LogFactory.getLog(PermissionHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Permission transientInstance) {
		log.debug("persisting Permission instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Permission instance) {
		log.debug("attaching dirty Permission instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Permission instance) {
		log.debug("attaching clean Permission instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Permission persistentInstance) {
		log.debug("deleting Permission instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Permission merge(Permission detachedInstance) {
		log.debug("merging Permission instance");
		try {
			Permission result = (Permission) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Permission findById(java.lang.Integer id) {
		log.debug("getting Permission instance with id: " + id);
		try {
			Permission instance = (Permission) sessionFactory.getCurrentSession().get("com.rbac.model.Permission", id);
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

	public List<Permission> findByExample(Permission instance) {
		log.debug("finding Permission instance by example");
		try {
			List<Permission> results = (List<Permission>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.Permission")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
