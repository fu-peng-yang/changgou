package demo.file.textFile;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.file.textFile
 * @Author: yang
 * @CreateTime: 2021-05-02 15:13
 * @Description:
 */
public class Manager extends Employee{

    private String secretary;

    @Override
    public String toString() {
        return "Manager{" +
                "secretary='" + secretary + '\'' +
                '}';
    }

    public String getSecretary() {
        return secretary;
    }



    public Manager(String name, double salary, int year, int month, int data) {
        super(name, salary, year, month, data);
    }
}
