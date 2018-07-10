  //restful api can use it, now is removed from back end
    function getUserDetail() {
        $.ajax({
          url: "/user/khoubyari",
          type: "GET",
          cache: false,
          success: function (result) {
        	  var tbl=$("<table id='repoTable'><thead><tr><th>Reposotory</th><th>Pull Times</th>");
        	  $("#div1").empty();
        	  $("#div1").append(tbl);
        	  for(var i=0;i<result.length;i++)
        	  {
        	      var tr="<tr>";
        	      var name="<td>"+ "<a target='_blank' rel='noopener noreferrer' href='" + result[i]["html_url"] + "'>" + result[i]["name"]+"</a></td>";
        	      var pullTims="<td>"+result[i]["pullTimes"]+"</td></tr>";
        	      
        	     $("#repoTable").append(tr+name+pullTims); 
        	    
        	  }
          },
          error: function () {
            // Handle upload error
        	  alert("failed");
          }
        });
      }