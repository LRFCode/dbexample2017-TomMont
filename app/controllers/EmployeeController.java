package controllers;

import models.Employee;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class EmployeeController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public EmployeeController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getEmployees()
    {
        List<Employee> employees =
                jpaApi.em().createQuery("SELECT e FROM Employee e ORDER BY lastName, firstName", Employee.class).getResultList();

        return ok(views.html.employees.render(employees));
    }

    @Transactional(readOnly = true)
    public Result employeeSearch()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String lastName = form.get("lastname");

        if(lastName == null)
        {
            lastName = "";
        }
        List<Employee> employees =
                jpaApi.em().createQuery("SELECT e FROM Employee e WHERE lastname LIKE :lastname ORDER BY lastName, firstName", Employee.class).
                        setParameter("lastname", "%" +lastName + "%").
                        getResultList();

        return ok(views.html.employeesearch.render(employees));
    }

    @Transactional(readOnly = true)
    public Result getEmployee(Integer id)
    {
        Employee employee =
                jpaApi.em().createQuery("SELECT e FROM Employee e WHERE employeeId = :id", Employee.class).setParameter("id", id).getSingleResult();
        List<Employee> employees =
                jpaApi.em().createQuery("SELECT e FROM Employee e ORDER BY lastName, firstName", Employee.class).getResultList();
        return ok(views.html.employee.render(employee, employees));
    }

    @Transactional
    public Result updateEmployee(Integer id)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String firstName = form.get("firstname");
        String lastName = form.get("lastname");
        String title = form.get("title");
        String titleOfCourtesy = form.get("titleofcourtesy");
        Integer reportsTo = new Integer(form.get("reportsto"));

        Employee employee =
                jpaApi.em().createQuery("SELECT e FROM Employee e WHERE employeeId = :id", Employee.class).setParameter("id", id).getSingleResult();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setTitle(title);
        employee.setTitleOfCourtesy(titleOfCourtesy);
        employee.setReportTo(reportsTo);


        jpaApi.em().persist(employee);

        return redirect(routes.EmployeeController.getEmployees());

    }


}
