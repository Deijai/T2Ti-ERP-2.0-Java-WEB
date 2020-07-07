/*
* The MIT License
* 
* Copyright: Copyright (C) 2014 T2Ti.COM
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
* 
* The author may be contacted at: t2ti.com@gmail.com
*
* @author Claudio de Barros (T2Ti.com)
* @version 2.0
*/
package com.t2tierp.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@SessionScoped
public class FuncoesController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TreeNode rootEscritaFiscal;
	private TreeNode funcaoSelecionada;
	private String pagina;
	private String tituloJanela;
	private boolean janelaVisivel = false;
	
	public FuncoesController() {  
        rootEscritaFiscal = new DefaultTreeNode("root", null);  
        
        TreeNode escritaFiscal = new DefaultTreeNode(new Funcao("Escrita Fiscal", null), rootEscritaFiscal);
        
        TreeNode cadastros = new DefaultTreeNode(new Funcao("Cadastros", null), escritaFiscal);
        new DefaultTreeNode("document", new Funcao("Registro Cart�rio", "/modulos/contabilidade/registroCartorio.jsf"), cadastros);
        new DefaultTreeNode("document", new Funcao("Par�metros", "/modulos/escrita-fiscal/fiscalParametro.jsf"), cadastros);
        new DefaultTreeNode("document", new Funcao("Tipo Nota Fiscal", "/modulos/vendas/notaFiscalTipo.jsf"), cadastros);
        new DefaultTreeNode("document", new Funcao("Tabela Simples Nacional", "/modulos/escrita-fiscal/simplesNacionalCabecalho.jsf"), cadastros);
        new DefaultTreeNode("document", new Funcao("Livros e Termos", "/modulos/escrita-fiscal/fiscalLivro.jsf"), cadastros);
        
        TreeNode movimento = new DefaultTreeNode(new Funcao("Movimento", null), escritaFiscal);
        new DefaultTreeNode("document", new Funcao("Registro Entradas", "/modulos/controle-estoque/entradaNotaFiscal.jsf"), movimento);
        new DefaultTreeNode("document", new Funcao("Registro Sa�das", "/modulos/controle-estoque/entradaNotaFiscal.jsf"), movimento);
        new DefaultTreeNode("document", new Funcao("Apura��o", "/modulos/escrita-fiscal/fiscalApuracaoIcms.jsf"), movimento);
        new DefaultTreeNode("document", new Funcao("Informativos e Guias", "/modulos/escrita-fiscal/informativosGuias.jsf"), movimento);
        
    }  
  
    public TreeNode getRootEscritaFiscal() {  
        return rootEscritaFiscal;  
    }
    
	public void onNodeSelect(NodeSelectEvent event) {
    	pagina = ((Funcao) event.getTreeNode().getData()).getPagina();
    	tituloJanela = ((Funcao) event.getTreeNode().getData()).getNome();
    	janelaVisivel = true;
    }      

    public TreeNode getFuncaoSelecionada() {
		return funcaoSelecionada;
	}

	public void setFuncaoSelecionada(TreeNode funcaoSelecionada) {
		this.funcaoSelecionada = funcaoSelecionada;
	}

	public String getPagina() {
		return pagina;
	}

	public String getTituloJanela() {
		return tituloJanela;
	}

	public boolean isJanelaVisivel() {
		return janelaVisivel;
	}
	
}
