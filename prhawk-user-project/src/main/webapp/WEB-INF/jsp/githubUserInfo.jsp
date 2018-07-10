<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<style>
		table, th, td {
   			border: 1px solid black;
		}
	</style>
</head>
<body>
<table id="repoTable"><thead><tr><th>Reposotory</th><th>Pull Times</th></tr></thead><tbody>
 <c:forEach var="item" items="${items}">
         <tr>
           <td >
        	  <a target="_blank"  href="${item.url}">${item.repoName}</a>
          </td>
            <td>${item.pullTimes}</td>
        </tr> 
 </c:forEach>
 </tbody>
 </table>
</body>
</html>