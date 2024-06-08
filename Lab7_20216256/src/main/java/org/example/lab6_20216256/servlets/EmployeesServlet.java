package org.example.lab6_20216256.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab6_20216256.beans.Employees;
import org.example.lab6_20216256.daos.DaoEmployee;
import org.example.lab6_20216256.daos.DaoJobs;
import org.example.lab6_20216256.beans.Jobs;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

@WebServlet(name = "Employees", value = "/home")
public class EmployeesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        DaoEmployee dao = new DaoEmployee();
        ArrayList<Employees> lista = dao.listarEmployees();

        String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("edit")) {
                int employeeId = Integer.parseInt(request.getParameter("id"));
                Employees employee = dao.getEmployeePorId(employeeId);
                DaoEmployee daoEmployee = new DaoEmployee();
                DaoJobs job= new DaoJobs();


                Employees employeeEdit = daoEmployee.getEmployeePorId(employeeId);
                request.setAttribute("employeeE", employeeEdit);

                ArrayList<Jobs> listaTrabajosU = job.listarJobs();
                request.setAttribute("jobs", listaTrabajosU);




                ArrayList<Employees> listaJefesU = daoEmployee.listarEmployees();
                request.setAttribute("jefes", listaJefesU);

                request.setAttribute("employee", employee);
                RequestDispatcher view = request.getRequestDispatcher("editarEmpleado.jsp");
                view.forward(request, response);



            } else if (action.equals("add")) {
                RequestDispatcher view = request.getRequestDispatcher("agregarEmpleado.jsp");
                view.forward(request, response);
            }  else if (action.equals("delete")) {
                int employeeId = Integer.parseInt(request.getParameter("id"));
                dao.deleteEmployee(employeeId);
                response.sendRedirect("home");
            }

        } else {
            request.setAttribute("lista", lista);
            RequestDispatcher view = request.getRequestDispatcher("paginaPrincipal.jsp");
            view.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        DaoEmployee dao = new DaoEmployee();


        if (action != null && action.equals("add")) {
            String Name = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String fullName=Name + " " + lastName;
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            Date hireDate = Date.valueOf(request.getParameter("hireDate"));
            System.out.println(hireDate);
            String jobId = request.getParameter("jobId");
            BigDecimal salary = new BigDecimal(request.getParameter("salary"));
            BigDecimal commissionPct = new BigDecimal(request.getParameter("commissionPct"));
            int managerId = Integer.parseInt(request.getParameter("managerId"));
            int departmentId = Integer.parseInt(request.getParameter("departmentId"));
            int enabled = Integer.parseInt(request.getParameter("enabled"));

            dao.agregrarEmployee(fullName, email, phoneNumber, hireDate, jobId, salary, commissionPct, managerId, departmentId, enabled);
        } else if(action.equals("delete")) {

            int idEmployee = Integer.parseInt(request.getParameter("idDelete"));

            DaoEmployee daoEmployeeD = new DaoEmployee();

            daoEmployeeD.deleteEmployee(idEmployee);

            response.sendRedirect(request.getContextPath() + "/home?action=ListaEmpleados");
        } else if(action.equals("edit")){

            DaoEmployee daos= new DaoEmployee();
            int id= Integer.parseInt(request.getParameter("id"));
            Employees employee= daos.getEmployeePorId(id);

            String fullName = request.getParameter("fullName");
            String correo = request.getParameter("c");
            String telefono = request.getParameter("phone");
            String fContratacion = request.getParameter("hire_date");
            String job = request.getParameter("a");
            String salario = request.getParameter("s");
            String manager = request.getParameter("m");
            String department = request.getParameter("d");

            employee.setFullNameEmployee(fullName);

            employee.setEmail(correo);

            employee.setPhoneNumber(telefono);


            if (fContratacion != null && !fContratacion.isEmpty()) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date hire_date = formatter.parse(fContratacion);
                employee.setHireDate(hire_date);

            }

            BigDecimal salarioDU = null;
            if (salario != null && !salario.isEmpty()) {

                salarioDU = new BigDecimal(salario);

            }
            employee.setSalary(salarioDU);

            Integer managerId = 0;
            if (!manager.equals("0")) {
                managerId = Integer.parseInt(manager);
            }
            int departmentId = Integer.parseInt(department);

            daos.editarEmployee(employee,job,managerId,departmentId);
            response.sendRedirect(request.getContextPath() + "/home");

            }
    }
    public void destroy() {
    }
}
