package edu.uptc.swii.employeeapp2.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import edu.uptc.swii.employeeapp2.model.Employee;
import edu.uptc.swii.employeeapp2.service.EmployeeDAOImpl;
import edu.uptc.swii.employeeapp2.service.IEmployeeDAO;

@RestController
public class AddEmployeeController {
    private IEmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @PostMapping("/save")
    public RedirectView addEmployee(Employee employee){
        if (this.employeeDAO.findById(employee.getId()) != null) {
            this.employeeDAO.save(employee);
            return new RedirectView("/employeeapp/error.html");
        } else {
            return new RedirectView("/employeeapp/confirm.html");
        }
    }

    @RequestMapping("/findbyid")
    public Employee findByID(String id){
        return this.employeeDAO.findById(id);
    }

    @RequestMapping("/findall")
    public List<Employee> findAll() {
        return this.employeeDAO.findAll();
    }

    @RequestMapping("/deletebyid")
    public void deleteEmployee(String id) {
        this.employeeDAO.deleteEmployeeById(id);
    }

}
