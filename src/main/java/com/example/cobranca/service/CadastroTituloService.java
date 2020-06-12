package com.example.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.cobranca.model.StatusTitulo;
import com.example.cobranca.model.Titulo;
import com.example.cobranca.model.TituloFilter;
import com.example.cobranca.repository.Titulos;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		
		try {
			titulos.save(titulo);
		}catch(DataIntegrityViolationException ex) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void delete(Long codigo) {
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		Titulo tit = titulos.findOne(codigo);
		tit.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(tit);
		
		return tit.getStatus().getDescricao();
	}

	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao()==null?"%":filtro.getDescricao();
		// List<Titulo> titulos = this.titulos.findAll();
		return this.titulos.findByDescricaoContaining(descricao);
	}
}
