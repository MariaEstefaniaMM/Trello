 				
		var url = new URL(window.location.href);
		var id = url.searchParams.get("id");
		var current_column_id;
		var idcard;
		console.log(id);
		showColumn(id);
		
		function addTitle(board_name){		
			$(`<h2 id="board-name" class="text">${board_name}</h2>`).appendTo( ".navbar" );
		}
		
		function addColab(){
			newColab(id, $('#colab').val(), 2);
		}
		
		function addComments(idcard){
			newComment(idcard, id);
		}
		
		function showModal(id_card){
			idcard = id_card;
			$("#myModal2").modal();
			showComment(idcard);
		}
		function showModal3(id_card){
			idcard = id_card;
			 $("#myModal3").modal();
			 showFiles(idcard);
		}

		function upFile(){
			newFile(id, idcard)
		}
		
			var draging = null,
			count_columns = 2,
			column_width = 252;
		
		var current_card;
			
			function allowDrop(ev) { ev.preventDefault(); }
			
			function drag(ev, card_id) { draging = ev.target; current_card = card_id}
			
			function drop(ev, column_id) {
				ev.preventDefault();
				var target = $(ev.target);
				if ( !target.is(".column") )
					target = target.parents(".column")
					console.log(target);
					updateCard(current_card, column_id, id);
					$(draging).insertBefore( target.find(".add-card") );
			}
			
			function deshabilitarN(boton){
				var textarea = $(boton).siblings()[0];
				textarea.disabled="true"
			}
			function deshabilitarN1(boton){
				var textarea = $(boton).siblings()[0];
				textarea.disabled="true";
				var container = $(boton).parents(".card");
				var name = container.find("input").attr("disabled", true);
			}
			function deshabilitarN2(boton){
				var textarea = $(boton).siblings()[0];
				textarea.disabled="true";
				var container = $(boton).parents(".list-c");
				var name = container.find("textarea").attr("disabled", true);
			}
			function habilitarN(boton){
			 	var input = $(boton).siblings()[0];
			 	input.disabled=false;
			}

			function habilitarN2(button){
				var textarea = $(button).siblings()[0];
				var container = $(button).parents(".card");
				var name = container.find("input").attr("disabled", false);
				textarea.disabled=false;	

			}
			function habilitarN1(button){
				var textarea = $(button).siblings()[0];
				var container = $(button).parents(".list-c");
				var name = container.find("textarea").attr("disabled", false);
				textarea.disabled=false;	
			}
			function add_tarjeta(btn, column_id) { 
				current_column_id =column_id;
				console.log(current_column_id);
				$(`
						<div class="card temp" draggable="true" ondragstart="drag(event)">
						<div class="column-header"><input type="text" id="card_name" class="form-control" placeholder="Titulo" /></div>
							<div class="card-body">
								<textarea id="card_description" class="form-control" type="text" name="card" placeholder="Write something"></textarea>
								<a class="a-card" title="Add Card" data-toggle="tooltip" onclick="deshabilitarN(this); newCard(${column_id}, ${id})"><button type="button" class="btn btn-c">add card</button></a>
								<button type="button" class="btn delete-card"><img src="img/ic_highlight_off_black_36dp.png" style="width:25px"></button>
							</div>
						</div>
						
					`).insertBefore( btn ); 
				
			}
			function add_column() { 
				newColumn(id)
			}
			
			//popover
			//$(document).on("click", ".edit-colab", function(){
				//$('[data-toggle="popover"]').popover({
					//container:'body'
				//});
			//});

			$(document).ready(function(){
			//modal
			$("#modal-btn").click(function(){
			    $("#myModal").modal();
			    showColab(id);		    
		     });
			$("#myModal").on('hidden.bs.modal',function(e){
				 location.reload();
			     $("#myModal").show();
			 });
			 $("#myModal2").on('hidden.bs.modal',function(e){
				 location.reload();
			     $("#myModal2").show();
			 });
			 $("#myModal3").on('hidden.bs.modal',function(e){
				 location.reload();
			     $("#myModal3").show();
			 });
			// Edit Card
			$(document).on("click", ".edit-card", function(){
		        $(this).parents(".card").find(".card_name:not(:last-child)").each(function(){
					$(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
				});		

		    });
		});
			function crear_columna(column_id, name) {
				count_columns++;
				$("#card-deck").width( count_columns * column_width  );
				$(`					
					<div class="column" id="${column_id}" ondrop="drop(event, ${column_id})" ondragover="allowDrop(event)" id="created-column">
						<div class="card-header" id="created-header">
							<input class="form-control i-header" id="${column_id}1" type="text" disabled="disabled" value="${name}"></input>
							<button class="delete-column btn" type="button" title="Delete Column" data-toggle="tooltip" onclick="deleteColumn(${column_id}, ${id});"><img src="img/ic_delete_white_36dp.png" alt="logo" style="width: 25px;"></button>
							<button class="edit-column btn" type="button" title="Edit" onclick="habilitarN(this);" data-toggle="tooltip"><img src="img/ic_mode_edit_white_36dp.png" alt="logo" style="width: 25px;"></button>
							<button class="a-column btn" type="button" title="Edit Card" data-toggle="tooltip"  onclick="deshabilitarN(this); updateColumn(${column_id}, ${id});"><img src="img/ic_check_circle_white_36dp.png" alt="logo" style="width: 25px;"></button>
						</div>
						<button type="button" onclick="add_tarjeta(this, ${column_id})" id="hide-btn" class="btn add-card ${column_id}" title="Add Card"><img src="img/ic_control_point_black_48dp.png" alt="logo" style="width: 25px;"></button>
					</div>
				`).insertBefore("#add-columns");

			}
			
			function crear_tarjeta(id_column, id_card, card_name, card_description) {
				var string = '.'+id_column
				$(`
					<div class="card" id="${id_card}" draggable="true" ondragstart="drag(event, ${id_card})">
						<div class="column-header"><input type="text" id="${card_name}"class="form-control ${id_card}1" placeholder="Titulo" value="${card_name}" disabled="disabled" /></div>
							<div class="card-body">
								<textarea id="${id_card}1" class="form-control" type="text" name="card" placeholder="Write something" disabled="disabled" style="resize: none;">${card_description}</textarea>
								<button class="delete-card btn" id="hide-btn" type="button" title="Delete Card" data-toggle="tooltip" onclick="deleteCard(${id_card},  ${id});"><img src="img/ic_delete_black_36dp.png" alt="logo" style="width: 25px;"></button>
								<button class="edit-card btn" id="hide-btn" type="button" title="Edit Card" onclick="habilitarN2(this)" data-toggle="tooltip"><img src="img/ic_mode_edit_black_36dp.png" alt="logo" style="width: 25px;"></button>
								<button class="a-card btn" id="hide-btn" type="button" title="Edit Card" data-toggle="tooltip" onclick="deshabilitarN1(this); updateCard(${id_card},${id_column}, ${id})"><img src="img/ic_check_circle_black_36dp.png" alt="logo" style="width: 25px;"></button>
							    <button class="c-card btn" id="comments-btn" type="button" title="Add comments" data-toggle="tooltip" onclick="showModal(${id_card})"><img src="img/ic_sms_black_36dp.png" alt="logo" style="width: 25px;"></button>
							    <button class="file-card btn" id="file-btn" type="button" title="Add file" data-toggle="tooltip" onclick="showModal3(${id_card})"><img src="img/ic_archive_black_36dp.png" alt="logo" style="width: 25px;"></button>
							</div>
						</div>	
					</div>
				`).insertBefore(string);
			}
			
			function crear_colab(user_id, user_username, board_id, type_id, desType){
				$(`<li id="${user_id}">
                  	     <h4>@${user_username}</h4>
                  	     <p id="user_${user_id}">${desType}</p>
                  	     <button class="delete-colab btn" id="delete-colab" type="button" title="Delete Collaborator" data-toggle="tooltip" onclick="deleteColaborator(${id},${user_id});"><img src="img/ic_delete_black_36dp.png" alt="logo" style="width: 20px;"></button>
						 <button class="edit-colab btn" id="edit-colab" type="button" title="Change" data-content="Change to Board Master" onclick="updateC(${type_id}, ${user_id});"><img src="img/ic_group_black_36dp.png" alt="logo" style="width: 23px;">
						 <img id="c-img"src="img/ic_autorenew_black_24dp.png" alt="logo" ></button>
                  	  </li>`).appendTo("#myUl");
			} 
			
			function updateC(type, user_id){
				var string = "#user_"+user_id
				console.log(string)
				if (type==2){
					updateColaborator(id, user_id, 1)
					$(string).text("Board Master");
				}else{
					updateColaborator(id, user_id, 2)
					$(string).text("Collaborator");
				}				
			}
			
			function crear_comments(comment_id, idcard, comment_text, username, create_date, updated_date){
				console.log(create_date + updated_date)
				$(`<li class="list-c" id="${comment_id}">
						 <h5 class="username-c">@${username}<h5/>
				         <textarea class="form-control textarea2" id="${comment_id}1" type="text" disabled="disabled">${comment_text}</textarea>
                  	     <br>
                  	     <h7 class="comment-created">Creado el ${create_date} --- Editado el ${updated_date}<h7/>
                  	     <br>
                  	     <button class="delete-card btn" id="hide-btn" type="button" title="Delete Comment" data-toggle="tooltip" onclick="deleteComment(${comment_id}, ${id})"><img src="img/ic_delete_black_36dp.png" alt="logo" style="width: 25px;"></button>
						 <button class="edit-card btn" id="hide-btn" type="button" title="Edit Comment" onclick="habilitarN1(this)" data-toggle="tooltip"><img src="img/ic_mode_edit_black_36dp.png" alt="logo" style="width: 25px;"></button>
						 <button class="a-card btn" id="hide-btn" type="button" title="Add Comment" data-toggle="tooltip" onclick="deshabilitarN2(this); updateComment(${comment_id}, ${id})"><img src="img/ic_check_circle_black_36dp.png" alt="logo" style="width: 25px;"></button>						    
                  	</li>`).appendTo("#myUl2");
			}
			
			function crear_files(file_id, card_id, file_uploaded_at, file_name, username){
				var name = "'"+file_name+"'"
				$(`<li class="list-c"id="${file_id}">
						 <h5 class="name-f">${file_name}<h5/>
						 <h7 class="comment-created">Uploaded at ${file_uploaded_at} by @${username}<h7/>
						 <button class="delete-file btn" type="button" title="Delete File" data-toggle="tooltip" onclick="deleteFile(${file_id}, ${id}, ${name});"><img src="img/ic_delete_black_36dp.png" alt="logo" style="width: 25px;"></button>
						 <button class="download-file btn" type="button" tittle="Download File" data-toggle="tooltip" onclick="download(${name});"><img src="img/ic_file_download_black_36dp.png" style="width: 25px;"></button>	
						 <hr>					    
                  	</li>`).appendTo("#myUl3");
			}
			
			
			