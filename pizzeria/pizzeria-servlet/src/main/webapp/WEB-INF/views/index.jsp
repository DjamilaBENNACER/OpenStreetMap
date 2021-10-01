<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body>
<table style="width:100%">
  <thead>
    <tr>
      <th>Pizza</th>
      <th>Prix</th>
      <th>Ingredients</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${pizzas}" var="pizza">
      <tr>
        <th>${pizza.name}</th>
        <th>${pizza.prix}</th>
        <th>${pizza.ingredients}</th>
      </tr>
    </c:forEach>
  </tbody>
</table>
</body>
</html>
