package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.rbac.model.UserRole;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class UserRole.
 * @see com.rbac.model.UserRole
 */
public class UserRoleHome {

	private static final Log log = LogFactory.getLog(UserRoleHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(UserRole transientInstance) {
		log.debug("persisting UserRole instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserRole instance) {
		log.debug("attaching dirty UserRole instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserRole instance) {
		log.debug("attaching clean UserRole instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserRole persistentInstance) {
		log.debug("deleting UserRole instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserRole merge(UserRole detachedInstance) {
		log.debug("merging UserRole instance");
		try {
			UserRole result = (UserRole) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserRole findById(java.lang.Integer id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = (UserRole) sessionFactory.getCurrentSession().get("com.rbac.model.UserRole", id);
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

	public List<UserRole> findByExample(UserRole instance) {
		log.debug("finding UserRole instance by example");
		try {
			List<UserRole> results = (List<UserRole>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.UserRole")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<UserRole> getAllUserRoles() {
		log.debug("get all user roles");
		try {
			List<UserRole> results = (List<UserRole>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.UserRole").list();
			log.debug("get all user roles successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get all user roles failed", re);
			throw re;
		}
	}
}
