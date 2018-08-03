showBoard();

//function addTitle(username){		
	//$(`<h3 id="username">${username}</h3>
            //<img src="img/user-icon-placeholder.png" id="img-user" alt="user">
    //`).insertBefore( ".b" );
//}

var column = `
	<div class="board">
		<div class="b-header"><input id="board_name" type="text" class="form-control" placeholder="Board name"/>
        <a class="a-board" title="Add" data-toggle="tooltip"><button type="button" onclick="newBoard();" onclick="crear_tarjeta(this);" class="btn">CREAR TABLERO</button></a>
        <button type="button" class="btn btn-d"><img src="img/ic_highlight_off_black_36dp.png"></button>
		</div>
	</div>
`;

//Add board
function add_board() { 
	$(column).insertBefore( $("#card-crear").parent());
}
$(document).ready(function(){
	//Delete board
	$(document).on("click", ".btn-d", function() {
		$(this).parents(".board").remove();
	});
	//modals
	 $("#modal-btn").click(function(){
	     $("#myModal").modal();
	 });
	 $("#myModal").on('hidden.bs.modal',function(e){
		 location.reload();
	     $("#myModal").show();
	 });
	 $("#modal-btn1").click(function(){
	     $("#myModal1").modal();
	 });
	 $("#myModal1").on('hidden.bs.modal',function(e){
		 location.reload();
	     $("#myModal1").show();
	 });
	 $(document).on("click", "#d-btn", function() {
		var removeLI =document.querySelectorAll("#li-m");
        $(removeLI).remove();
        $("#d-btn").hide();
        $("#s-btn").show();
	});
	 $(document).on("click", "#s-btn", function() {
		$("#empty").remove();
		$("#li-m").remove();
	});
});

function edit (button) {
	var container = $(button).parents(".card-header");
	container.find("a").hide();
	container.find("input").show();
}

function edit_final (button) {
	var container = $(button).parents(".card-header");
	var name = container.find("input").hide().val();
	container.find("a").show();
	container.find(".text h5").text(name);
	console.log(name);
}

function crear_tarjeta(boards) {
	console.log(boards)
	for (var i=0; i<boards.length; i++){
		var id = boards[i][0];
		console.log(boards[i][0]);
		$(`
		<div class="board created-board">
			<div class="card-header text-center">
				<a href="boards.html?id=${id}" class="text"><h5>${boards[i][1]}</h5></a> 
				<input type="text" value="${boards[i][1]}" style="display:none;" id="${boards[i][0]}" />
				<button type="button" onclick="deleteBoard(${id});" class="delete-board"><img src="img/ic_delete_black_36dp.png" alt="logo" style="width: 25px;"></button>
				<button type="button" class="edit-board" title="Edit" data-toggle="tooltip" onclick="edit(this);"><img src="img/ic_mode_edit_black_36dp.png" alt="logo" style="width: 25px;"></button>
				<button class="a-card btn" type="button" title="Add Card" onclick="edit_final(this); updateBoard(${boards[i][0]})" data-toggle="tooltip"><img src="img/ic_check_circle_black_36dp.png" alt="logo" style="width: 25px;"></button>
			</div>
		</div>	
	`).insertBefore("#card-deck");
	}  
}