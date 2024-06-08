<%--
  Created by IntelliJ IDEA.
  User: Ricardo
  Date: 07/06/2024
  Time: 04:07 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.lab6_20216256.beans.Employees" %>
<%@ page import="org.example.lab6_20216256.beans.Jobs" %>
<%@ page import="org.example.lab6_20216256.beans.Departments" %>
<%
    Employees employee = (Employees) request.getAttribute("employee");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Empleado</title>
</head>
<body>
<h1>Editar Empleado</h1>
<form action="<%=request.getContextPath()%>/home?action=a" method="post">
    <input type="hidden" name="action" value="a">
    <input type="hidden" name="id"  value="<%= employee.getEmployeeId() %>">
    <div class="inputs">
        <label for="nombre">Nombre completo:</label>
        <input type="text" id="nombre" name="fullName" value="<%= employee.getFullNameEmployee() %>" required>

        <label for="correo">Correo:</label>
        <input type="text" id="correo" name="email" value="<%= employee.getEmail() %>" required>

        <label for="phone">Teléfono:</label>
        <input type="text" id="phone" name="phone" value="<%= employee.getPhoneNumber() %>" required>

        <label for="hire_date">Fecha de contratación:</label>
        <input type="date" id="hire_date" name="hire_date" value="<%= employee.getHireDate() %>" >

        <div class="form-group">
            <label for="job_id">Job:</label>
            <select id="job_id" name="job_id" class = "form-control" >
                <%
                    for (Jobs job : jobs) {
                %>
                <option value="<%= job.getJobId() %>"<%= employee.getJobId().equals(job.getJobTitle()) ? "selected": "" %>><%=job.getJobTitle()%></option>
                <%
                    }
                %>
            </select>
        </div>

        <label for="salary">Salario:</label>
        <input type="text" id="salary" name="salary" value="<%= employee.getSalary() %>" >


        <div class="form-group">
            <label for="manager_id">Manager:</label>
            <select id="manager_id" name="manager_id" class="form-control" required>
                <% if (employee.getManagerId() == null) { %>
                <option value="0" selected>--Sin Jefe--</option>
                <% } else { %>
                <option value="0">--Sin Jefe--</option>
                <% } %>
                <% for (Employees manager : jefes) { %>
                <% if (employee.getManagerId() != null && employee.getManagerId().equals(manager.getFullNameEmployee())) { %>
                <option value="<%= manager.getEmployeeId() %>" selected><%= manager.getFullNameEmployee() %></option>
                <% } else { %>
                <option value="<%= manager.getEmployeeId() %>"><%= manager.getFullNameEmployee() %></option>
                <% } %>
                <% } %>
            </select>
        </div>

        <div class="form-group">
            <label for="department_id">Departamento:</label>
            <select id="department_id" name="department_id" class = "form-control" required>
                <%
                    for (Departments department : lista_departamentos) {
                %>
                <option value="<%= department.getDepartmentId() %>" <%= employee.getDepartmentId().equals(department.getDepartmentName()) ? "selected" : "" %>><%=department.getDepartmentName()%></option>
                <%
                    }
                %>
            </select>
        </div>
    </div>
    <div class = "buttons">
        <button type="submit"  >Guardar Progreso</button>
    </div>

</form>
<a href="home">Volver a la lista de empleados</a>
</body>
</html>
