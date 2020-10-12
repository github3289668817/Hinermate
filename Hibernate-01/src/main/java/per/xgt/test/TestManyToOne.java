package per.xgt.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import per.xgt.pojo.Customer;
import per.xgt.pojo.Order;

/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-21 15:16:36
 * @Descirption:
 */
public class TestManyToOne {

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
    public void testMany2One() {
        Customer customer = new Customer();
        customer.setCustomerName("AA");

        Order order1 = new Order();
        order1.setOrderName("Order1");

        Order order2 = new Order();
        order2.setOrderName("Order2");

        //设定关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        //执行save操作
        session.save(customer);
        session.save(order1);
        session.save(order2);

    }

    @Test
    public void testMany2OneGet() {
        //若查询多的一端的对象，则默认情况下只查询了多的一端的对象而没有查询关联一的一方对象
        Order order = (Order) session.get(Order.class, 1);
        //只有在使用的时候才会发送sql查询一方的对象
        System.out.println(order.getCustomer());
        Customer customer = order.getCustomer();
        System.out.println(customer);
        //在查询一的一方对象时，由多的一方到一的一方的时候，可能发生懒加载异常
        //获取多的一方对象时，其关联的一的一方的对象是代理对象
    }

    @Test
    public void testUpdateOne2Many(){
        Customer customer = (Customer)session.get(Customer.class, 1);
        customer.getOrders().iterator().next().setOrderName("修改了");
    }



}
