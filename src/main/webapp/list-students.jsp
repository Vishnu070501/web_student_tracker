<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<title>students-listed</title>
<link type="text/css" rel="stylesheet" href="css/style.css"/>
</head>
<body>
<!-- get the students list passed on by the controller servlet -->

<div id = "Wrapper">

<div id = "Header">
Karpagam Institute of Technology

</div>

</div>


<div id = "Container">

<div id ="Content">
<input type="button" value="Add Student"
onclick="window.location.href='add-student-form.jsp'"
class="add-student-button"/>
<Table Border="1">
<tr>
<th>ID</th><th>First Name</th><th>Last Name</th><th>email</th><th>Action</th>
</tr>
<c:forEach var="temp" items="${myStudents}">

<!-- Update links for each of the fields -->
<c:url var="templink" value="StudentControllerServlet">
<c:param name="command" value="LOAD"/>
<c:param name="studentID" value="${temp.ID}"/>
</c:url>

<!-- Delete links for each of the fields -->
<c:url var="dellink" value="StudentControllerServlet">
<c:param name="command" value="DELETE"/>
<c:param name="studentID" value="${temp.ID}"/>
</c:url>
<tr>
<td>${ temp.ID}</td>
<td>${ temp.firstname}</td>
<td>${ temp.lastname}</td>
<td>${ temp.email}</td>
<td><a href="${templink}">Update</a>|
<a href="${dellink}"
onclick="(!(confirm('Do you sure you want to delete this student ?'))) return false">
Delete</a> </td>
</tr>
</c:forEach>
</Table>

</div>

</div>

</body>
</html>
