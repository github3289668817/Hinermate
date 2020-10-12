package per.xgt.dao;

import org.hibernate.Session;
import per.xgt.pojo.Department;
import per.xgt.utils.HibernateUtils;

/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-27 16:10:02
 * @Descirption:
 */
public class DepartmentDao {

    /**
     * 若需要传入一个Session对象，则意味着上一层Service需要获取到Session对象
     * 这说明Service需要和Hibernate耦合，所以不可能传参有Session
     * @param department
     */
    public void save(Department department){
        //内部获取和当前线程绑定的Session对象
        //好处：1.不需要从外部传入Session对象；2.多个Dao方法也可以使用一个事务
        Session session = HibernateUtils.getInstance().getSession();
        session.save(department);

    }

}
