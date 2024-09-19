package edu.uptc.swii.employeeapp2.service;

import edu.uptc.swii.employeeapp2.model.Employee;
import java.sql.Connection; //Hace la conexion con BD
import java.sql.Statement; //FABIRCA LAS SENTENCIAS QUE SE VAN A EJECUTAR EN LA BD
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements IEmployeeDAO{

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL ="jdbc:mysql://localhost:3306/employee?useSSL=false&allowPublicKeyRetrieval=true";
    private static Connection connection  = null;
    private Statement stmt= null;

    @Override
    public List<Employee> findAll() {
        return this.getEmployees();
    }

    @Override
    public Employee findById(String id) {
        return this.getEmployee(String.valueOf(id));
    }

    @Override
    public boolean save(Employee employee) {
        boolean res=false;
        String id = this.getEmployee(employee.getId()).getId();
        if(id!=null)
            res = this.executeQuery("update employee set id='"+employee.getId()+"', name='"+employee.getName()+"', email='"+employee.getEmail()+"', phone='"+employee.getPhone()+"' where id='"+employee.getId()+"';");
        else
            res=this.executeQuery("insert into employee (id, name, email, phone) values('"+employee.getId()+"','"+employee.getName()+"','"+employee.getEmail()+"','"+employee.getPhone()+"');");
        return res;
    }

    @Override
    public boolean deleteEmployeeById(String id) {
        boolean res=false;
        if(this.getEmployee(id)!=null)
            res=this.executeQuery("delete from employee where id='"+id+"';");
        return res;
    }

    private boolean executeQuery(String query){
        boolean res = false;
        try{    
                Class.forName(DRIVER);  
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                stmt = connection.createStatement();
                stmt.executeUpdate(query);
                connection.close();
                res=true;
        }catch(SQLException sqlex){System.out.println(sqlex);}
        catch(ClassNotFoundException ex){System.out.println(ex);}
        return res;    
    }

    private Employee getEmployee(String id){
            String query = "select * from employee where id="+id+";";
            Employee emp = new Employee();
            try{
                Class.forName(DRIVER);  
                connection = DriverManager.getConnection(URL,USER,PASSWORD);
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    emp.setId(rs.getString("id"));
                    emp.setName(rs.getString("name"));
                    emp.setEmail(rs.getString("email"));
                    emp.setPhone(rs.getString("phone"));
                }
                connection.close();
            }catch(SQLException sqlex){System.out.println(sqlex);}
            catch(ClassNotFoundException ex){System.out.println(ex);}
            return emp; 

    }

    private List<Employee> getEmployees(){
        String query = "select * from employee";
        List<Employee> list = new ArrayList<Employee>();
        Employee employee = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);  
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
             rs = stmt.executeQuery(query);
            while (rs.next()) {
                employee = new Employee();
                /*Retrieve one employee details 
                and store it in employee object*/
                employee.setId(rs.getString("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail((rs.getString("email")));
                employee.setPhone((rs.getString("phone")));    
                //add each employee to the list
                list.add(employee);
            }
            connection.close(); 
        }catch(SQLException sqlex){System.out.println(sqlex);}
        catch(ClassNotFoundException ex){System.out.println(ex);}
        return list;
    }
        
}
