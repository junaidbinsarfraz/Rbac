package com.rbac.dao;
// Generated Jan 30, 2017 10:05:45 AM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.cache.internal.NoCachingRegionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

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
			Configuration configuration = new Configuration().configure(
                    "hibernate.cfg.xml");
			configuration.setProperty( Environment.USE_QUERY_CACHE, Boolean.FALSE.toString() );
			configuration.setProperty( Environment.USE_SECOND_LEVEL_CACHE, Boolean.FALSE.toString() );
			configuration.setProperty(Environment.CACHE_REGION_FACTORY,NoCachingRegionFactory.class.getName());
//			configuration.setProperty(Environment.CACHE_PROVIDER_CONFIG,NoCachingRegionFactory.class.getName());	
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            return sessionFactory;

        } catch (Exception e) {

            log.error("Initial SessionFactory creation failed." + e);
            throw new IllegalStateException("Initial Session Factory creation failed.");
        }
	}

	public void persist(RolePermission transientInstance) {
		log.debug("persisting RolePermission instance");
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

	public void attachDirty(RolePermission instance) {
		log.debug("attaching dirty RolePermission instance");
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

	public void attachClean(RolePermission instance) {
		log.debug("attaching clean RolePermission instance");
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

	public void delete(RolePermission persistentInstance) {
		log.debug("deleting RolePermission instance");
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

	public RolePermission merge(RolePermission detachedInstance) {
		log.debug("merging RolePermission instance");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			RolePermission result = (RolePermission) sessionFactory.getCurrentSession().merge(detachedInstance);
			sessionFactory.getCurrentSession().getTransaction().commit();
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
			sessionFactory.getCurrentSession().beginTransaction();
			RolePermission instance = (RolePermission) sessionFactory.getCurrentSession().get("com.rbac.model.RolePermission", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
				sessionFactory.getCurrentSession().getTransaction().commit();
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
			sessionFactory.getCurrentSession().beginTransaction();
			List<RolePermission> results = (List<RolePermission>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.RolePermission")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			sessionFactory.getCurrentSession().getTransaction().commit();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List<RolePermission> getAllRolePermission() {
		log.debug("get all Role Permission");
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			List<RolePermission> results = (List<RolePermission>) sessionFactory.getCurrentSession().createCriteria("com.rbac.model.RolePermission").list();
			log.debug("get all Role Permission successful, result size: " + results.size());
			sessionFactory.getCurrentSession().getTransaction().commit();
			return results;
		} catch (RuntimeException re) {
			log.error("get all Role Permission failed", re);
			throw re;
		}
	}
}
