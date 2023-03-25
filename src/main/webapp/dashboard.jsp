<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cattle Weight Dashboard</title>
    <link rel="stylesheet" href="styles3.css">
</head>
<body>
    <h1>Cattle Weight Dashboard</h1>

    <h2>Add New Cattle Weight Data</h2>
    <form action="CattleWeightServlet" method="post">
        <label for="cattleId">Cattle ID:</label>
        <input type="text" id="cattleId" name="cattleId" required>
        <label for="weight">Weight (kg):</label>
        <input type="number" id="weight" name="weight" step="0.01" min="0" required>
        <input type="submit" value="Submit">
    </form>

    <table>
          <tr>
            <th>Cattle ID</th>
            <th>Weight (kg)</th>
          </tr>
          <c:if test="${empty cattleWeights}">
            <tr>
              <td colspan="2">No cattle weights found</td>
            </tr>
          </c:if>
          <c:forEach var="item" items="${cattleWeights}">
            <c:if test="${empty item.cattleId}">
              <tr>
                <td colspan="2">Missing cattle ID</td>
              </tr>
            </c:if>
        <c:if test="${empty item.weight}">
          <tr>
            <td colspan="2">Missing weight</td>
          </tr>
        </c:if>
        <tr>
          <td><c:out value="${item.cattleId}"/></td>
          <td><c:out value="${item.weight}"/></td>
        </tr>
      </c:forEach>
    </table>
</body>
</html>