<%-- 
    Document   : addfoodpage
    Created on : Jan 6, 2021, 9:24:27 AM
    Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Food Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    </head>
    <body>
        <c:if test="${sessionScope.USER ==null}">
            <c:import url='./navbar.jsp'></c:import>
                Your role is not valid 
        </c:if>
        <c:if test='${sessionScope.USER !=null}'>
            <c:if test="${! sessionScope.USER.roleId eq 'AD'}">
                <c:import url='./navbar.jsp'></c:import>
                    Your role is not valid 
            </c:if>
            <c:if test="${sessionScope.USER.roleId eq 'AD'}">
                <div class='container '>
                    <h2 className='card-title'>Add Food</h2>
                    <form action='MainController'enctype="multipart/form-data" >
                        <div class="form-group col-md-6">
                            <label for="foodId">Food Id:</label>
                            <input type="text"  name ='txtFoodId'class="form-control" value='${param.txtFoodId}' id="foodId" placeholder="Food ID">
                        </div>
                        <p>${requestScope.FOOD_ERROR.foodIdError}</p>
                        <div class="form-group col-md-6">
                            <label for="foodName">Food Name:</label>
                            <input type="text"  name ='txtFoodName'class="form-control" value='${param.txtFoodName}' id="foodName" placeholder="Food Name">
                        </div>
                        <p>${requestScope.FOOD_ERROR.foodNameError}</p>
                        <div class="form-group col-md-3">
                            <label for="quantity">Quantity:</label>
                            <input type="number"  name ='txtQuantity'class="form-control" value='${param.txtQuantity}' id="quantity" placeholder="Quantity">
                        </div>
                        <p>${requestScope.FOOD_ERROR.quantityError}</p>
                        <div class="form-group col-md-3">
                            <label for="expiredDate">ExpiredDate:</label>
                            <input type="number"  name ='txtExpiredDate'class="form-control" value='${param.txtExpiredDate}' id="expiredDate" placeholder="ExpiredDate">
                        </div>

                        <div class="form-group col-md-6">
                            <label for="description">Description:</label>
                            <input type="text"  name ='txtDescription'class="form-control" value='${param.txtDescription}' id="description" placeholder="Description">
                        </div>
                        <p>${requestScope.FOOD_ERROR.descriptionError}</p>
                        <div class="form-group col-md-6">
                            <label for="price">Price:</label>
                            <input type="number"  name ='txtPrice'class="form-control" value='${param.txtPrice}' id="price" placeholder="Price">
                        </div>
                        <p>${requestScope.FOOD_ERROR.priceError}</p>
                        <div >
                            <label for="txImage">Image:</label>
                            <input type="file"  name='txtImage'  onchange="change(this)"    id="txtImage" >
                            <img  src='' id="myimage"/>
                        </div>
                        <p>${requestScope.FOOD_ERROR.imageError}</p>
                        <div class="form-group col-md-6">
                            <c:if test='${sessionScope.CATEGORY_LIST !=null}'>
                                <select name="txtCategory" >
                                    <c:forEach items='${sessionScope.CATEGORY_LIST}' var="item" varStatus="counter">
                                        <option value="${item.categoryId}">${item.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                        </div>
                        <p>${requestScope.FOOD_ERROR.categoryIdError}</p>
                        <input type='submit' class='btn btn-primary col-md-1' name='btnAction' value='Add'/>
                    </form>
                </div>
            </c:if>
        </c:if>
        <script>
            function change(input) {
                document.getElementById('myimage').src = input.value;
                let file = element.files[0];
                let reader = new FileReader();
                reader.onloadend = function () {
                    document.write('RESULT: ', reader.result);
                }
                console.log(reader.readAsDataURL(file));
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
