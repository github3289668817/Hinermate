package per.xgt.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import per.xgt.pojo.Person;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-15 14:18:32
 * @Descirption:
 */
public class test {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry
                = new ServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testSessionCache() {
        Person person = (Person) session.get(Person.class, 1);
        System.out.println(person);
    }

    @Test
    public void testSessionFlush() {
        Person person = (Person) session.get(Person.class, 1);
        person.setPersonName("瑞穗");
        session.flush();
    }

    @Test
    public void testSeesionReflesh() throws InterruptedException {
        Person person = (Person) session.get(Person.class, 3);
        System.out.println(person);
        session.refresh(person);
        System.out.println(person);
    }

    @Test
    public void testSave() {

        Person person = new Person();
        person.setPersonName("雪莱");
        person.setPersonAge(28);
        System.out.println(person);
        session.save(person);
        System.out.println(person);

    }

    @Test
    public void testPersist() {
        Person person = new Person();
        person.setPersonName("亚衣");
        person.setPersonAge(28);
        System.out.println(person);
        session.persist(person);
        System.out.println(person);
    }

    @Test
    public void testGet() {
        Person person = (Person) session.get(Person.class, 1);
        System.out.println(person);
    }

    @Test
    public void testLoad() {
        Person person = (Person) session.load(Person.class, 1);
        System.out.println(person);
    }

    @Test
    public void testUpdate() {
        Person person = (Person) session.get(Person.class, 10);
        person.setPersonName("结衣");
    }

    @Test
    public void testSaveOrUpdate(){
        Person person = new Person();
        person.setPersonName("玛丽亚");
        person.setPersonAge(35);
        session.saveOrUpdate(person);
    }

    @Test
    public void testDelete(){
        Person person = new Person();
        person.setPersonId(12);
        session.delete(person);
    }

    @Test
    public void testEvict(){
        Person person1 = (Person) session.get(Person.class, 10);
        Person person2 = (Person) session.get(Person.class, 11);

        person1.setPersonAge(30);
        person2.setPersonAge(30);

        session.evict(person1);
    }

    @Test
    public void testDoWork(){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
                //TODO 可以调用存储过程
            }
        });
    }

    /*@Test
    public void test(){
        //1.创建SessionFactory对象
        SessionFactory sessionFactory = null;
        //1)创建Configuration对象：对应Hibernate的基本配置信息和对象关系映射信息
        Configuration configuration = new Configuration().configure();
        *//**
     * 4.0以前是这样创建的
     * sessionFactory = configuration.buildSessionFactory();
     *//*
        //2)创建一个ServiceRegistry对象：Hibernate4.x新添加的对象-》Hibernate的任何配置和服务都需要再该对象中注册后才能有效
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        //3)创建一个SessionFactory
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //2.创建一个Session对象
        Session session = sessionFactory.openSession();
        //3.开启事务
        Transaction transaction = session.beginTransaction();
        //4.执行保存操作
        Person person = new Person("河豚", 2);
        session.save(person);
        //5.提交事务
        transaction.commit();
        //6.关闭Session，关闭SessionFactory
        session.close();
        sessionFactory.close();
    }*/

}
