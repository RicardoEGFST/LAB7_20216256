<%--
  Created by IntelliJ IDEA.
  User: Ricardo
  Date: 31/05/2024
  Time: 06:51 p. m.
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.lab6_20216256.beans.Employees" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    ArrayList<Employees> lista = (ArrayList<Employees>) request.getAttribute("lista");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista de empleados</title>
</head>
<body>
<h1>Lista de empleados</h1>


<a href="home?action=add">Agregar empleado</a>
<%
    if (lista == null || lista.isEmpty()) {
%>
<p>No se encontraron películas;</p>
<%
} else {
%>
<table border="1">
    <thead>
    <tr>

        <th>Nombre completo</th>
        <th>Correo</th>
        <th>Número telefónico</th>
        <th>Fecha de contratación</th>
        <th>Trabajo</th>
        <th>Salario</th>
        <th>Manager</th>
        <th>Departamento</th>
        <th></th>

    </tr>
    </thead>
    <tbody>
    <%
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);

        for (Employees employee : lista) {
            String salaryFormatted = formatter.format(employee.getSalary()); %>
            <%SimpleDateFormat dateformat= new SimpleDateFormat("yyyy-MM-dd");%>


    <tr>

        <td><%=employee.getFullNameEmployee()%></td>
        <td><%=employee.getEmail()%></td>
        <td><%=employee.getPhoneNumber()%></td>
        <td><%=dateformat.format(employee.getHireDate())%></td>
        <td><%=employee.getJobId()%></td>
        <td><%=employee.getSalary()%></td>
        <td><%= employee.getManagerId() != null ? employee.getManagerId() : "Sin Jefe" %></td>
        <td><%=employee.getDepartmentId()%></td>
        <td>
            <a href="home?action=edit&id=<%= employee.getEmployeeId() %>">Editar</a>
            <form id="eliminarEmpleado<%=employee.getEmployeeId()%>" action="<%=request.getContextPath()%>/home" method="post" style="display: inline;">
                <input type="hidden" name="action" value="eliminarEmpleado">
                <input type="hidden" name="idDelete" value="<%= employee.getEmployeeId() %>">
                <a href="home?action=delete&id=<%= employee.getEmployeeId() %>" onclick="return confirm('¿Estás seguro de que deseas eliminar este empleado?');">Borrar</a>
            </form>


        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>