/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.dao;

import cz.muni.fi.pa165.pokemon.league.participation.manager.common.PersistenceApplicationContext;
import cz.muni.fi.pa165.pokemon.league.participation.manager.entities.Badge;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Unit test to badge data access object.
 *
 * @author Tamás Rózsa 445653
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners({TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@RunWith(SpringRunner.class)
public class BadgeDAOTest {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private BadgeDAO badgeDao;
    
    @Mock
    private Badge todaysBadge;
    private Badge finalBadge;
    
    public BadgeDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        todaysBadge.setDate(LocalDate.now());
        
        em.getTransaction().begin();
        em.persist(todaysBadge.getTrainer());
        em.persist(finalBadge.getTrainer());
        em.persist(todaysBadge.getGym());
        em.persist(finalBadge.getGym());
        em.persist(todaysBadge);
        em.persist(finalBadge);
        em.getTransaction().commit();
    }
    
    @After
    public void tearDown() {
    }

 
    @Test
    public void createNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.createBadge(null));
    }
    
    @Test
    public void updateNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.updateBadge(null));
    }

    @Test
    public void deleteNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.deleteBadge(null));
    }
     
    @Test
    public void findNullBadge() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> badgeDao.findBadgeById(null));
    }
    
    @Test
    public void createBadge() {
        Badge badge = mock(Badge.class);
        badgeDao.createBadge(badge);
        assertThat(em.find(Badge.class, badge)).isEqualToComparingFieldByField(badge);
    }
    
    @Test
    public void testMultipleCreation() {
        assertThat(todaysBadge.getId()).isNotNull();
        assertThat(em.find(Badge.class, todaysBadge)).isEqualToComparingFieldByField(todaysBadge);
        try {
            badgeDao.createBadge(finalBadge);
            Assert.fail("Created the same gym twice.");
        }
        catch(Exception ex){
        }
    }
    
    @Test
    public void findByIdExistingBadge() {
        assertThat(badgeDao.findBadgeById(todaysBadge.getId())).isEqualToComparingFieldByField(todaysBadge);
    }
    
    @Test
    public void findByIDNotExistingBadge() {
        assertThat(badgeDao.findBadgeById(Long.MIN_VALUE)).isNull();
    }
    
    @Test
    public void updateBadge() {
        Badge badge = em.find(Badge.class, todaysBadge);
        assertThat(badge).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    
        badge.setDate(LocalDate.MIN);
        badgeDao.updateBadge(badge);
        assertThat(badge).isNotSameAs(todaysBadge);
        
        badge = em.find(Badge.class, todaysBadge);
        assertThat(badge).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    }
    
    @Test
    public void deleteBadge() {
        assertThat(em.find(Badge.class, todaysBadge)).isEqualToComparingFieldByField(todaysBadge);
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
        
        badgeDao.deleteBadge(todaysBadge);
        
        assertThat(em.find(Badge.class, todaysBadge)).isNull();
        assertThat(em.find(Badge.class, finalBadge)).isEqualToComparingFieldByField(finalBadge);
    }
    
    @Test
    public void getAllBadges() {
        assertThat(badgeDao.getAllBadges()).usingFieldByFieldElementComparator().containsOnly(todaysBadge, finalBadge);
    }
}
