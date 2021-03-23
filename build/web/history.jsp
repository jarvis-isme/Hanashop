<%-- 
    Document   : history.jsp
    Created on : Jan 19, 2021, 11:37:34 AM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <c:import url="./navbar.jsp"/>
        <c:if test="${sessionScope.USER==null}">
            Your role is not valid to access this resource, login pls!
        </c:if>
        <c:if test="${sessionScope.USER!=null}">
            <c:if test="${! sessionScope.USER.roleId eq 'US'}">
                Your role is not valid to access this resource, login pls!
            </c:if>
            <c:if test="${sessionScope.USER.roleId eq 'US'}">
                <form action="MainController">
                    FoodName <input  type='text'name="txtFoodName" value="" />
                    Day <input type="number" name="txtDay" value=""/>
                    Month<input type="number" name="txtMonth" value=""/>
                    Year<input type='number' name='txtYear' value=""/>
                    <input type="submit" name='btnAction' value='SearchOrder'/>
                </form>
                <h3>History Order</h3>
                <c:if test="${sessionScope.ORDER_HISTORY_LIST==null}">
                    <a class="nav-link" href="MainController?btnAction=SearchOrder">Get All</a>
                </c:if>
                    
                <c:if test="${sessionScope.ORDER_HISTORY_LIST !=null}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>OrderId</th>
                                <th>Food Id</th>
                                <th>Food Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Image</th>
                                <th>Category Id</th>
                                <th>Bought Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var='order' items="${sessionScope.ORDER_HISTORY_LIST}">
                                <c:forEach var='item' items="${order.value}">
                                    <tr>
                                        <td>${order.key.orderId}</td>
                                        <td>${item.foodId}</td>
                                        <td>${item.foodName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.price}</td>
                                        <td><img width="50px" src="${item.image}"/></td>
                                        <td>${item.categoryId}</td>
                                        <td>${order.key.boughtDate}</td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>

                </c:if>
            </c:if>
        </c:if>
    </body>
</html>
