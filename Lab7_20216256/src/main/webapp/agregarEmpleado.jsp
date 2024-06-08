<%--
  Created by IntelliJ IDEA.
  User: Ricardo
  Date: 07/06/2024
  Time: 04:37 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.lab6_20216256.beans.Employees" %>
<%
    Employees employee = (Employees) request.getAttribute("employee");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Empleado</title>
</head>
<body>
<h1>Crear Nuevo Empleado</h1>
<form action="employee?action=add" method="post">
    <p>Nombre: <input type="text" name="first_name" required/></p>
    <p>Apellido: <input type="text" name="last_name" required/></p>
    <p>Email: <input type="email" name="email" required/></p>
    <p>Teléfono: <input type="text" name="phoneNumber" required/></p>
    <p>Fecha de Contratación: <input type="date" name="hireDate" required/></p>
    <p>Job ID: <input type="text" name="jobId" required/></p>
    <p>Salario: <input type="text" name="salary" required/></p>
    <p>Porcentaje de Comisión: <input type="text" name="commissionPct"/></p>
    <p>Manager ID: <input type="text" name="managerId"/></p>
    <p>Department ID: <input type="text" name="departmentId" required/></p>
    <p>Enabled: <input type="text" name="enabled" required/></p>
    <p><input type="submit" value="Crear Empleado"/></p>
</form>
<a href="home">Volver a la lista de empleados</a>
</body>
</html>
