<%-- 
    Document   : shopping.jsp
    Created on : Jan 16, 2021, 8:14:29 AM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
    </head>
    <style>
        .card {
            max-width: 300px;

            border: 1px solid #000000;
            padding: 10px;
        }
        .card img{
            height: 200px;
        }
        .row{
            padding:10px;
        }
        .form{
            border-bottom: 1px solid #000000;
        }
        select:focus{
            outline: none;
        }
    </style>
    <body>
        <c:import url="./navbar.jsp"/>

        <c:if test="${sessionScope.FOOD_LIST_USER== null || sessionScope.CATEGORY_LIST_USER==null }">
            <h2>Not found</h2>
            <a class="btn-primary" href="MainController?btnAction=search">Get All</a>
        </c:if>
        <c:if test="${sessionScope.FOOD_LIST_USER!=null}">
            <form action='MainController' class='form'>
                <div class='row'>
                    <select name="txtCategoryId" class="form-control col-md-1">
                        <option value="">None</option>
                        <c:forEach var='item' items="${sessionScope.CATEGORY_LIST_USER}">
                            <option value="${item.categoryId}">${item.categoryName}</option>
                        </c:forEach>
                    </select>
                    <div class="form-group col-md-5">
                        <input type="text" placeholder="Enter Food You want to find" name='txtFoodName' class="form-control"id="foodName">
                    </div>

                </div>
                <div class='row'>
                    <div class="form-group col-md-2">
                        <input type='number' class="form-control" placeholder="Min" name='min' />
                    </div>
                    <div class="form-group col-md-2">
                        <input type='number' class="form-control" placeholder="Max" name='max' />
                    </div>
                </div>
                <div class="form-group col-md-5">
                    <input type='submit' name='btnAction' class="btn btn-danger" value='search' />
                </div>
            </form>
            <c:forEach var='row' begin="1" end='5'>
                <div class="row">
                    <c:forEach var='item' items="${sessionScope.FOOD_LIST_USER}" varStatus="counter" >
                        <c:if test="${counter.count> (row-1)*4 &&   counter.count <=row*4}">
                            <div class="col-sm">
                                <div class="card">
                                    <img class="img-fluid" src="${item.image}" alt="Card image cap">
                                    <div class="card-body">
                                        <h5 class="card-title">${item.foodName}</h5>
                                        <p class='card-text'>${item.description}</p>
                                        <span>${item.price}</span>
                                        <p class='card-text'>${item.quantity}</p>
                                        <p class="card-text">${item.categoryId}</p>
                                        <p clas="card-test">${item.foodId}</p>
                                        <p>${counter.count}</p>
                                        <c:if test="${ sessionScope.USER != null}">
                                            <c:if test="${ ! (sessionScope.USER.roleId eq 'AD') && item.quantity>0}">
                                                <a class='btn btn-primary' href="MainController?btnAction=AddToCart&txtFoodId=${item.foodId}">Add to Cart</a>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${ sessionScope.USER == null && item.quantity>0}">
                                            <a class='btn btn-primary' href="MainController?btnAction=AddToCart&txtFoodId=${item.foodId}">Add to Cart</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div> 
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
            <div class="row">
                <c:forEach begin='1' var='count' end="${sessionScope.COUNT_PAGE}">
                    <form action="MainController">
                        <input type="hidden" name="txtCategoryId" value="${param.txtCategoryId}">
                        <input type="hidden" name="txtFoodName" value="${param.txtFoodName}"/>
                        <input  type="hidden" name="min" value="${param.min}"/>
                        <input type="hidden" name="max" value="${param.max}"/>
                        <input type="hidden" name="page" value="${count}"/>
                        <button type="submit" name="btnAction" value="search">${count}</button>
                    </form>
                </c:forEach>
            </div>
            <h3>May be you like</h3>
            <div class="row">
                <c:forEach var='item' items="${sessionScope.USER_LIKE_LIST}" varStatus="counter" >
                    <div class="col-sm">
                        <div class="card">
                            <img class="img-fluid" src="${item.image}" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title">${item.foodName}</h5>
                                <p class='card-text'>${item.description}</p>
                                <span>${item.price}</span>
                                <p class='card-text'>${item.quantity}</p>
                                <p class="card-text">${item.categoryId}</p>
                                <p clas="card-test">${item.foodId}</p>
                                <p>${counter.count}</p>
                                <c:if test="${ sessionScope.USER != null}">
                                    <c:if test="${ ! (sessionScope.USER.roleId eq 'AD') && item.quantity>0}">
                                        <a class='btn btn-primary' href="MainController?btnAction=AddToCart&txtFoodId=${item.foodId}">Add to Cart</a>
                                    </c:if>
                                </c:if>
                                <c:if test="${ sessionScope.USER == null && item.quantity>0}">
                                    <a class='btn btn-primary' href="MainController?btnAction=AddToCart&txtFoodId=${item.foodId}">Add to Cart</a>
                                </c:if>
                            </div>
                        </div>
                    </div> 

                </c:forEach>
            </div>
        </c:if>
    </body>
</html>
