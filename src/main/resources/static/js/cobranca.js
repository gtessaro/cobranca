$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	var codigoTitulo = button.data('codigo');
	var descricao = button.data('descricao');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	
	if(!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action+codigoTitulo);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o título <strong>'+descricao+'</strong>?');
	
});

$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal: ',',thousands: '.', allowZero: false});
	
	$('.js-atualizar-status').on('click', function(event){
		event.preventDefault();
		
		var button = $(event.currentTarget);
		var url	   = button.attr('href');
		
		var response = $.ajax({
			url: url,
			type: 'PUT'
		});
		
		response.done(function(e){
			var codigo = button.data('codigo');
			$('[data-role='+codigo+']').html('<span class="label label-success">'+e+'</span>');			
			
			button.hide();
		});
		
		response.fail(function(e){
			alert('Erro ao receber cobrança!');
		});
		
	});
});