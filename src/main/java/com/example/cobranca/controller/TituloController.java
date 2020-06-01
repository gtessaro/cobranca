package com.example.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cobranca.model.StatusTitulo;
import com.example.cobranca.model.Titulo;
import com.example.cobranca.repository.Titulos;
import com.example.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping({"/titulo","/titulo/"})
public class TituloController {

	@Autowired
	private Titulos titulos;
	
	@Autowired
	private CadastroTituloService service;
	
	private static String CADASTRO_VIEW = "CadastroTitulo";
	
	@RequestMapping({"/novo","/novo/"})
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
			service.salvar(titulo);
			
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/titulo/novo";
		}catch(DataIntegrityViolationException ex) {
			errors.rejectValue("dataVencimento", null, ex.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@ModelAttribute("statusTitulo")
	public List<StatusTitulo> statusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	
	@RequestMapping
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		List<Titulo> titulos = this.titulos.findAll();
		mv.addObject("titulos", titulos);
		
		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
//		Titulo titulo = titulos.findOne(codigo);
		
		mv.addObject(titulo);
		
		return mv;
	}
	
//	@RequestMapping("/delete/{codigo}")
//	public ModelAndView deletar(@PathVariable Long codigo) {
//		ModelAndView mv = new ModelAndView("PesquisaTitulos");
//		
//		titulos.delete(codigo);
//
//		List<Titulo> titulos = this.titulos.findAll();
//		mv.addObject("titulos", titulos);
//		
//		return mv;
//	}
	
	@RequestMapping(value="{codigo}",method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		
		service.delete(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		
		return "redirect:/titulo";
	}
	
}
