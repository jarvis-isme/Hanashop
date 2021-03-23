<%-- 
Document   : admin
Created on : Jan 5, 2021, 1:05:04 PM
Author     : nguye
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <c:if test="${ sessionScope.USER == null}"> 
            <a href="MainController?btnAction=ShowLoginPage" >Your role is not valid , click to Login</a>
        </c:if>
        <c:if test="${sessionScope.USER != null}">
            <c:if test="${ ! sessionScope.USER.roleId  eq 'AD' }"> 
                <a href="MainController?btnAction=ShowLoginPage" >Your role is not valid , click to Login</a>
            </c:if>
            <c:if test="${ sessionScope.USER.roleId eq 'AD'}">
                <c:import url="./navbaradmin.jsp"/>
                <h2>Food Management</h2>
                <c:if test="${requestScope.UPDATE_ERROR !=null}">
                    <p>Update unsuccessfully</p>
                    <h2>Error Update</h2>
                    <p color="red">
                        FoodIdError: ${requestScope.UPDATE_ERROR.foodIdError} <hr/>
                        foodNameError: ${requestScope.UPDATE_ERROR.foodNameError},<hr/>
                        DescriptionError: ${requestScope.UPDATE_ERROR.descriptionError},<hr/>
                        QuantityError:${requestScope.UPDATE_ERROR.quantityError},<hr/>
                        PriceError: ${requestScope.UPDATE_ERROR.priceError}<hr/>    
                        ImageError:${requestScope.UPDATE_ERROR.imageError}
                    </p>
                </c:if>
                <c:if test="${sessionScope.FOOD_LIST_ADMIN ==null}">
                    <a href="MainController?btnAction=ShowManageFoodPage&index=1" class="btn btn-danger">Get All</a>
                </c:if>

                <c:if test="${sessionScope.FOOD_LIST_ADMIN !=null}">

                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Food Id</th>
                                <th>Food Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Image</th>
                                <th>Created Date</th>
                                <th>Expired Date</th>
                                <th>Description</th>
                                <th>Category</th>
                                <th>isDeletd</th>
                                <th>Update</th>
                                <th>Selected</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach varStatus="counter" var='item' items="${sessionScope.FOOD_LIST_ADMIN}" >
                            <form aciton="MainController"  >
                                <tr>
                                    <td>${counter.count}</td>                
                                    <td>
                                        <input type='text' name='txtFoodId' readonly='true' value='${item.foodId}'/>

                                    </td>
                                    <td>
                                        <input type='text' name='txtFoodName'   value='${item.foodName}'/>
                                    </td>
                                    <td>
                                        <input type='number' name='txtQuantity'   value='${item.quantity}'/>
                                    </td>
                                    <td>
                                        <input type='number' name='txtPrice'   value='${item.price}'/>
                                    </td>
                                    <td>
                                        <img    id="${counter.count}image" name="image" width='50px' src="${item.image}"/>
                                        <input type='file' id="${counter.count}fi" onchange='changeImg(${counter.count}, this)'   name="chane" value='${item.image}'/>
                                        <input type='hidden' id="${counter.count}" name='txtImage' value='${item.image}'/>
                                    </td>
                                    <td>  
                                        ${item.createdDate}
                                    </td>
                                    <td>
                                        ${item.expiredDate}
                                    </td>
                                    <td>
                                        <input type="text" name="txtDescription" onchange="test(this)" value="${item.description}"/>
                                    </td>
                                    <td>   
                                        <c:if test="${sessionScope.CATEGORY_LIST==null}"> 
                                            null
                                        </c:if>
                                        <c:if test="${sessionScope.CATEGORY_LIST !=null}"> 
                                            <select name="txtCategory">
                                                <c:forEach items="${sessionScope.CATEGORY_LIST}" var="cate">
                                                    <c:if test="${cate.categoryId eq item.categoryId}">
                                                        <option selected value="${cate.categoryId}" >${cate.categoryName}</option>
                                                    </c:if>
                                                    <c:if test="${! cate.categoryId eq item.categoryId}">   
                                                        <option selected value="${cate.categoryId}" >${cate.categoryName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>   
                                    <td>
                                        <select name="txtIsDeleted">
                                            <c:if test="${item.isDeleted==true}">
                                                <option selected="true" value="True">True</option>
                                                <option value="False">False</option>
                                            </c:if>
                                            <c:if test="${item.isDeleted== false}">
                                                <option  value="True">True</option>
                                                <option selected="true" value="False">False</option>
                                            </c:if>
                                        </select>
                                    </td>
                                    <td>
                                        <input type='submit' class='btn btn-primary' name='btnAction' value='Update Food'/>
                                    </td>
                            </form>
                            <c:if test="${item.isDeleted == true }">
                                <td></td>
                            </c:if>
                            <c:if test="${item.isDeleted == false}">
                                <td><input type='checkbox' name='btnChecked' onclick="handleClick(this)" value='${item.foodId}'/> </td>
                                </c:if>  
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <c:forEach begin='1' var='count' end="${sessionScope.COUNT_PAGE_ADMIN}">
                    <form action="MainController">
                        <input type="hidden" name="index" value="${count}"> 
                        <button type="submit" name="btnAction" value="ShowManageFoodPage">${count}</button>
                    </form>
                </c:forEach>
            </div>
            <form aciton="MainController" id='form'method="POST">
                <input type="hidden"  id='hidden1'value="" name="listFoodId"/>
                <button   value="Delete Selected" onclick="handleDeleteClick()" name='btnAction'>Delete Selected</button>
            </form>
        </c:if>
    </c:if>
</c:if>
<script>

    function handleDeleteClick() {
        var hiddenValue = document.getElementById('hidden1');
        console.log(hiddenValue.value);
        if (hiddenValue.value == '') {
            alert('You did not select anyone');
            return false;
        } else {
            option = confirm("Do you really delete items");
            if (option === true) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    function  handleClick(input) {
        var hiddenValue = document.getElementById('hidden1');
        hiddenValue.value = '';
        var btnCheck = document.getElementsByName('btnChecked');
        for (i = 0; i < btnCheck.length; i++) {
            if (btnCheck[i].checked == true) {
                hiddenValue.value += btnChecked[i].value + ";";
            }
        }
        console.log('HIdden:' + hiddenValue.value);
    }
    function changeImg(id, input) {
        var newValue = input.value;
        if (newValue != ' ') {
            document.getElementById(id + 'image').src = newValue;
            document.getElementById('' + id).value = newValue;
        } else {
            console.log('Empty');
        }
    }
</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
