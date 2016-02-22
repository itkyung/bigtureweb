var searchTable;

$(document).ready(function(){
	
	/**
	 * 각종 버튼의 click이벤트 처리.
	 */
	$("#btnSearch").click(function(){
		doSearch();
	});
	
	var url = _requestPath + "/admin/searchUser";
	
	searchTable = $('#searchResult').DataTable({
		  language: {
			  paginate: {
				  next: "다음",previous : "이전"
			  }
		   },
		   scrollY: 400,
		   searching: false,
		   paging:true,
		   displayLength : 10,
	       lengthChange : true,
	       info : false,
	       dom : '<"top"i>rt<"bottom"lp><"clear">',
		   ajax : {
			   url : url,
			   type : "POST"
		   },
		   columns : [
		       { data : "id", sortable : false},
		       { data : "loginId"},
		       { data : "nickName",width : "20%"},
		       { data : "countryNameKr", width : "10%"},
		       { data : "createStr", width : "10%"},
		       { data : "lastLoginDateStr", width : "15%"},
		       { data : "osType", width : "10%"},
		       { data : "appVersion",width:"10%"},
		       { data : "gender", width : "10%"},
		       { data : "birthDay",width:"10%"}
		   ],
		   processing: true,
		   serverSide : true,
		   columnDefs : [
				{
				    targets : 1,
				    render : function(data,type,row){
				    	return "<img src=\"" + data + "\"/>";
				    }
				},
		       {
		            targets : 2,
		            render : function(data,type,row){
		            	return "<a href='javascript:viewUser(\"" + row.id + "\");'>" + data + "</a>";
		            }
		       }
		   ]
	  }).on('preXhr.dt',function(e,settings,data){
		  //Ajax Call을 하기 전에 호출된다.
		  makeParams(data);
	  }).on("xhr.dt",function(e,settings,json){
		 $("#totalCount").html(json.recordsTotal);  
	  });
	
});


/**
 * 검색조건을 만든다.
 */
makeParams = function(params){
	var keyword = $("#searchKeyword").val();
	var userSearchType = $("#userSearchType").val();
	
	if(keyword.length > 0){
		params.keyword = keyword;
	}else{
		params.keyword = "";
	}
	params.userSearchType = userSearchType;
	
	var verified = $("#verified").val();
	var userSearchRole = $("#userSearchRole").val();
	
	params.verified = verified;
	params.userSearchRole = userSearchRole;
	
	return params;
};


doSearch = function(){
	searchTable.ajax.reload(function(json){
		
	});
};

addUser = function(){
	
};

viewUser= function(id){
	
};
