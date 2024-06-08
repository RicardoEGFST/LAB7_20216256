package org.example.lab6_20216256.daos;

import org.example.lab6_20216256.beans.Employees;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class DaoEmployee {
    //-----------------------------------------------------------------------------------------------------------
    public ArrayList<Employees> listarEmployees() {

        ArrayList<Employees> employees = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        // Definimos el query mediante el cual obtendremos los datos que necesitamos de la base de datos
        String query = "SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS 'fullNameEmployee', e.email, " +
                "e.phone_number, e.salary, e.hire_date, " +  "(SELECT d.department_name FROM departments d WHERE d.department_id = e.department_id) AS 'departament', " +
                "(SELECT j.job_title FROM jobs j WHERE j.job_id = e.job_id) AS 'job', " +
                "(SELECT CONCAT(m.first_name, ' ', m.last_name) FROM employees m WHERE m.employee_id = e.manager_id) AS 'manager' " +
                "FROM employees e ";

        try (Connection conn = DriverManager.getConnection(url, username, password);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //Rellenamos la lista con las películas mientras .next no se quede sin filas y retorne false
            while (rs.next()) {

                Employees employee = new Employees();

                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setFullNameEmployee(rs.getString("fullNameEmployee"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setJobId(rs.getString("job"));
                employee.setSalary(rs.getBigDecimal("salary"));
                employee.setDepartmentId(rs.getString("departament"));
                employee.setManagerId(rs.getString("manager"));


                employees.add(employee);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    //-----------------------------------------------------------------------------------------------------------
    public Employees getEmployeePorId(int id) {
        //Creo el empleado
        Employees employee = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        String query = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new Employees();

                employee.setEmployeeId(rs.getInt(1));
                employee.setFullNameEmployee(rs.getString(2) + " " + rs.getString(3));
                employee.setEmail(rs.getString(4));
                employee.setPassword(rs.getString(5));
                employee.setPhoneNumber(rs.getString(6));
                employee.setHireDate(rs.getDate(7));
                employee.setJobId(rs.getString(8));
                employee.setSalary(rs.getBigDecimal(9));
                employee.setCommissionPct(rs.getBigDecimal(10));
                employee.setManagerId(rs.getString(11));
                employee.setDepartmentId(rs.getString(12));
                employee.setEnabled(rs.getInt(13));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    //-----------------------------------------------------------------------------------------------------------
    public void editarEmployee(Employees employee, String job, Integer manager, Integer department){
        String query = "UPDATE employees " +
                "SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, " +
                "job_id = ?, salary = ?, manager_id = ?, department_id = ? " +
                "WHERE employee_id = ?";

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";


        try (Connection conn = DriverManager.getConnection(url, username, password);
        PreparedStatement pstmt = conn.prepareStatement(query)){

            String fullName = employee.getFullNameEmployee();
            String[] names = fullName.split(" ");
            String first_name = names[0].trim();
            String last_name = names.length > 1 ? names[1].trim() : "";

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPhoneNumber());
            pstmt.setDate(5,new java.sql.Date(employee.getHireDate().getTime()));
            pstmt.setString(6, job);
            pstmt.setBigDecimal(7, employee.getSalary());
            if (manager != 0) {
                pstmt.setInt(8, manager);
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setInt(9, department);
            pstmt.setInt(10, employee.getEmployeeId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //-----------------------------------------------------------------------------------------------------------
    public void deleteEmployee(int id) {

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";


        try (Connection conn = DriverManager.getConnection(url, username, password)){
            String updEmployees = "UPDATE employees SET manager_id = NULL WHERE manager_id = ?";
            try (PreparedStatement pstmtUpdEmployees = conn.prepareStatement(updEmployees);) {
                pstmtUpdEmployees.setInt(1, id);
                pstmtUpdEmployees.executeUpdate();
            }

            String updDepartments = "UPDATE departments SET manager_id = NULL WHERE manager_id = ?";
            try (PreparedStatement pstmtUpdDepartments = conn.prepareStatement(updDepartments);) {
                pstmtUpdDepartments.setInt(1, id);
                pstmtUpdDepartments.executeUpdate();
            }
            String delJobH = "DELETE FROM job_history WHERE employee_id = ?";
            try (PreparedStatement pstmtDelEmployee = conn.prepareStatement(delJobH)) {
                pstmtDelEmployee.setInt(1, id);
                pstmtDelEmployee.executeUpdate();

            }
            String delEmployee = "DELETE FROM employees WHERE employee_id = ?";
            try (PreparedStatement pstmtDelEmployee = conn.prepareStatement(delEmployee)) {
                pstmtDelEmployee.setInt(1, id);
                pstmtDelEmployee.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //---------------------------------------------------------------------------------------------------------------------
    public void agregrarEmployee(String fullName, String email, String phoneNumber, Date hireDate, String jobId, BigDecimal salary, BigDecimal commissionPct, int managerId, int departmentId, int enabled) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        String query  = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id, enabled) VALUES ('PEPE', 'GRILLO', 'john.doe@example.com', '1234567890', '2024-06-07 15:30:00', 'IT_PROG', 60000, 0.10, 101, 60, 1)";

        try (Connection conn = DriverManager.getConnection(url, username , password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String[] nameParts = fullName.split(" ", 2);
            if (nameParts.length < 2) {
                throw new IllegalArgumentException("Full name must contain both first name and last name.");
            }
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.setDate(5, hireDate);
            pstmt.setString(6, jobId);
            pstmt.setBigDecimal(7, salary);
            pstmt.setBigDecimal(8, commissionPct);
            pstmt.setInt(9, managerId);
            pstmt.setInt(10, departmentId);
            pstmt.setInt(11, enabled);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}