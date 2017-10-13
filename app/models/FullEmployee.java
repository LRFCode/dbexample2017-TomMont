package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class FullEmployee
{
    @Id
    private int EmployeeId;
    private String TitleOfCourtesy;
    private String FirstName;
    private String LastName;
    private String Title;
    private String ReportsToLastName;
    private BigDecimal Salary;

    public FullEmployee(int employeeId, String titleOfCourtesy, String firstName, String lastName, String title, String reportsToLastName, BigDecimal salary)
    {
        this.EmployeeId = employeeId;
        this.TitleOfCourtesy = titleOfCourtesy;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Title = title;
        this.ReportsToLastName = reportsToLastName;
        this.Salary = salary;
    }

    public int getEmployeeId()
    {
        return EmployeeId;
    }

    public String getTitleOfCourtesy()
    {
        return TitleOfCourtesy;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public String getTitle()
    {
        return Title;
    }

    public String getReportsToLastName()
    {
        return ReportsToLastName;
    }

    public BigDecimal getSalary()
    {
        return Salary;
    }
}
