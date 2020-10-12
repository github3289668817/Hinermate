package per.xgt.pojo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

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
    public void testSave(){
        Department department = new Department();
        department.setDeptName("D1");

        Manager manager = new Manager();
        manager.setMgrName("M1");

        department.setManager(manager);
        manager.setDepartment(department);

        session.save(manager);
        session.save(department);
    }

    @Test
    public void testGet(){
        Department department = (Department) session.get(Department.class, 1);
        System.out.println(department);
        Manager manager = department.getManager();
        System.out.println(manager.getMgrName());
    }

    @Test
    public void testGet2(){
        Manager manager = (Manager) session.get(Manager.class, 1);
        System.out.println(manager.getMgrName());
        System.out.println(manager.getDepartment().getDeptName());
    }

    @Test
    public void test1(){
        Category category1 = new Category();
        category1.setName("C11");

        Category category2 = new Category();
        category2.setName("C22");

        Item item1 = new Item();
        item1.setName("I11");

        Item item2 = new Item();
        item2.setName("I22");

        category1.getItems().add(item1);
        category2.getItems().add(item2);
        category1.getItems().add(item2);
        category2.getItems().add(item1);

        item1.getCategories().add(category1);
        item1.getCategories().add(category2);
        item2.getCategories().add(category1);
        item2.getCategories().add(category2);

        session.save(category1);
        session.save(category2);

        session.save(item1);
        session.save(item2);

    }

    @Test
    public void test2(){
        Category category = (Category) session.get(Category.class, 1);
        System.out.println(category.getName());

        Set<Item> items = category.getItems();
        System.out.println(items.size());
    }

    @Test
    public void test3(){

        Person person = new Person();
        person.setAge(1111);
        person.setName("A11");
        session.save(person);

        Student student = new Student();
        student.setAge(2222);
        student.setName("B22");
        student.setSchool("AABBCC");
        session.save(student);

    }

    @Test
    public void test4(){
        List<Person> persons = session.createQuery("FROM Person").list();
        System.out.println(persons.size());
        List<Student> students = session.createQuery("FROM Student").list();
        System.out.println(students.size());
    }

    @Test
    public void test5(){
        String hql = "update Person p set p.age = 20";
        session.createQuery(hql).executeUpdate();
    }

}
