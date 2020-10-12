package per.xgt.pojo;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-22 13:48:45
 * @Descirption:
 */
public class MyTest {

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
    public void test(){
    }

    @Test
    public void testHQL1(){

        //1.创建Query对象
        String hql = "FROM Employee e where e.salary > ? and e.email like ?";
        Query query = session.createQuery(hql);

        //2.绑定参数
        query.setFloat(0, 800).setString(1, "%V%");

        //3.执行查询
        List list = query.list();
        System.out.println(list.getClass());
        System.out.println(list);

    }

    @Test
    public void testHQL2(){

        //1.创建Query对象
        String hql = "FROM Employee e where e.salary > :sal and e.email like :email";
        Query query = session.createQuery(hql);

        //2.绑定参数
        query.setFloat("sal", 800).setString("email", "%V%");

        //3.执行查询
        List list = query.list();
        System.out.println(list.getClass());
        System.out.println(list);

    }

    @Test
    public void testPageQuery(){
        String hql = "FROM Employee";
        Query query = session.createQuery(hql);
        int pageNo = 3;
        int pageSize = 5;
        List list = query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
        System.out.println(list);
    }

    @Test
    public void testNameQuery(){
        Query query = session.getNamedQuery("salaryEmps");
        List list = query.setFloat("minSal", 6).setFloat("maxSal", 10).list();
        System.out.println(list);
    }

    @Test
    public void testFieldQuery1(){

        String hql = "select e.email,e.salary from Employee e where e.dept = :dept";
        Query query = session.createQuery(hql);
        Department dept = new Department();
        dept.setId(100);
        List<Object[]> list = query.setEntity("dept", dept).list();
        System.out.println(list);

    }

    @Test
    public void testFieldQuery2(){

        String hql = "select new Employee(e.email,e.salary) from Employee e where e.dept = :dept";
        Query query = session.createQuery(hql);
        Department dept = new Department();
        dept.setId(100);
        List<Employee> list = query.setEntity("dept", dept).list();

    }

    @Test
    public void testQBC(){

        //1.创建一个Criteria对象
        Criteria criteria = session.createCriteria(Employee.class);

        //2.添加查询条件：QBC中查询条件使用Criterion来表示
        //Criterion可以通过Restrictions的静态方法得到
        criteria.add(Restrictions.eq("email", "22"));
        criteria.add(Restrictions.gt("salary", 100F));
        //AND 使用Conjunction表示：本身就是一个Criterion对象
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("name", "xx", MatchMode.ANYWHERE));
        Department department = new Department();
        department.setId(1);
        conjunction.add(Restrictions.eq("dept",department));
        System.out.println(conjunction);
        //OR 
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.ge("salary", 200F));
        disjunction.add(Restrictions.isNotNull("email"));
        System.out.println(disjunction);

        criteria.add(disjunction);
        criteria.add(conjunction);

        //3.执行查询
        Employee employee = (Employee) criteria.uniqueResult();
        System.out.println(employee);

    }

    @Test
    public void testSQL(){

        String sql = "insert into T_DEPARTMENT values(?,?)";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, 100).setString(1, "哈哈").executeUpdate();

    }

    @Test
    public void testBatch(){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                //通过JDBC原生API进行批量操作，效率最高，速度最快
                //TODO 批量操作代码
            }
        });
    }


}
