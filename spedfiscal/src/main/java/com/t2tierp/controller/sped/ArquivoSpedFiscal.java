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
package com.t2tierp.controller.sped;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.t2tierp.controller.sped.fiscal.bloco0.Registro0150;
import com.t2tierp.controller.sped.fiscal.bloco0.Registro0190;
import com.t2tierp.controller.sped.fiscal.bloco0.Registro0200;
import com.t2tierp.controller.sped.fiscal.bloco0.Registro0205;
import com.t2tierp.controller.sped.fiscal.bloco0.Registro0220;
import com.t2tierp.controller.sped.fiscal.bloco0.Registro0400;
import com.t2tierp.controller.sped.fiscal.bloco1.Registro1010;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC100;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC114;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC170;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC190;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC300;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC310;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC320;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC321;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC350;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC370;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC390;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC400;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC405;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC420;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC425;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC460;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC470;
import com.t2tierp.controller.sped.fiscal.blococ.RegistroC490;
import com.t2tierp.controller.sped.fiscal.blocoe.RegistroE100;
import com.t2tierp.controller.sped.fiscal.blocoe.RegistroE110;
import com.t2tierp.controller.sped.fiscal.blocoe.RegistroE116;
import com.t2tierp.controller.sped.fiscal.blocoh.RegistroH005;
import com.t2tierp.controller.sped.fiscal.blocoh.RegistroH010;
import com.t2tierp.model.bean.cadastros.Contador;
import com.t2tierp.model.bean.cadastros.Empresa;
import com.t2tierp.model.bean.cadastros.EmpresaEndereco;
import com.t2tierp.model.bean.cadastros.Produto;
import com.t2tierp.model.bean.cadastros.ProdutoAlteracaoItem;
import com.t2tierp.model.bean.cadastros.UnidadeConversao;
import com.t2tierp.model.bean.cadastros.UnidadeProduto;
import com.t2tierp.model.bean.escritafiscal.FiscalApuracaoIcms;
import com.t2tierp.model.bean.nfe.NfeCabecalho;
import com.t2tierp.model.bean.nfe.NfeCupomFiscalReferenciado;
import com.t2tierp.model.bean.nfe.NfeDestinatario;
import com.t2tierp.model.bean.nfe.NfeDetalhe;
import com.t2tierp.model.bean.nfe.NfeEmitente;
import com.t2tierp.model.bean.nfe.NfeTransporte;
import com.t2tierp.model.bean.pafecf.EcfImpressora;
import com.t2tierp.model.bean.pafecf.EcfNotaFiscalCabecalho;
import com.t2tierp.model.bean.pafecf.EcfR02;
import com.t2tierp.model.bean.pafecf.EcfR03;
import com.t2tierp.model.bean.pafecf.EcfVendaCabecalho;
import com.t2tierp.model.bean.pafecf.EcfVendaDetalhe;
import com.t2tierp.model.bean.sped.ViewSpedC190;
import com.t2tierp.model.bean.sped.ViewSpedC190Id;
import com.t2tierp.model.bean.sped.ViewSpedC300;
import com.t2tierp.model.bean.sped.ViewSpedC300Id;
import com.t2tierp.model.bean.sped.ViewSpedC321;
import com.t2tierp.model.bean.sped.ViewSpedC321Id;
import com.t2tierp.model.bean.sped.ViewSpedC370;
import com.t2tierp.model.bean.sped.ViewSpedC370Id;
import com.t2tierp.model.bean.sped.ViewSpedC390;
import com.t2tierp.model.bean.sped.ViewSpedC390Id;
import com.t2tierp.model.bean.sped.ViewSpedC425;
import com.t2tierp.model.bean.sped.ViewSpedC425Id;
import com.t2tierp.model.bean.sped.ViewSpedC490;
import com.t2tierp.model.bean.sped.ViewSpedC490Id;
import com.t2tierp.model.bean.tributacao.TributOperacaoFiscal;
import com.t2tierp.model.dao.InterfaceDAO;
import com.t2tierp.model.dao.pafecf.EcfNotaFiscalCabecalhoDAO;
import com.t2tierp.model.dao.sped.ViewSpedC425DAO;
import com.t2tierp.util.Biblioteca;

@ManagedBean
@ViewScoped
public class ArquivoSpedFiscal {

	private Date dataInicio;
	private Date dataFim;
	private String versao;
	private String finalidadeArquivo;
	private String perfil;
	private Integer inventario;
	private Integer idContador;
	private SpedFiscal sped;

	@EJB
	private InterfaceDAO<Empresa> empresaDao;
	@EJB
	private InterfaceDAO<NfeCabecalho> nfeDao;
	@EJB
	private InterfaceDAO<EcfNotaFiscalCabecalho> nf2Dao;
	@EJB
	private InterfaceDAO<TributOperacaoFiscal> operacaoFiscalDao;
	@EJB
	private InterfaceDAO<Contador> contadorDao;
	@EJB
	private InterfaceDAO<ProdutoAlteracaoItem> alteracaoItemDao;
	@EJB
	private InterfaceDAO<ViewSpedC190Id> viewC190Dao;
	@EJB
	private InterfaceDAO<ViewSpedC370Id> viewC370Dao;
	@EJB
	private InterfaceDAO<ViewSpedC390Id> viewC390Dao;
	@EJB
	private InterfaceDAO<ViewSpedC300Id> viewC300Dao;
	@EJB
	private EcfNotaFiscalCabecalhoDAO ecfNotaFiscalCabecalhoDao;
	@EJB
	private InterfaceDAO<ViewSpedC321Id> viewC321Dao;
	@EJB
	private InterfaceDAO<EcfImpressora> ecfImpressoraDao;
	@EJB
	private InterfaceDAO<EcfR02> ecfR02Dao;
	@EJB
	private InterfaceDAO<EcfR03> ecfR03Dao;
	@EJB
	private ViewSpedC425DAO viewC425Dao;
	@EJB
	private InterfaceDAO<EcfVendaCabecalho> ecfVendaCabecalhoDao;
	@EJB
	private InterfaceDAO<EcfVendaDetalhe> ecfVendaDetalheDao;
	@EJB
	private InterfaceDAO<Produto> produtoDao;
	@EJB
	private InterfaceDAO<ViewSpedC490Id> viewC490Dao;
	@EJB
	private InterfaceDAO<FiscalApuracaoIcms> apuracaoIcmsDao;

	public File geraArquivo(String versao, String finalidadeArquivo, String perfil, Integer inventario, Date dataInicial, Date dataFinal, Integer idContador) throws Exception {
		this.dataInicio = dataInicial;
		this.dataFim = dataFinal;
		this.versao = versao;
		this.finalidadeArquivo = finalidadeArquivo;
		this.perfil = perfil;
		this.inventario = inventario;
		this.sped = new SpedFiscal();
		this.idContador = idContador;

		geraBloco0();
		geraBlocoC();
		// BLOCO D: DOCUMENTOS FISCAIS II - SERVI�OS (ICMS).
		// Bloco de registros dos dados relativos � emiss�o ou ao recebimento de
		// documentos fiscais que acobertam as presta��es de servi�os de
		// comunica��o, transporte intermunicipal e interestadual.
		// Implementado a crit�rio do Participante do T2Ti ERP
		geraBlocoE();
		// BLOCO G � CONTROLE DO CR�DITO DE ICMS DO ATIVO PERMANENTE CIAP
		// Implementado a crit�rio do Participante do T2Ti ERP
		if (inventario > 0) {
			geraBlocoH();
		}
		geraBloco1();

		File file = File.createTempFile("spedfiscal", ".txt");
		file.deleteOnExit();

		sped.geraArquivoTxt(file);

		return file;
	}

	// BLOCO 0: ABERTURA, IDENTIFICA��O E REFER�NCIAS
	private void geraBloco0() throws Exception {
		Empresa empresa = empresaDao.getBean(1, Empresa.class);
		empresa = (Empresa) Biblioteca.nullToEmpty(empresa);
		EmpresaEndereco enderecoPrincipal = new EmpresaEndereco();
		for (EmpresaEndereco e : empresa.getListaEndereco()) {
			if (e.getPrincipal().equals("S")) {
				enderecoPrincipal = e;
			}
		}
		enderecoPrincipal = (EmpresaEndereco) Biblioteca.nullToEmpty(enderecoPrincipal);

		List<NfeCabecalho> listaNfeCabecalho = nfeDao.getBeans(NfeCabecalho.class, "dataHoraEmissao", dataInicio, dataFim);

		List<UnidadeProduto> listaUnidadeProduto = new ArrayList<>();

		List<TributOperacaoFiscal> listaOperacaoFiscal = operacaoFiscalDao.getBeans(TributOperacaoFiscal.class);

		Contador contador = contadorDao.getBean(idContador, Contador.class);

		// REGISTRO 0000: ABERTURA DO ARQUIVO DIGITAL E IDENTIFICA��O DA
		// ENTIDADE
		sped.getBloco0().getRegistro0000().setDtIni(dataInicio);
		sped.getBloco0().getRegistro0000().setDtFin(dataFim);
		sped.getBloco0().getRegistro0000().setCodVer(versao);
		sped.getBloco0().getRegistro0000().setCodFin(finalidadeArquivo);
		sped.getBloco0().getRegistro0000().setIndPerfil(perfil);
		sped.getBloco0().getRegistro0000().setNome(empresa.getRazaoSocial());
		sped.getBloco0().getRegistro0000().setCnpj(empresa.getCnpj());
		sped.getBloco0().getRegistro0000().setCpf("");
		sped.getBloco0().getRegistro0000().setIe(empresa.getInscricaoEstadual());
		sped.getBloco0().getRegistro0000().setCodMun(empresa.getCodigoIbgeCidade().toString());
		sped.getBloco0().getRegistro0000().setIm(empresa.getInscricaoMunicipal());
		sped.getBloco0().getRegistro0000().setSuframa(empresa.getSuframa());
		sped.getBloco0().getRegistro0000().setIndAtiv("1");
		sped.getBloco0().getRegistro0000().setUf(enderecoPrincipal.getUf());

		// REGISTRO 0001: ABERTURA DO BLOCO 0
		sped.getBloco0().getRegistro0001().setIndMov(0);

		// REGISTRO 0005: DADOS COMPLEMENTARES DA ENTIDADE
		sped.getBloco0().getRegistro0005().setFantasia(empresa.getNomeFantasia());
		sped.getBloco0().getRegistro0005().setCep(enderecoPrincipal.getCep());
		sped.getBloco0().getRegistro0005().setEndereco(enderecoPrincipal.getLogradouro());
		sped.getBloco0().getRegistro0005().setNum(enderecoPrincipal.getNumero());
		sped.getBloco0().getRegistro0005().setCompl(enderecoPrincipal.getComplemento());
		sped.getBloco0().getRegistro0005().setBairro(enderecoPrincipal.getBairro());
		sped.getBloco0().getRegistro0005().setFone(enderecoPrincipal.getFone());
		sped.getBloco0().getRegistro0005().setFax(enderecoPrincipal.getFone());
		sped.getBloco0().getRegistro0005().setEmail(empresa.getEmail());

		// REGISTRO 0015: DADOS DO CONTRIBUINTE SUBSTITUTO
		// Implementado a crit�rio do Participante do T2Ti ERP
		// REGISTRO 0100: DADOS DO CONTABILISTA
		sped.getBloco0().getRegistro0100().setNome(contador.getNome());
		if (!contador.getCpf().isEmpty()) {
			sped.getBloco0().getRegistro0100().setCpf(contador.getCpf());
		} else if (!contador.getCnpj().isEmpty()) {
			sped.getBloco0().getRegistro0100().setCpf(contador.getCnpj());
		}
		sped.getBloco0().getRegistro0100().setCrc(contador.getInscricaoCrc());
		sped.getBloco0().getRegistro0100().setCep(contador.getCep());
		sped.getBloco0().getRegistro0100().setEndereco(contador.getLogradouro());
		sped.getBloco0().getRegistro0100().setNum(contador.getNumero());
		sped.getBloco0().getRegistro0100().setCompl(contador.getComplemento());
		sped.getBloco0().getRegistro0100().setBairro(contador.getBairro());
		sped.getBloco0().getRegistro0100().setFone(contador.getFone());
		sped.getBloco0().getRegistro0100().setFax(contador.getFax());
		sped.getBloco0().getRegistro0100().setEmail(contador.getEmail());
		sped.getBloco0().getRegistro0100().setCodMun(contador.getMunicipioIbge());

		// REGISTRO 0150: TABELA DE CADASTRO DO PARTICIPANTE
		/*
		 * Dever�o ser informados somente os participantes que tiveram
		 * movimenta��o no per�odo de refer�ncia da EFD, n�o sendo necess�rio
		 * informar os CNPJs e CPFs citados nos registros C350 e C460
		 * [adquirentes nas opera��es acobertadas com nota fiscal de venda a
		 * consumidor ou cupom fiscal]
		 */
		Registro0150 registro0150;
		NfeEmitente emitente;
		NfeDestinatario destinatario;
		for (NfeCabecalho c : listaNfeCabecalho) {
			registro0150 = new Registro0150();
			emitente = c.getNfeEmitente();

			registro0150.setCodPart("F" + emitente.getNfeCabecalho().getFornecedor().getId());
			registro0150.setNome(emitente.getNome());
			registro0150.setCodPais("01058");
			if (emitente.getCpfCnpj().length() == 11) {
				registro0150.setCpf(emitente.getCpfCnpj());
			} else if (emitente.getCpfCnpj().length() == 14) {
				registro0150.setCnpj(emitente.getCpfCnpj());
			}
			registro0150.setCodMun(emitente.getCodigoMunicipio());
			registro0150.setSuframa(String.valueOf(emitente.getSuframa()));
			registro0150.setEndereco(emitente.getLogradouro());
			registro0150.setNum(emitente.getNumero());
			registro0150.setCompl(emitente.getComplemento());
			registro0150.setBairro(emitente.getBairro());

			sped.getBloco0().getListaRegistro0150().add(registro0150);

			registro0150 = new Registro0150();
			destinatario = c.getNfeDestinatario();

			registro0150.setCodPart("C" + destinatario.getNfeCabecalho().getCliente().getId());
			registro0150.setNome(destinatario.getNome());
			registro0150.setCodPais("01058");
			if (destinatario.getCpfCnpj().length() == 11) {
				registro0150.setCpf(destinatario.getCpfCnpj());
			} else if (destinatario.getCpfCnpj().length() == 14) {
				registro0150.setCnpj(destinatario.getCpfCnpj());
			}
			registro0150.setCodMun(destinatario.getCodigoMunicipio());
			registro0150.setSuframa(String.valueOf(destinatario.getSuframa()));
			registro0150.setEndereco(destinatario.getLogradouro());
			registro0150.setNum(destinatario.getNumero());
			registro0150.setCompl(destinatario.getComplemento());
			registro0150.setBairro(destinatario.getBairro());

			sped.getBloco0().getListaRegistro0150().add(registro0150);

			// REGISTRO 0175: ALTERA��O DA TABELA DE CADASTRO DE PARTICIPANTE
			// Pegar os dados de PESSOA_ALTERACAO para gerar o registro 0175
			// registro 0200
			for (NfeDetalhe nfeDetalhe : c.getListaNfeDetalhe()) {
				Registro0200 registro0200 = new Registro0200();
				Produto produto = nfeDetalhe.getProduto();

				registro0200.setCodItem(produto.getId().toString());
				registro0200.setDescrItem(produto.getDescricao());
				registro0200.setCodBarra(produto.getGtin());
				// TEM QUE PREENCHER PARA INFORMAR NO 0205
				registro0200.setCodAntItem("");
				registro0200.setUnidInv(produto.getUnidadeProduto().getId().toString());
				registro0200.setTipoItem(produto.getTipoItemSped());
				registro0200.setCodNcm(produto.getNcm());
				registro0200.setExIpi(produto.getExTipi());
				registro0200.setCodGen(produto.getNcm().substring(0, 2));
				registro0200.setCodLst(produto.getCodigoLst());
				registro0200.setAliqIcms(produto.getAliquotaIcmsPaf());

				if (!listaUnidadeProduto.contains(produto.getUnidadeProduto())) {
					listaUnidadeProduto.add(produto.getUnidadeProduto());
				}

				// REGISTRO 0205: ALTERA��O DO ITEM
				List<ProdutoAlteracaoItem> listaProdutoAlteracaoItem = alteracaoItemDao.getBeans(ProdutoAlteracaoItem.class, "produto", produto, "dataInicial", dataInicio, dataFim);
				Registro0205 registro0205;
				for (ProdutoAlteracaoItem produtoAlteracaoItem : listaProdutoAlteracaoItem) {
					registro0205 = new Registro0205();

					registro0205.setDescrAntItem(produtoAlteracaoItem.getNome());
					registro0205.setDtIni(produtoAlteracaoItem.getDataInicial());
					registro0205.setDtFin(produtoAlteracaoItem.getDataFinal());
					registro0205.setCodAntItem(produtoAlteracaoItem.getCodigo());

					registro0200.getRegistro0205List().add(registro0205);
				}

				// REGISTRO 0206: C�DIGO DE PRODUTO CONFORME TABELA PUBLICADA
				// PELA ANP (COMBUST�VEIS)
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO 0210: CONSUMO ESPEC�FICO PADRONIZADO
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO 0220: FATORES DE CONVERS�O DE UNIDADES
				UnidadeConversao unidadeConversao = produto.getUnidadeConversao();
				if (unidadeConversao != null) {
					Registro0220 registro0220 = new Registro0220();
					registro0220.setUnidConv(unidadeConversao.getProduto().getUnidadeProduto().getId().toString());
					registro0220.setFatConv(unidadeConversao.getFatorConversao());

					registro0200.getRegistro0220List().add(registro0220);
				}
			}
		}

		// REGISTRO 0190: IDENTIFICA��O DAS UNIDADES DE MEDIDA
		Registro0190 registro0190;
		for (UnidadeProduto unidade : listaUnidadeProduto) {
			registro0190 = new Registro0190();

			registro0190.setUnid(unidade.getId().toString());
			registro0190.setDescr(unidade.getSigla());

			sped.getBloco0().getListaRegistro0190().add(registro0190);
		}

		// REGISTRO 0300: CADASTRO DE BENS OU COMPONENTES DO ATIVO IMOBILIZADO
		// REGISTRO 0305: INFORMA��O SOBRE A UTILIZA��O DO BEM
		// Implementado a crit�rio do Participante do T2Ti ERP - vers�o 1.0 n�o
		// possui controle CIAP
		// REGISTRO 0400: TABELA DE NATUREZA DA OPERA��O/PRESTA��O
		Registro0400 registro0400;
		for (TributOperacaoFiscal operacaoFiscal : listaOperacaoFiscal) {
			registro0400 = new Registro0400();

			registro0400.setCodNat(operacaoFiscal.getId().toString());
			registro0400.setDescrNat(operacaoFiscal.getDescricaoNaNf());
		}

		// REGISTRO 0450: TABELA DE INFORMA��O COMPLEMENTAR DO DOCUMENTO FISCAL
		// Implementado a crit�rio do Participante do T2Ti ERP
		// REGISTRO 0460: TABELA DE OBSERVA��ES DO LAN�AMENTO FISCAL
		// Implementado a crit�rio do Participante do T2Ti ERP
		// REGISTRO 0500: PLANO DE CONTAS CONT�BEIS
		// Implementado a crit�rio do Participante do T2Ti ERP
		// REGISTRO 0600: CENTRO DE CUSTOS
		// Implementado a crit�rio do Participante do T2Ti ERP
	}

	// BLOCO C: DOCUMENTOS FISCAIS I - MERCADORIAS (ICMS/IPI)
	private void geraBlocoC() throws Exception {
		List<NfeCabecalho> listaNfeCabecalho = nfeDao.getBeans(NfeCabecalho.class, "dataHoraEmissao", dataInicio, dataFim);

		List<EcfNotaFiscalCabecalho> listaNf2Cabecalho = nf2Dao.getBeans(EcfNotaFiscalCabecalho.class, "dataEmissao", dataInicio, dataFim);

		List<EcfNotaFiscalCabecalho> listaNf2Cancelada = nf2Dao.getBeans(EcfNotaFiscalCabecalho.class, "cancelada", "S", "dataEmissao", dataInicio, dataFim);

		// PERFIL A
		if (perfil.equals("A")) {
			// REGISTRO C100: NOTA FISCAL (C�DIGO 01), NOTA FISCAL AVULSA
			// (C�DIGO 1B), NOTA FISCAL DE PRODUTOR (C�DIGO 04), NF-e (C�DIGO
			// 55) e NFC-e (C�DIGO 65)
			RegistroC100 registroC100;
			for (NfeCabecalho nfe : listaNfeCabecalho) {
				registroC100 = new RegistroC100();

				registroC100.setIndOper(String.valueOf(nfe.getTipoOperacao()));
				registroC100.setIndEmit("0"); // 0 - Emissao Propria
				if (nfe.getCliente() != null) {
					registroC100.setCodPart("C" + nfe.getCliente().getId().toString());
				} else if (nfe.getFornecedor() != null) {
					registroC100.setCodPart("F" + nfe.getFornecedor().getId().toString());
				}
				registroC100.setCodMod(nfe.getCodigoModelo());

				/*
				 * 4.1.2- Tabela Situa��o do Documento C�digo Descri��o 00
				 * Documento regular 01 Documento regular extempor�neo 02
				 * Documento cancelado 03 Documento cancelado extempor�neo 04
				 * NFe denegada 05 Nfe � Numera��o inutilizada 06 Documento
				 * Fiscal Complementar 07 Documento Fiscal Complementar
				 * extempor�neo. 08 Documento Fiscal emitido com base em Regime
				 * Especial ou Norma Espec�fica
				 */
				if (nfe.getStatusNota().equals("5")) {
					registroC100.setCodSit("00");
				} else if (nfe.getStatusNota().equals("6")) {
					registroC100.setCodSit("02");
				}
				registroC100.setSer(nfe.getSerie());
				registroC100.setNumDoc(nfe.getNumero());
				registroC100.setChvNfe(nfe.getChaveAcesso());
				registroC100.setDtDoc(nfe.getDataHoraEmissao());
				registroC100.setDtES(nfe.getDataHoraEntradaSaida());
				registroC100.setVlDoc(nfe.getValorTotal());
				registroC100.setIndPgto(String.valueOf(nfe.getIndicadorFormaPagamento()));
				registroC100.setVlDesc(nfe.getValorDesconto());
				registroC100.setVlAbatNt(BigDecimal.ZERO);
				registroC100.setVlMerc(nfe.getValorTotalProdutos());

				NfeTransporte transporte = nfe.getNfeTransporte();
				if (transporte != null) {
					registroC100.setIndFrt(String.valueOf(transporte.getModalidadeFrete()));
				}

				registroC100.setVlFrt(nfe.getValorFrete());
				registroC100.setVlSeg(nfe.getValorSeguro());
				registroC100.setVlOutDa(nfe.getValorDespesasAcessorias());
				registroC100.setVlBcIcms(nfe.getBaseCalculoIcms());
				registroC100.setVlIcms(nfe.getValorIcms());
				registroC100.setVlBcIcmsSt(nfe.getBaseCalculoIcmsSt());
				registroC100.setVlIcmsSt(nfe.getValorIcmsSt());
				registroC100.setVlIpi(nfe.getValorIpi());
				registroC100.setVlPis(nfe.getValorPis());
				registroC100.setVlPisSt(BigDecimal.ZERO);
				registroC100.setVlCofins(nfe.getValorCofins());
				registroC100.setVlCofinsSt(BigDecimal.ZERO);

				// REGISTRO C105: OPERA��ES COM ICMS ST RECOLHIDO PARA UF
				// DIVERSA DO DESTINAT�RIO DO DOCUMENTO FISCAL (C�DIGO 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C110: INFORMA��O COMPLEMENTAR DA NOTA FISCAL (C�DIGO
				// 01, 1B, 04 e 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C111: PROCESSO REFERENCIADO
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C112: DOCUMENTO DE ARRECADA��O REFERENCIADO.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C113: DOCUMENTO FISCAL REFERENCIADO.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C114: CUPOM FISCAL REFERENCIADO
				RegistroC114 registroC114;
				;
				for (NfeCupomFiscalReferenciado cupomFiscal : nfe.getListaNfeCupomFiscalReferenciado()) {
					registroC114 = new RegistroC114();

					registroC114.setCodMod(cupomFiscal.getModeloDocumentoFiscal());
					registroC114.setEcfFab(cupomFiscal.getNumeroSerieEcf());
					registroC114.setEcfCx(cupomFiscal.getNumeroCaixa().toString());
					registroC114.setNumDoc(cupomFiscal.getCoo().toString());
					registroC114.setDtDoc(cupomFiscal.getDataEmissaoCupom());

					sped.getBlocoC().getListaRegistroC114().add(registroC114);
				}

				// REGISTRO C115: LOCAL DA COLETA E/OU ENTREGA (C�DIGO 01, 1B E
				// 04).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C116: CUPOM FISCAL ELETR�NICO REFERENCIADO
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C120: COMPLEMENTO DE DOCUMENTO - OPERA��ES DE
				// IMPORTA��O (C�DIGOS 01 e 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C130: ISSQN, IRRF E PREVID�NCIA SOCIAL.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C140: FATURA (C�DIGO 01)
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C141: VENCIMENTO DA FATURA (C�DIGO 01).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C160: VOLUMES TRANSPORTADOS (C�DIGO 01 E 04) -
				// EXCETO COMBUST�VEIS.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C165: OPERA��ES COM COMBUST�VEIS (C�DIGO 01).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C170: ITENS DO DOCUMENTO (C�DIGO 01, 1B, 04 e 55).
				RegistroC170 registroC170;
				for (NfeDetalhe nfeDetalhe : nfe.getListaNfeDetalhe()) {
					registroC170 = new RegistroC170();

					registroC170.setNumItem(nfeDetalhe.getNumeroItem().toString());
					registroC170.setCodItem(nfeDetalhe.getGtin());
					registroC170.setDescrCompl(nfeDetalhe.getNomeProduto());
					registroC170.setQtd(nfeDetalhe.getQuantidadeComercial());
					registroC170.setUnid(nfeDetalhe.getProduto().getUnidadeProduto().getId().toString());
					registroC170.setVlItem(nfeDetalhe.getValorTotal());
					registroC170.setVlDesc(nfeDetalhe.getValorDesconto());
					registroC170.setIndMov(0);
					registroC170.setCstIcms(nfeDetalhe.getNfeDetalheImpostoIcms().getCstIcms());
					registroC170.setCfop(nfeDetalhe.getCfop().toString());
					registroC170.setCodNat(nfeDetalhe.getNfeCabecalho().getTributOperacaoFiscal().getId().toString());
					registroC170.setVlBcIcms(nfeDetalhe.getNfeDetalheImpostoIcms().getBaseCalculoIcms());
					registroC170.setAliqIcms(nfeDetalhe.getNfeDetalheImpostoIcms().getAliquotaIcms());
					registroC170.setVlIcms(nfeDetalhe.getNfeDetalheImpostoIcms().getValorIcms());
					registroC170.setVlBcIcmsSt(nfeDetalhe.getNfeDetalheImpostoIcms().getValorBaseCalculoIcmsSt());
					registroC170.setAliqSt(nfeDetalhe.getNfeDetalheImpostoIcms().getAliquotaIcmsSt());
					registroC170.setVlIcmsSt(nfeDetalhe.getNfeDetalheImpostoIcms().getValorIcmsSt());
					registroC170.setIndApur(0);
					registroC170.setCstIpi(nfeDetalhe.getNfeDetalheImpostoIpi().getCstIpi());
					registroC170.setCodEnq(nfeDetalhe.getNfeDetalheImpostoIpi().getEnquadramentoIpi());
					registroC170.setVlBcIpi(nfeDetalhe.getNfeDetalheImpostoIpi().getValorBaseCalculoIpi());
					registroC170.setAliqIpi(nfeDetalhe.getNfeDetalheImpostoIpi().getAliquotaIpi());
					registroC170.setVlIpi(nfeDetalhe.getNfeDetalheImpostoIpi().getValorIpi());
					registroC170.setCstPis(nfeDetalhe.getNfeDetalheImpostoIpi().getCstIpi());
					registroC170.setVlBcPis(nfeDetalhe.getNfeDetalheImpostoPis().getValorBaseCalculoPis());
					registroC170.setAliqPisPerc(nfeDetalhe.getNfeDetalheImpostoPis().getAliquotaPisPercentual());
					registroC170.setQuantBcPis(nfeDetalhe.getNfeDetalheImpostoPis().getQuantidadeVendida());
					registroC170.setAliqPisR(nfeDetalhe.getNfeDetalheImpostoPis().getAliquotaPisReais());
					registroC170.setVlPis(nfeDetalhe.getNfeDetalheImpostoPis().getValorPis());
					registroC170.setCstCofins(nfeDetalhe.getNfeDetalheImpostoCofins().getCstCofins());
					registroC170.setVlBcCofins(nfeDetalhe.getNfeDetalheImpostoCofins().getBaseCalculoCofins());
					registroC170.setAliqCofinsPerc(nfeDetalhe.getNfeDetalheImpostoCofins().getAliquotaCofinsPercentual());
					registroC170.setQuantBcCofins(nfeDetalhe.getNfeDetalheImpostoCofins().getQuantidadeVendida());
					registroC170.setAliqCofinsR(nfeDetalhe.getNfeDetalheImpostoCofins().getAliquotaCofinsReais());
					registroC170.setVlCofins(nfeDetalhe.getNfeDetalheImpostoCofins().getValorCofins());
					registroC170.setCodCta("");

					registroC100.getRegistroC170List().add(registroC170);
				}

				// REGISTRO C171: ARMAZENAMENTO DE COMBUSTIVEIS (c�digo 01, 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C172: OPERA��ES COM ISSQN (C�DIGO 01)
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C173: OPERA��ES COM MEDICAMENTOS (C�DIGO 01 e 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C174: OPERA��ES COM ARMAS DE FOGO (C�DIGO 01).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C175: OPERA��ES COM VE�CULOS NOVOS (C�DIGO 01 e 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C176: RESSARCIMENTO DE ICMS EM OPERA��ES COM
				// SUBSTITUI��O TRIBUT�RIA (C�DIGO 01, 55).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C177: OPERA��ES COM PRODUTOS SUJEITOS A SELO DE
				// CONTROLE IPI.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C178: OPERA��ES COM PRODUTOS SUJEITOS � TRIBUTA��O
				// DE IPI POR UNIDADE OU QUANTIDADE DE PRODUTO.
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C179: INFORMA��ES COMPLEMENTARES ST (C�DIGO 01).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C190: REGISTRO ANAL�TICO DO DOCUMENTO (C�DIGO 01,
				// 1B, 04 ,55 e 65).
				List<ViewSpedC190Id> listaNfeAnalitico = viewC190Dao.getBeans(ViewSpedC190Id.class, "viewSpedC190.id", nfe.getId());
				RegistroC190 registroC190;
				for (ViewSpedC190Id s : listaNfeAnalitico) {
					registroC190 = new RegistroC190();
					ViewSpedC190 spedC190 = s.getViewSpedC190();

					registroC190.setCstIcms(spedC190.getCstIcms());
					registroC190.setCfop(spedC190.getCfop().toString());
					registroC190.setAliqIcms(spedC190.getAliquotaIcms());
					registroC190.setVlOpr(spedC190.getSomaValorOperacao());
					registroC190.setVlBcIcms(spedC190.getSomaBaseCalculoIcms());
					registroC190.setVlIcms(spedC190.getSomaValorIcms());
					registroC190.setVlBcIcmsSt(spedC190.getSomaBaseCalculoIcmsSt());
					registroC190.setVlIcmsSt(spedC190.getSomaValorIcmsSt());
					registroC190.setVlRedBc(spedC190.getSomaVlRedBc());
					registroC190.setVlIpi(spedC190.getSomaValorIpi());
					registroC190.setCodObs("");

					registroC100.getRegistroC190List().add(registroC190);
				}

				// REGISTRO C195: OBSERVA�OES DO LAN�AMENTO FISCAL (C�DIGO 01,
				// 1B E 55)
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C197: OUTRAS OBRIGA��ES TRIBUT�RIAS, AJUSTES E
				// INFORMA��ES DE VALORES PROVENIENTES DE DOCUMENTO FISCAL.
				// Implementado a crit�rio do Participante do T2Ti ERP
			}

			// REGISTRO C350: NOTA FISCAL DE VENDA A CONSUMIDOR (C�DIGO 02)
			RegistroC350 registroC350;
			for (EcfNotaFiscalCabecalho notaFiscal : listaNf2Cabecalho) {
				registroC350 = new RegistroC350();

				registroC350.setSer(notaFiscal.getSerie());
				registroC350.setSubSer(notaFiscal.getSubserie());
				registroC350.setNumDoc(notaFiscal.getNumero());
				registroC350.setDtDoc(notaFiscal.getDataEmissao());
				registroC350.setCnpjCpf(notaFiscal.getCpfCnpjCliente());
				registroC350.setVlMerc(notaFiscal.getTotalProdutos());
				registroC350.setVlDoc(notaFiscal.getTotalNf());
				registroC350.setVlDesc(notaFiscal.getDesconto());
				registroC350.setVlPis(notaFiscal.getPis());
				registroC350.setVlCofins(notaFiscal.getCofins());
				registroC350.setCodCta("");

				sped.getBlocoC().getListaRegistroC350().add(registroC350);

				// REGISTRO C370: ITENS DO DOCUMENTO (C�DIGO 02)
				List<ViewSpedC370Id> listaC370 = viewC370Dao.getBeans(ViewSpedC370Id.class, "viewC370.idNfCabecalho", notaFiscal.getId());
				RegistroC370 registroC370;
				;
				for (ViewSpedC370Id s : listaC370) {
					registroC370 = new RegistroC370();
					ViewSpedC370 viewC370 = s.getViewC370();

					registroC370.setNumItem(viewC370.getItem().toString());
					registroC370.setCodItem(viewC370.getIdProduto().toString());
					registroC370.setQtd(viewC370.getQuantidade());
					registroC370.setUnid(viewC370.getIdUnidadeProduto().toString());
					registroC370.setVlItem(viewC370.getValorTotal());
					registroC370.setVlDesc(viewC370.getDesconto());

					registroC350.getRegistroC370List().add(registroC370);
				}

				// REGISTRO C390: REGISTRO ANAL�TICO DAS NOTAS FISCAIS DE VENDA
				// A CONSUMIDOR (C�DIGO 02)
				List<ViewSpedC390Id> listaC390 = viewC390Dao.getBeans(ViewSpedC390Id.class, "viewC390.dataEmissao", dataInicio, dataFim);
				RegistroC390 registroC390;
				for (ViewSpedC390Id s : listaC390) {
					registroC390 = new RegistroC390();
					ViewSpedC390 viewC390 = s.getViewC390();

					registroC390.setCstIcms(viewC390.getCst());
					registroC390.setCfop(viewC390.getCfop().toString());
					registroC390.setAliqIcms(viewC390.getTaxaIcms());
					registroC390.setVlOpr(viewC390.getSomaItem());
					registroC390.setVlBcIcms(viewC390.getSomaBaseIcms());
					registroC390.setVlIcms(viewC390.getSomaIcms());
					registroC390.setVlRedBc(viewC390.getSomaIcmsOutras());

					registroC350.getRegistroC390List().add(registroC390);
				}
			}
		}

		// PERFIL B
		if (perfil.equals("B")) {
			// REGISTRO C300: RESUMO DI�RIO DAS NOTAS FISCAIS DE VENDA A
			// CONSUMIDOR (C�DIGO 02)
			List<ViewSpedC300Id> listaC300 = viewC300Dao.getBeans(ViewSpedC300Id.class, "viewC300.dataEmissao", dataInicio, dataFim);
			RegistroC300 registroC300;
			RegistroC310 registroC310;
			for (ViewSpedC300Id c300 : listaC300) {
				ViewSpedC300 viewC300 = c300.getViewSpedC300();

				registroC300 = new RegistroC300();

				registroC300.setCodMod("02");
				registroC300.setSer(viewC300.getSerie());
				registroC300.setSub(viewC300.getSubserie());

				List<EcfNotaFiscalCabecalho> listaEcfNotaFiscal = ecfNotaFiscalCabecalhoDao.getBeans(dataInicio, dataFim, viewC300.getSerie(), viewC300.getSubserie());
				if (!listaEcfNotaFiscal.isEmpty()) {
					registroC300.setNumDocIni(listaEcfNotaFiscal.get(0).getNumero());
					registroC300.setNumDocFin(listaEcfNotaFiscal.get(listaEcfNotaFiscal.size() - 1).getNumero());
				}

				registroC300.setDtDoc(viewC300.getDataEmissao());
				registroC300.setVlDoc(viewC300.getSomaTotalNf());
				registroC300.setVlPis(viewC300.getSomaPis());
				registroC300.setVlCofins(viewC300.getSomaCofins());

				// REGISTRO C310: DOCUMENTOS CANCELADOS DE NOTAS FISCAIS DE
				// VENDA A CONSUMIDOR (C�DIGO 02).
				for (int j = 0; j < listaNf2Cancelada.size(); j++) {
					registroC310 = new RegistroC310();

					registroC310.setNumDocCanc(listaNf2Cancelada.get(j).getNumero());

					registroC300.getRegistroC310List().add(registroC310);
				}

				// REGISTRO C320: REGISTRO ANAL�TICO DO RESUMO DI�RIO DAS NOTAS
				// FISCAIS DE VENDA A CONSUMIDOR (C�DIGO 02). ---> igual ao C390
				List<ViewSpedC390Id> listaC390 = viewC390Dao.getBeans(ViewSpedC390Id.class, "viewC390.dataEmissao", dataInicio, dataFim);
				RegistroC320 registroC320;
				for (ViewSpedC390Id c390 : listaC390) {
					registroC320 = new RegistroC320();
					ViewSpedC390 viewC390 = c390.getViewC390();

					registroC320.setCstIcms(viewC390.getCst());
					registroC320.setCfop(viewC390.getCfop().toString());
					registroC320.setAliqIcms(viewC390.getTaxaIcms());
					registroC320.setVlOpr(viewC390.getSomaItem());
					registroC320.setVlBcIcms(viewC390.getSomaBaseIcms());
					registroC320.setVlIcms(viewC390.getSomaIcms());
					registroC320.setVlRedBc(viewC390.getSomaIcmsOutras());

					// REGISTRO C321: ITENS DO RESUMO DI�RIO DOS DOCUMENTOS
					// (C�DIGO 02).
					List<ViewSpedC321Id> listaC321 = viewC321Dao.getBeans(ViewSpedC321Id.class, "viewC321.dataEmissao", dataInicio, dataFim);
					RegistroC321 registroC321;
					for (ViewSpedC321Id c321 : listaC321) {
						registroC321 = new RegistroC321();
						ViewSpedC321 spedC321 = c321.getViewC321();

						registroC321.setCodItem(spedC321.getIdProduto().toString());
						registroC321.setQtd(spedC321.getSomaQuantidade());
						registroC321.setUnid(spedC321.getDescricaoUnidade());
						registroC321.setVlItem(spedC321.getSomaItem());
						registroC321.setVlDesc(spedC321.getSomaDesconto());
						registroC321.setVlBcIcms(spedC321.getSomaBaseIcms());
						registroC321.setVlIcms(spedC321.getSomaIcms());
						registroC321.setVlPis(spedC321.getSomaPis());
						registroC321.setVlCofins(spedC321.getSomaCofins());

						registroC320.getRegistroC321List().add(registroC321);
					}

					registroC300.getRegistroC320List().add(registroC320);
				}

				sped.getBlocoC().getListaRegistroC300().add(registroC300);
			}
		}// if (perfil.equals("B")) {

		// Ambos os Perfis
		// REGISTRO C400: EQUIPAMENTO ECF (C�DIGO 02, 2D e 60).
		List<EcfImpressora> listaEcf = ecfImpressoraDao.getBeans(EcfImpressora.class);
		RegistroC400 registroC400;
		for (EcfImpressora ecf : listaEcf) {
			registroC400 = new RegistroC400();

			registroC400.setCodMod(ecf.getModeloDocumentoFiscal());
			registroC400.setEcfMod(ecf.getModelo());
			registroC400.setEcfFab(ecf.getSerie());
			registroC400.setEcfCx(ecf.getNumero().toString());

			// REGISTRO C405: REDU��O Z (C�DIGO 02, 2D e 60).
			// verifica se existe movimento no periodo para aquele ECF
			List<EcfR02> listaR02 = ecfR02Dao.getBeans(EcfR02.class, "idImpressora", ecf.getId(), "dataEmissao", dataInicio, dataFim);
			RegistroC405 registroC405;
			for (EcfR02 r02 : listaR02) {
				registroC405 = new RegistroC405();

				registroC405.setDtDoc(r02.getDataMovimento());
				registroC405.setCro(r02.getCro());
				registroC405.setCrz(r02.getCrz());
				registroC405.setNumCooFin(r02.getCoo());
				registroC405.setGtFin(r02.getGrandeTotal());
				registroC405.setVlBrt(r02.getVendaBruta());

				// REGISTRO C410: PIS E COFINS TOTALIZADOS NO DIA (C�DIGO 02 e
				// 2D).
				// Implementado a crit�rio do Participante do T2Ti ERP
				// REGISTRO C420: REGISTRO DOS TOTALIZADORES PARCIAIS DA REDU��O
				// Z (COD 02, 2D e 60).
				List<EcfR03> listaR03 = ecfR03Dao.getBeans(EcfR03.class, "idR02", r02.getId());
				RegistroC420 registroC420;
				for (EcfR03 r03 : listaR03) {
					registroC420 = new RegistroC420();

					if (r03.getTotalizadorParcial().length() == 8) {
						registroC420.setCodTotPar(r03.getTotalizadorParcial().substring(1));
					} else {
						registroC420.setCodTotPar(r03.getTotalizadorParcial());
					}
					registroC420.setVlrAcumTot(r03.getValorAcumulado());
					if (r03.getTotalizadorParcial().length() == 7) {
						registroC420.setNrTot(Integer.valueOf(r03.getTotalizadorParcial().substring(0, 2)));
					} else {
						registroC420.setNrTot(0);
					}

					if (perfil.equals("B")) {
						// REGISTRO C425: RESUMO DE ITENS DO MOVIMENTO DI�RIO
						// (C�DIGO 02 e 2D).
						List<ViewSpedC425Id> listaC425 = viewC425Dao.getBeans(dataInicio, dataFim, "%CAN%", "%S%");
						RegistroC425 registroC425;
						for (ViewSpedC425Id c425 : listaC425) {
							registroC425 = new RegistroC425();
							ViewSpedC425 viewC425 = c425.getViewC425();

							registroC425.setCodItem(viewC425.getIdEcfProduto().toString());
							registroC425.setUnid(viewC425.getDescricaoUnidade());
							registroC425.setQtd(viewC425.getSomaQuantidade());
							registroC425.setVlItem(viewC425.getSomaItem());
							registroC425.setVlPis(viewC425.getSomaPis());
							registroC425.setVlCofins(viewC425.getSomaCofins());

							registroC420.getRegistroc425List().add(registroC425);
						}
					}

					registroC405.getRegistroC420List().add(registroC420);
				}

				// se tiver o perfil A, gera o C460 com C470
				if (perfil.equals("A")) {
					// REGISTRO C460: DOCUMENTO FISCAL EMITIDO POR ECF (C�DIGO
					// 02, 2D e 60).
					List<EcfVendaCabecalho> listaR04 = ecfVendaCabecalhoDao.getBeans(EcfVendaCabecalho.class, "dataVenda", dataInicio, dataFim);
					RegistroC460 registroC460;
					for (EcfVendaCabecalho r04 : listaR04) {
						registroC460 = new RegistroC460();

						registroC460.setCodMod("2D");
						if (r04.getStatusVenda().equals("C")) {
							registroC460.setCodSit("02");
						} else {
							registroC460.setCodSit("00");
							registroC460.setDtDoc(r04.getDataVenda());
							registroC460.setVlDoc(r04.getValorFinal());
							registroC460.setVlPis(r04.getPis());
							registroC460.setVlCofins(r04.getCofins());
							registroC460.setCpfCnpj(r04.getCpfCnpjCliente());
							registroC460.setNomAdq(r04.getNomeCliente());

							// REGISTRO C465: COMPLEMENTO DO CUPOM FISCAL
							// ELETR�NICO EMITIDO POR ECF � CF-e-ECF (C�DIGO
							// 60).
							// Implementado a crit�rio do Participante do T2Ti
							// ERP }
							// REGISTRO C470: ITENS DO DOCUMENTO FISCAL EMITIDO
							// POR ECF (C�DIGO 02 e 2D).
							List<EcfVendaDetalhe> listaR05 = ecfVendaDetalheDao.getBeans(EcfVendaDetalhe.class, "idEcfVendaCabecalho", r04.getId());
							RegistroC470 registroC470;
							for (EcfVendaDetalhe r05 : listaR05) {
								registroC470 = new RegistroC470();

								registroC470.setCodItem(r05.getIdEcfProduto().toString());
								registroC470.setQtd(r05.getQuantidade());
								registroC470.setQtdCanc(BigDecimal.ZERO);

								Produto produto = produtoDao.getBean(r05.getIdEcfProduto(), Produto.class);

								registroC470.setUnid(produto.getUnidadeProduto().getId().toString());
								registroC470.setVlItem(r05.getTotalItem());
								registroC470.setCstIcms(r05.getCst());
								registroC470.setCfop(r05.getCfop().toString());
								registroC470.setAliqIcms(r05.getTaxaIcms());
								registroC470.setVlPis(r05.getPis());
								registroC470.setVlCofins(r05.getCofins());

								registroC460.getRegistroC470List().add(registroC470);
							}
						}
						registroC460.setNumDoc(r04.getCoo().toString());

						registroC405.getRegistroC460List().add(registroC460);
					}
				}

				// REGISTRO C490: REGISTRO ANAL�TICO DO MOVIMENTO DI�RIO (C�DIGO
				// 02, 2D e 60).
				List<ViewSpedC490Id> listaC490 = viewC490Dao.getBeans(ViewSpedC490Id.class, "viewC490.dataVenda", dataInicio, dataFim);
				RegistroC490 registroC490;
				for (ViewSpedC490Id c490 : listaC490) {
					registroC490 = new RegistroC490();
					ViewSpedC490 viewC490 = c490.getViewC490();

					registroC490.setCstIcms(viewC490.getCst());
					registroC490.setCfop(viewC490.getCfop().toString());
					registroC490.setAliqIcms(viewC490.getTaxaIcms());
					registroC490.setVlOpr(viewC490.getSomaItem());
					registroC490.setVlBcIcms(viewC490.getSomaBaseIcms());
					registroC490.setVlIcms(viewC490.getSomaIcms());

					registroC405.getRegistroC490List().add(registroC490);
				}

				// REGISTRO C495: RESUMO MENSAL DE ITENS DO ECF POR
				// ESTABELECIMENTO (C�DIGO 02 e 2D).
				// Implementado a crit�rio do Participante do T2Ti ERP
				registroC400.getRegistroC405List().add(registroC405);
			}

			// REGISTRO C500: NOTA FISCAL/CONTA DE ENERGIA EL�TRICA (C�DIGO 06),
			// NOTA FISCAL/CONTA DE FORNECIMENTO D'�GUA CANALIZADA (C�DIGO 29) E
			// NOTA FISCAL CONSUMO FORNECIMENTO DE G�S (C�DIGO 28).
			// REGISTRO C510: ITENS DO DOCUMENTO NOTA FISCAL/CONTA ENERGIA
			// EL�TRICA (C�DIGO 06), NOTA FISCAL/CONTA DE FORNECIMENTO D'�GUA
			// CANALIZADA (C�DIGO 29) E NOTA FISCAL/CONTA DE FORNECIMENTO DE G�S
			// (C�DIGO 28).
			// REGISTRO C590: REGISTRO ANAL�TICO DO DOCUMENTO - NOTA
			// FISCAL/CONTA DE ENERGIA EL�TRICA (C�DIGO 06), NOTA FISCAL/CONTA
			// DE FORNECIMENTO D'�GUA CANALIZADA (C�DIGO 29) E NOTA FISCAL
			// CONSUMO FORNECIMENTO DE G�S (C�DIGO 28).
			// REGISTRO C600: CONSOLIDA��O DI�RIA DE NOTAS FISCAIS/CONTAS DE
			// ENERGIA EL�TRICA (C�DIGO 06), NOTA FISCAL/CONTA DE FORNECIMENTO
			// D'�GUA CANALIZADA (C�DIGO 29) E NOTA FISCAL/CONTA DE FORNECIMENTO
			// DE G�S (C�DIGO 28) (EMPRESAS N�O OBRIGADAS AO CONV�NIO ICMS
			// 115/03).
			// REGISTRO C601: DOCUMENTOS CANCELADOS - CONSOLIDA��O DI�RIA DE
			// NOTAS FISCAIS/CONTAS DE ENERGIA EL�TRICA (C�DIGO 06), NOTA
			// FISCAL/CONTA DE FORNECIMENTO D'�GUA CANALIZADA (C�DIGO 29) E NOTA
			// FISCAL/CONTA DE FORNECIMENTO DE G�S (C�DIGO 28)
			// REGISTRO C610: ITENS DO DOCUMENTO CONSOLIDADO (C�DIGO 06), NOTA
			// FISCAL/CONTA DE FORNECIMENTO D'�GUA CANALIZADA (C�DIGO 29) E NOTA
			// FISCAL/CONTA DE FORNECIMENTO DE G�S (C�DIGO 28) (EMPRESAS N�O
			// OBRIGADAS AO CONV�NIO ICMS 115/03).
			// REGISTRO C690: REGISTRO ANAL�TICO DOS DOCUMENTOS (NOTAS
			// FISCAIS/CONTAS DE ENERGIA EL�TRICA (C�DIGO 06), NOTA FISCAL/CONTA
			// DE FORNECIMENTO D��GUA CANALIZADA (C�DIGO 29) E NOTA FISCAL/CONTA
			// DE FORNECIMENTO DE G�S (C�DIGO 28)
			// REGISTRO C700: CONSOLIDA��O DOS DOCUMENTOS NF/CONTA ENERGIA
			// EL�TRICA (C�D 06), EMITIDAS EM VIA �NICA (EMPRESAS OBRIGADAS �
			// ENTREGA DO ARQUIVO PREVISTO NO CONV�NIO ICMS 115/03) E NOTA
			// FISCAL/CONTA DE FORNECIMENTO DE G�S CANALIZADO (C�DIGO 28)
			// REGISTRO C790: REGISTRO ANAL�TICO DOS DOCUMENTOS (C�DIGOS 06 e
			// 28).
			// REGISTRO C791: REGISTRO DE INFORMA��ES DE ST POR UF (COD 06)
			/* Implementados a crit�rio do Participante do T2Ti ERP */

			// REGISTRO C800: CUPOM FISCAL ELETR�NICO (C�DIGO 59)
			// REGISTRO C850: REGISTRO ANAL�TICO DO CF-E (CODIGO 59)
			// REGISTRO C860: IDENTIFICA��O DO EQUIPAMENTO SAT-CF-E
			// REGISTRO C890: RESUMO DI�RIO DO CF-E (C�DIGO 59) POR EQUIPAMENTO
			// SATCF-E
			/* SER�O IMPLEMENTADOS QUANDO O SAT FOR CONSTRUIDO */

			sped.getBlocoC().getListaRegistroC400().add(registroC400);
		}
	}

	// BLOCO E: APURA��O DO ICMS E DO IPI
	private void geraBlocoE() throws Exception {
		// REGISTRO E001: ABERTURA DO BLOCO E
		sped.getBlocoE().getRegistroE001().setIndMov(0);

		// REGISTRO E100: PER�ODO DA APURA��O DO ICMS.
		RegistroE100 registroE100 = new RegistroE100();
		registroE100.setDtIni(dataInicio);
		registroE100.setDtFin(dataFim);
		sped.getBlocoE().getListaRegistroE100().add(registroE100);

		// REGISTRO E110: APURA��O DO ICMS � OPERA��ES PR�PRIAS.
		List<FiscalApuracaoIcms> listaE110 = apuracaoIcmsDao.getBeans(FiscalApuracaoIcms.class, "competencia", Biblioteca.mesAno(dataInicio));
		FiscalApuracaoIcms v;
		RegistroE110 registroE110;
		if (!listaE110.isEmpty()) {
			registroE110 = new RegistroE110();
			v = listaE110.get(0);

			registroE110.setVlTotDebitos(v.getValorTotalDebito());
			registroE110.setVlAjDebitos(v.getValorAjusteDebito());
			registroE110.setVlTotAjDebitos(v.getValorTotalAjusteDebito());
			registroE110.setVlEstornosCred(v.getValorEstornoCredito());
			registroE110.setVlTotCreditos(v.getValorTotalCredito());
			registroE110.setVlAjCreditos(v.getValorAjusteCredito());
			registroE110.setVlTotAjCreditos(v.getValorTotalAjusteCredito());
			registroE110.setVlEstornosDeb(v.getValorEstornoDebito());
			registroE110.setVlSldCredorAnt(v.getValorSaldoCredorAnterior());
			registroE110.setVlSldApurado(v.getValorSaldoApurado());
			registroE110.setVlTotDed(v.getValorTotalDeducao());
			registroE110.setVlIcmsRecolher(v.getValorIcmsRecolher());
			registroE110.setVlSldCredorTransportar(v.getValorSaldoCredorTransp());
			registroE110.setDebEsp(v.getValorDebitoEspecial());

			// registro E116
			RegistroE116 registroE116 = new RegistroE116();
			registroE116.setCodOr("000");
			registroE116.setVlOr(v.getValorIcmsRecolher());
			registroE116.setDtVcto(dataFim);
			registroE116.setCodRec("1");
			registroE116.setNumProc("");
			registroE116.setIndProc("");
			registroE116.setProc("");
			registroE116.setTxtCompl("");
			registroE116.setMesRef("");

			registroE110.getRegistroE116List().add(registroE116);

			registroE100.setRegistroE110(registroE110);
		}
	}

	// BLOCO H: INVENT�RIO F�SICO
	private void geraBlocoH() throws Exception {
		sped.getBlocoH().getRegistroH001().setIndMov(0);// com dados

		List<Produto> listaProduto = produtoDao.getBeans(Produto.class);
		BigDecimal totalGeral = BigDecimal.ZERO;
		for (int i = 0; i < listaProduto.size(); i++) {
			totalGeral = Biblioteca.soma(totalGeral, Biblioteca.multiplica(listaProduto.get(i).getValorCompra(), listaProduto.get(i).getQuantidadeEstoque()));
		}

		// REGISTRO H005: TOTAIS DO INVENT�RIO
		RegistroH005 registroH005 = new RegistroH005();
		registroH005.setDtInv(dataFim);
		registroH005.setVlInv(totalGeral);
		registroH005.setMotInv("0" + inventario);
		sped.getBlocoH().getListaRegistroH005().add(registroH005);

		RegistroH010 registroH010;
		for (int i = 0; i < listaProduto.size(); i++) {
			registroH010 = new RegistroH010();

			registroH010.setCodItem(listaProduto.get(i).getId().toString());
			registroH010.setUnid(listaProduto.get(i).getUnidadeProduto().getId().toString());
			registroH010.setQtd(listaProduto.get(i).getQuantidadeEstoque());
			registroH010.setVlUnit(listaProduto.get(i).getValorCompra());
			registroH010.setVlItem(Biblioteca.multiplica(listaProduto.get(i).getQuantidadeEstoque(), listaProduto.get(i).getValorCompra()));
			registroH010.setIndProp("0");

			registroH005.getRegistroH010List().add(registroH010);
		}

		// REGISTRO H020: Informa��o complementar do Invent�rio.
		// Implementado a crit�rio do Participante do T2Ti ERP }
	}

	// BLOCO 1: OUTRAS INFORMA��ES
	private void geraBloco1() throws Exception {
		sped.getBloco1().getRegistro1001().setIndMov(0);// com dados

		Registro1010 registro1010 = new Registro1010();
		registro1010.setIndExp("N");
		registro1010.setIndCcrf("N");
		registro1010.setIndComb("N");
		registro1010.setIndUsina("N");
		registro1010.setIndVa("N");
		registro1010.setIndEe("N");
		registro1010.setIndCart("N");
		registro1010.setIndForm("N");
		registro1010.setIndAer("N");

		sped.getBloco1().getListaRegistro1010().add(registro1010);
	}

}
