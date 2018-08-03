	var boards;
	var columns;
	var cards;
	var comments;
	var desType;
	var date= new Date();
	
	function signup(){
		let data = {
				username: $('#username').val(),
				password: $('#password').val(),
				email: $('#email').val(),
				name: $('#name').val(),
				lastname: $('#lastname').val()
		}
		console.log(data);
		if(data.email==""|| data.password==""|| data.username==""){
			alert("Campos incompletos");
        }else {
        	
			fetching(data, 'POST', './Signup'). then((data) => {
                console.log(data);
                if(data.status==200){
                	window.location.href="signin.html"
                }else{
                	alert(data.response);
                }
                
            });
        }
	}
	
	function logout(){
        fetching({}, 'POST', './Logout').then((data) => {
                console.log(data);
                alert(data.response);
                window.location.href="index.html"
            });
        }
	
	
	function login(){
		let data = {
				email: $('#email').val(),
				password: $('#password').val(),
		}
        fetching(data, 'POST', './Login').then ((data) => {
                console.log(data);
                if(data.status==200){
                	window.location.href="home.html"
                }else{
                	alert(data.response);
                }
            });
	}
	
//----- CRUD BOARDS-------------
	
	function newBoard(){
		let data = {
				board_name: $('#board_name').val(),
		}
		console.log(data);
		if(data.board_name==""){
			alert("Inserte Título");
        }else {
        fetching(data, 'POST', './Board').then((data) => {
                console.log(data);
                if(data.status==200){
                	var url = "boards.html?id="+data.board_id;
                	window.location.href=url;
                }else{
                	alert(data.response);
                }
            })
        }
	}
        
    function deleteBoard(id){
    	let data = {
				board_id: id,
		}
        fetching(data, 'DELETE', './Board').then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
                
            })
	}
    
    function updateBoard(board_id){
    	var string = '#'+board_id
		let data = {
               board_id: board_id,
               new_board_name: $(string).val(),
		}
        fetching(data, 'PUT', './Board').then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
            })
	}
    
    function showBoard(){
    	url = './Board?board_name='+ $('#search').val();
        fetching({}, 'GET', url). then((data) => {
        		boards=data.boards;
                if(data.status==200){
                	if ($('#search').val()==""){ 
                	console.log(boards);           
                	crear_tarjeta(boards);
                	}else{
                		console.log(boards);
                		//AQUI
                		for (var i=0; i<boards.length; i++){               
                			var id = boards[i];
                			var id = boards[i][0];
                			$(` 
                    			    <li id="li-m"><a href="boards.html?id=${id}" class="text"><h4>${boards[i][3]}</h4></a>
                    					<p>@${boards[i][1]}</p>
                    			    </li>                				
                    			`).appendTo(".list");     			
                		}
                	}
                	if(boards.length === 0){
                		$("#li-m").replaceWith($(` <h4 id="empty"><b>${$('#search').val()}</b> does not exist</h4>               				
                			`).appendTo(".list")); 
                	}
                }else{
                	
                	alert(data.response);
                }
            })
	}
    
 //-----CRUD COLUMNS------   
    
    function newColumn(id){
    	url = './Column?board_id='+ id
		let data = {
				board_id: id,
	            column_name: $('#column_name').val(),
		}
		console.log($('#column_name').val())
        fetching(data, 'POST', url).then((data) => {
                console.log(data);
                if(data.status==200){
                	crear_columna(data.column_id, $('#column_name').val());  
               }else{
               	alert(data.response);
               }                
            })
	}
    
    function updateColumn(column_id, board_id){
    	var string = '#'+column_id+'1'
    	url = './Column?board_id='+ board_id + '&column_id='+ column_id
    	console.log($(string).val());
		let data = {               
               column_id: column_id,
               new_column_name: $(string).val(),
		}
    	console.log(data)
        fetching(data, 'PUT', url).then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
              
            })
	}
    
    function deleteColumn(id, board_id){
    	url = './Column?board_id='+ board_id+'&column_id='+ id
		let data = {
               column_id: id
		}
        fetching(data, 'DELETE', url).then((data) => {
                console.log(data);
                if(data.status!=200){
                	alert(data.response);
                }else {
                var string = "#"+id
        		$(string).remove();
                }
            })
	}
    
    function showColumn(id){
    	url = './Column?id='+ id
        fetching({}, 'GET', url).then((data) => {
        		columns = data.columns
                console.log(columns);
                if(data.status==200){
                	addTitle(columns [0][2]);
                	showCards(id);  
               }else{
               		alert(data.response);
               }        		
              
            })
	}
  
   //------CRUD CARDS-------------
    function newCard(id, board_id){
    	url = './Card?board_id='+ board_id
		let data = {
				column_id: id,
	            card_name: $('#card_name').val(),
	            card_description: $('#card_description').val(),
		}
		console.log(data)
        fetching(data, 'POST', url).then((data) => {
                console.log(data);
                if(data.status==200){
                	crear_tarjeta(id, data.card_id,$('#card_name').val(), $('#card_description').val())
                    $('.temp').hide(); 
               }else{
               		alert(data.response);
               }                 
            })
	}
    
    function updateCard(id, new_id, board_id){
    	url = './Card?board_id='+ board_id + '&card_id='+ id
    	var card_name = '.'+id+'1';
    	var card_description = '#'+id+'1';
    	
		let data = {
			   card_id : id,
               new_column_id :new_id,
               new_card_name: $(card_name).val(),
               new_card_description: $(card_description).val(),
		}
		console.log(card_name+card_description)
		console.log(data)
        fetching(data, 'PUT', url).then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
            })
	}
    
    function deleteCard(card_id, board_id){
    	url = './Card?board_id='+ board_id +'&card_id='+ card_id
		let data = {
	            card_id: card_id,
		}
        fetching(data, 'DELETE', url).then((data) => {
                console.log(data);
                if(data.status!=200){
                	alert(data.response);
        		}else {
                var string = "#"+card_id
    			$(string).remove();
        		}
            })
	}
    
    function showCards(id){
    	url = './Card?id='+ id
        fetching({}, 'GET', url ).then((data) => {
        		cards = data.cards
                console.log(cards);
                if(data.status==200){
                	for (var i=0; i<columns.length; i++){
                    	crear_columna(columns[i][0], columns[i][1]);
                        for(var j = 0; j<cards.length;j++){
        					if (cards[j][1]==columns[i][0])
        						crear_tarjeta(cards[j][1], cards[j][2],cards[j][3], cards[j][4]);        					
        				}
                    }
               }else{
               		alert(data.response);
               } 
                
            })
	}
    
  //-------- CRUD COLABORATOR ----------
    
    function newColab(board_id, username, type){
    	url = './UserBoard?board_id='+ board_id
		let data = {
				board_id: board_id,
	            username: username,
	            type: type
		}
		console.log(data)
        fetching(data, 'POST', url).then((data) => {
                console.log(data);
                if(data.status==200){
                	//data.user_id, username   ,board_id   , 2          , "Collaborator"
                	//colab[i][1] ,colab[i][3],colab[i][0], colab[i][2], desType
                	crear_colab(data.user_id, data.username, board_id, 2, "Collaborator");
               }else{  
            	   alert(data.response);
               }                 
            })
	}
    
    function updateColaborator(board_id, user_id, type){
    	url = './UserBoard?board_id='+ board_id
    	let data = {
				board_id: board_id,
	            user_id: user_id,
	            newType: type
		}  
    	console.log(data)
        fetching(data, 'PUT', url).then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
            })
	}
    
    function deleteColaborator(board_id, user_id){
    	console.log(board_id);
    	url = './UserBoard?board_id='+ board_id
    	let data = {
				board_id: board_id,
	            user_id: user_id
		}
    	console.log(data)
        fetching(data, 'DELETE', url).then((data) => {
                console.log(data);
                if(data.status!=200){
                	
                }else{
                	console.log(user_id)
                	var string = "#"+user_id
    				$(string).remove();
                	alert(data.response);
                }                	
            })
	}
    
    function showColab(id){
    	url = './UserBoard?id='+ id
        fetching({}, 'GET', url ).then((data) => {
        		colab = data.colab
                console.log(colab);
                if(data.status==200){
                	for (var i=1; i<colab.length; i++){
	                	if(colab[i][2]==1){
	                		desType = "Board Master"
	                	}else{
	                		desType = "Collaborator"
	                	}
                	console.log(colab[i][1]);
                	crear_colab(colab[i][1],colab[i][3],colab[i][0], colab[i][2], desType);
                	
                	}
               }else{
               		alert(data.response);      
               } 
                
            })
	}
    
    //------CRUD COMMENT-------------
    
    function newComment(id, board_id){
    	url = './Comment?board_id='+ board_id
		let data = {
				card_id: id,
				comment_text: $('#comments').val()
		}
		console.log(data)
        fetching(data, 'POST', url ).then((data) => {
                console.log(data);
                if(data.status==200){  
                	console.log(id, $('#comments').val());
                	crear_comments(data.comment_id, id, $('#comments').val(), data.username, new Date().toISOString().slice(0,10));
               }else{
               		alert(data.response);
               }                 
            })
	}
    
    function updateComment(id, board_id){
    	url = './Comment?board_id='+ board_id + '&comment_id='+ id
    	var string = '#'+ id+1;
		let data = {
			   comment_id: id,
			   new_comment: $(string).val()
		}
		console.log(data)
        fetching(data, 'PUT', url ).then((data) => {
                console.log(data);
                if(data.status!=200)
                	alert(data.response);
            })
	}
    
    function deleteComment(comment_id, board_id){
    	url = './Comment?board_id='+ board_id + '&comment_id='+ comment_id
		let data = {
				comment_id : comment_id,
		}
        fetching(data, 'DELETE', url ).then((data) => {
                console.log(data);
                if(data.status!=200){
                	alert(data.response);
        		}else {
                var string = "#"+comment_id
    			$(string).remove();
        		}
            })
	}
    
    function showComment(id){
    	url = './Comment?id='+ id
        fetching({}, 'GET', url ).then((data) => {
        		comments = data.comments
        		console.log(comments);
        		if(data.status==200){
                	for (var i=0; i<comments.length; i++){
                	console.log(comments[i][0],comments[i][2]);
                	crear_comments(comments[i][0],comments[i][1], comments[i][3], comments[i][6], comments[i][4], comments[i][5]);             	
                	}
               }else{
               		alert(data.response);      
               } 
            })
	}

    //------ CRUD FILES -------------
    function newFile( board_id, card_id){
    	url = './Files?board_id='+ board_id + '&card_id='+ card_id
    	console.log(url)
    	var formData = new FormData();
    	var files = document.getElementById("files").files;
    	console.log(files.length);
    	for (var i = 0; i < files.length; i++) {
    		  var file = files[i];
    		  formData.append('files[]', file, file.name);
    	}
    	let datajson = {
                method: 'POST',
                body: formData,
                withCredentials: true,
                credentials: 'include',
                contentType: false,
            };
            fetch(url, datajson)
            .then((res) => res.json())
            .then((data) => {
            	console.log(data)
                var files_id = data.files_id;
            	var files_name = data.files_name;
                if(data.status==200){
                	for (var i=0; i<files_id.length; i++){
                		crear_files(files_id[i], card_id, date.getHours()+':'+date.getMinutes(), files_name[i], data.username);
                	}
               }else{
               		alert(data.response)
               } 
            })
            .catch((err) => {
                console.log(err);
            })
    }
    
    function deleteFile(file_id, board_id, file_name){
    	url = './Files?board_id='+ board_id +'&file_id='+ file_id
		let data = {
	            file_id: file_id,
	            file_name: file_name
		}
        fetching(data, 'DELETE', url).then((data) => {
                console.log(data)
                if(data.status!=200){
                	alert(data.response)
        		}else {
        			var string = "#"+file_id
        			$(string).remove();
        		}
            })
	}
    
    function showFiles(id){
    	url = './Files?id='+ id
        fetching({}, 'GET', url ).then((data) => {
        		files = data.files
                console.log(files);
                if(data.status==200){
                	for (var i=0; i<files.length; i++){
                    	crear_files(files[i][0], files[i][1], files[i][2], files[i][3], files[i][4]);
                    }
               }else{
               		alert(data.response);
               } 
                
            })
	}
    
    function download (file_name) {
    		url = "./GetFile?name="+file_name;
    		console.log(url);
    		var downloadWindow = window.open(url);
    }

    //------------ADMIN-----------------
    
   /* function showUser(){
    	//$('#search-user').val();
        //-------
            $(` <li id="li-user"><h4>${username}</h4></li>                				
               `).appendTo(".list1");     			
        //-------- if length == 0 {
           $("#li-m").replaceWith($(`<h4 id="empty"><b>${$('#search-user').val()}</b> does not exist</h4>               				
               `).appendTo(".list1")); }
	} */
    
