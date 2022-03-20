package demo.file.textFile;

import java.time.LocalDate;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.textFile
 * @Author: yang
 * @CreateTime: 2021-05-02 09:42
 * @Description:
 */
public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String n, double s, int year,int month,int day) {
        name = n;
        salary = s;
        hireDay = LocalDate.of(year,month,day);
    }

    public void raiseSalary(double byPercent){
        double raise = salary * byPercent /100;
        salary += raise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void setHireDay(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}
