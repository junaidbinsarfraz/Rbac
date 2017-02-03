package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

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

            SessionFactory sessionFactory = new Configuration().configure(
                    "hibernate.cfg.xml")
                    .buildSessionFactory();

            return sessionFactory;

        } catch (Exception e) {

            log.error("Initial SessionFactory creation failed." + e);
            throw new IllegalStateException("Initial Session Factory creation failed.");
        }
	}

	public void persist(UserRole transientInstance) {
		log.debug("persisting UserRole instance");
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

	public void attachDirty(UserRole instance) {
		log.debug("attaching dirty UserRole instance");
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

	public void attachClean(UserRole instance) {
		log.debug("attaching clean UserRole instance");
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

	public void delete(UserRole persistentInstance) {
		log.debug("deleting UserRole instance");
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

	public UserRole merge(UserRole detachedInstance) {
		log.debug("merging UserRole instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			UserRole result = (UserRole) sessionFactory.getCurrentSession().merge(detachedInstance);
			sessionFactory.getCurrentSession().getTransaction().commit();
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
			sessionFactory.getCurrentSession().beginTransaction();
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
			sessionFactory.getCurrentSession().beginTransaction();
			List<UserRole> results = (List<UserRole>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.UserRole")
					.add(Example.create(instance)).list();
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
			sessionFactory.getCurrentSession().beginTransaction();
			List<UserRole> results = (List<UserRole>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.UserRole").list();
			log.debug("get all user roles successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get all user roles failed", re);
			throw re;
		}
	}
}
