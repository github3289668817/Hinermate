package per.xgt.pojo;

/**
 * @Author: Yvonneflynn's husband
 * @Email：I don't know
 * @CreateTime: 2020-09-24 15:50:48
 * @Descirption:
 */
public class Employee {

    private Integer id;
    private String name;
    private float salary;
    private String email;
    private Department dept;

    public Employee() {
    }

    public Employee(String email,float salary) {
        this.salary = salary;
        this.email = email;
    }

    public Employee(Integer id, String name, float salary, String email, Department dept) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
