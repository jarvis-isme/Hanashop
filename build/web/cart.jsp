<%-- 
    Document   : cart
    Created on : Jan 17, 2021, 4:40:08 PM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <body>
        <c:import url='navbar.jsp'/>
        <c:if test="${sessionScope.CART ==null}">
            <h2>You haven't buy any one back to shopping</h2>
        </c:if>
        <c:if test="${sessionScope.CART !=null}">
            <c:if test="${! sessionScope.USER.roleId eq 'US'}">
                <h2>You haven't buy any one back to shopping</h2>
            </c:if>
            <c:if test="${sessionScope.USER.roleId eq 'US'}">
                <c:set scope="session" var='cart' value="${sessionScope.CART.cart}"/>
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Food Id</th>
                            <th>Food Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Image</th>
                            <th>Amount</th>
                            <th>Update</th>
                            <th>Deleted</th>
                            <th>Note</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var = "totalPrice" scope = "request" value = "${0}"/>
                        <c:forEach  varStatus="counter" var='item' items="${cart}">
                            <tr>
                        <form action="MainController">
                            <c:set var="totalPrice" scope="request" value="${totalPrice+(item.value.price*item.value.quantity)}"/>
                            <td>${counter.count}</td>
                            <td> ${item.value.foodId}</td>
                            <td>${item.value.foodName}</td>
                            <td><input type="number" name="txtQuantity" value="${item.value.quantity}"/></td>
                            <td>${item.value.price}</td>
                            <td><img width="50px" src="${item.value.image}"/></td>
                            <td>${item.value.price*item.value.quantity}</td>
                            <td><input type="submit" name="btnAction" value="Update"/></td>
                            <td>
                                <form action="MainController">
                                    <input type="hidden" name="txtFoodId" value="${item.value.foodId}"/>
                                    <button name="btnAction" onclick="handleClick()" value="Delete Cart">Delete</button>
                                </form>
                            </td>
                            <td>
                                <c:forEach var='error' items="${requestScope.UPDATE_CART_ERROR}">
                                    <c:if test="${error.key eq item.key}">
                                        ${error.value}
                                    </c:if>
                                </c:forEach>

                                <c:forEach var='errorCheckOut' items="${requestScope.CHECKOUT_ERROR}">
                                    <c:if test="${errorCheckOut.key eq item.key}">

                                        ${errorCheckOut.value}
ss
                                    </c:if>
                                    <c:if test="${!errorCheckOut.key eq item.key}">

                                        not giong
                                    </c:if>
                                </c:forEach>
                        
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <hr/>
        <h2>Check Out</h2>
        <form action="MainController">
            <div class="form-group col-md-6">
                <span>TotalPrice:${totalPrice}</span>
            </div>
            <div class="form-group col-md-6">
                <label for="address">Deleivery Address:</label>
                <input type="text" id='address'name="txtAddress" value="${sessionScope.USER.address}"/>
                <p color='red'>${requestScope.ADDRESS_ERROR}</p>
            </div>
            <input type="submit" name="btnAction" value="Buy"/>
        </form>
    </c:if>
</c:if>
<script>
    function handleClick() {
        promt = confirm('Do you really want to delete');
        if (promt) {
            return true;
        } else {
            return false
        }
    }
</script>
</body>
</html>
