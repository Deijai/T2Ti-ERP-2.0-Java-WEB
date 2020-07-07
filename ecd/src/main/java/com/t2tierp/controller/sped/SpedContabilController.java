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
 * all copies or substantial portions of the Software
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.t2tierp.controller.AbstractController;
import com.t2tierp.model.bean.cadastros.Contador;
import com.t2tierp.model.bean.cadastros.Empresa;
import com.t2tierp.model.bean.cadastros.EmpresaEndereco;
import com.t2tierp.model.bean.contabilidade.ContabilConta;
import com.t2tierp.model.bean.contabilidade.ContabilHistorico;
import com.t2tierp.model.bean.contabilidade.ContabilLancamentoCabecalho;
import com.t2tierp.model.bean.contabilidade.ContabilLancamentoDetalhe;
import com.t2tierp.model.bean.contabilidade.ContabilLivro;
import com.t2tierp.model.bean.contabilidade.ContabilTermo;
import com.t2tierp.model.bean.contabilidade.PlanoConta;
import com.t2tierp.model.bean.contabilidade.RegistroCartorio;
import com.t2tierp.model.bean.sped.ViewSpedI155VoId;
import com.t2tierp.model.dao.Filtro;
import com.t2tierp.model.dao.InterfaceDAO;
import com.t2tierp.sped.SpedContabil;
import com.t2tierp.sped.contabil.blocoi.RegistroI050;
import com.t2tierp.sped.contabil.blocoi.RegistroI075;
import com.t2tierp.sped.contabil.blocoi.RegistroI150;
import com.t2tierp.sped.contabil.blocoi.RegistroI155;
import com.t2tierp.sped.contabil.blocoi.RegistroI200;
import com.t2tierp.sped.contabil.blocoi.RegistroI250;
import com.t2tierp.sped.contabil.blocoj.RegistroJ930;
import com.t2tierp.util.Biblioteca;
import com.t2tierp.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class SpedContabilController extends AbstractController<ContabilConta> implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private InterfaceDAO<ContabilLivro> contabilLivroDao;
	@EJB
	private InterfaceDAO<ContabilTermo> contabilTermoDao;
	@EJB
	private InterfaceDAO<RegistroCartorio> registroCartorioDao;
	@EJB
	private InterfaceDAO<PlanoConta> planoContaDao;
	@EJB
	private InterfaceDAO<ContabilHistorico> contabilHistoricoDao;
	@EJB
	private InterfaceDAO<ViewSpedI155VoId> viewSpedI155VoIdDao;
	@EJB
	private InterfaceDAO<ContabilLancamentoCabecalho> contabilLancamentoCabecalhoDao;
	@EJB
	private InterfaceDAO<ContabilLancamentoDetalhe> contabilLancamentoDetalheDao;
	@EJB
	private InterfaceDAO<Contador> contadorDao;
	
	
	private List<Filtro> filtros;
	
	private Date dataInicial;
	private Date dataFinal;	
    private String formaEscrituracao;
    private String versaoLayout;
    private Empresa empresa;
    private EmpresaEndereco endereco;
    private SpedContabil sped;
    
	@Override
	public Class<ContabilConta> getClazz() {
		return ContabilConta.class;
	}

	@Override
	public String getFuncaoBase() {
		return "ECD";
	}

	public void geraECD() {
		try {
			filtros = new ArrayList<>();
			empresa = FacesContextUtil.getEmpresaUsuario();
			for (EmpresaEndereco e : empresa.getListaEndereco()) {
				if (e.getPrincipal().equals("S")) {
					endereco = e;
				}
			}
			sped = new SpedContabil();

            geraBloco0();
            geraBlocoI();
            geraBlocoJ();

            File file = File.createTempFile("ecd", ".txt");
            file.deleteOnExit();

            sped.geraArquivoTxt(file);
			FacesContextUtil.downloadArquivo(file, "ecd.txt");
		} catch (Exception e) {
			e.printStackTrace();
			FacesContextUtil.adicionaMensagem(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro!", e.getMessage());
		}
	}

    //Bloco 0 - Abertura, Identifica��o e Refer�ncias
    private void geraBloco0() {
        sped.getBloco0().limpaRegistros();

        // REGISTRO 0000: ABERTURA DO ARQUIVO DIGITAL E IDENTIFICA��O DO EMPRES�RIO OU DA SOCIEDADE EMPRES�RIA
        sped.getBloco0().getRegistro0000().setDtIni(dataInicial);
        sped.getBloco0().getRegistro0000().setDtFin(dataFinal);
        sped.getBloco0().getRegistro0000().setNome(empresa.getRazaoSocial());
        sped.getBloco0().getRegistro0000().setCnpj(empresa.getCnpj());
        sped.getBloco0().getRegistro0000().setIe(empresa.getInscricaoEstadual());
        sped.getBloco0().getRegistro0000().setCodMun(empresa.getCodigoIbgeCidade());
        sped.getBloco0().getRegistro0000().setIm(empresa.getInscricaoMunicipal());
        sped.getBloco0().getRegistro0000().setIndSitEsp("");
        sped.getBloco0().getRegistro0000().setUf(endereco.getUf());

        // REGISTRO 0001: ABERTURA DO BLOCO 0
        // bloco com dados informados = 0 | sem dados inf = 1
        sped.getBloco0().getRegistro0001().setIndDad(0);

        // REGISTRO 0007: OUTRAS INSCRI��ES CADASTRAIS DA PESSOA JUR�DICA
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO 0020: ESCRITURA��O CONT�BIL DESCENTRALIZADA
        // Implementado a crit�rio do Participante do T2Ti ERP - Para o treinamento a escritura��o ser� centralizada
        // REGISTRO 0150: TABELA DE CADASTRO DO PARTICIPANTE
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO 0180: IDENTIFICA��O DO RELACIONAMENTO COM O PARTICIPANTE
        // Implementado a crit�rio do Participante do T2Ti ERP
    }

    //Bloco I - Lan�amentos Cont�beis
    private void geraBlocoI() throws Exception {
        // REGISTRO I001: ABERTURA DO BLOCO I
        sped.getBlocoI().getRegistroI001().setIndDad(0);

        // REGISTRO I010: IDENTIFICA��O DA ESCRITURA��O CONT�BIL
        sped.getBlocoI().getRegistroI010().setIndEsc(formaEscrituracao);
        sped.getBlocoI().getRegistroI010().setCodVerLc(versaoLayout);

        // REGISTRO I012: LIVROS AUXILIARES AO DI�RIO
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO I015: IDENTIFICA��O DAS CONTAS DA ESCRITURA��O RESUMIDA A QUE SE REFERE A ESCRITURA��O AUXILIAR
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO I020: CAMPOS ADICIONAIS
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO I030: TERMO DE ABERTURA
        filtros.clear();
        filtros.add(new Filtro(Filtro.AND, "formaEscrituracao", Filtro.IGUAL, formaEscrituracao));
        filtros.add(new Filtro(Filtro.AND, "competencia", Filtro.IGUAL, Biblioteca.mesAno(dataInicial)));
        ContabilLivro contabilLivro = contabilLivroDao.getBean(ContabilLivro.class, filtros);

        if (contabilLivro != null) {
            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "contabilLivro", Filtro.IGUAL, contabilLivro));
            filtros.add(new Filtro(Filtro.AND, "aberturaEncerramento", Filtro.IGUAL, "A"));
            ContabilTermo contabilTermo = contabilTermoDao.getBean(ContabilTermo.class, filtros);
            if (contabilTermo == null) {
                throw new Exception("Termo de Abertura n�o encontrado");
            }

            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "empresa", Filtro.IGUAL, empresa));
            RegistroCartorio registroCartorio = registroCartorioDao.getBean(RegistroCartorio.class, filtros);
            if (registroCartorio == null) {
                throw new Exception("Registro em Cart�rio n�o encontrado");
            }

            sped.getBlocoI().getRegistroI030().setNumOrd(contabilTermo.getNumeroRegistro());
            sped.getBlocoI().getRegistroI030().setNatLivr(contabilLivro.getDescricao());
            sped.getBlocoI().getRegistroI030().setNome(empresa.getRazaoSocial());
            sped.getBlocoI().getRegistroI030().setNire(registroCartorio.getNire());
            sped.getBlocoI().getRegistroI030().setCnpj(empresa.getCnpj());
            sped.getBlocoI().getRegistroI030().setDtArq(registroCartorio.getDataRegistro());
            sped.getBlocoI().getRegistroI030().setDescMun(endereco.getCidade());
        }

        // REGISTRO I050: PLANO DE CONTAS
        filtros.clear();
        filtros.add(new Filtro(Filtro.AND, "empresa", Filtro.IGUAL, empresa));
        PlanoConta planoConta = planoContaDao.getBean(PlanoConta.class, filtros);
        
        List<ContabilConta> listaContabilConta = new ArrayList<>();
        if (planoConta != null) {
            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "planoConta", Filtro.IGUAL, planoConta));
            listaContabilConta = dao.getBeans(ContabilConta.class, filtros);

            RegistroI050 registroI050;
            for (int i = 0; i < listaContabilConta.size(); i++) {
                registroI050 = new RegistroI050();
                registroI050.setDtAlt(listaContabilConta.get(i).getDataInclusao());
                registroI050.setCodNat(listaContabilConta.get(i).getCodigoEfd());
                registroI050.setIndCta(listaContabilConta.get(i).getTipo());
                String classificacao[] = listaContabilConta.get(i).getClassificacao().split(".");
                registroI050.setNivel(String.valueOf(classificacao.length));
                registroI050.setCodCta(listaContabilConta.get(i).getClassificacao());
                registroI050.setCodCtaSup("");
                registroI050.setCta(listaContabilConta.get(i).getDescricao());

                // REGISTRO I051: PLANO DE CONTAS REFERENCIAL
                /*
                 Observa��o: A partir da vers�o 3.X e altera��es posteriores do PVA do Sped Cont�bil, n�o haver� o plano de
                 contas referencial da RFB . Portanto, para as empresas que utilizavam esse plano, n�o ser� necess�rio o preenchimento
                 do registro I051.

                 Fonte: Manual de Orienta��o da ECD
                 */
                //registroI050.getRegistroi051List().add(registroI051);
                sped.getBlocoI().getListaRegistroI050().add(registroI050);
            }
        }

        // REGISTRO I052: INDICA��O DOS C�DIGOS DE AGLUTINA��O
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO I075: TABELA DE HIST�RICO PADRONIZADO
        filtros.clear();
        filtros.add(new Filtro(Filtro.AND, "empresa", Filtro.IGUAL, empresa));
        List<ContabilHistorico> contabilHistorico = contabilHistoricoDao.getBeans(ContabilHistorico.class, filtros);
        RegistroI075 registroI075;
        for (int i = 0; i < contabilHistorico.size(); i++) {
            registroI075 = new RegistroI075();

            registroI075.setCodHist(contabilHistorico.get(i).getId().toString());
            registroI075.setDescrHist(contabilHistorico.get(i).getDescricao());

            sped.getBlocoI().getListaRegistroI075().add(registroI075);
        }

        // REGISTRO I100: CENTRO DE CUSTOS
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO I150: SALDOS PERI�DICOS � IDENTIFICA��O DO PER�ODO
        RegistroI150 registroI150 = new RegistroI150();
        registroI150.setDtIni(dataInicial);
        registroI150.setDtFin(dataFinal);

        // REGISTRO I151: Hash dos Arquivos que Cont�m as Fichas de Lan�amento Utilizadas no Per�odo
        // Implementado a crit�rio do Participante do T2Ti ERP
        BigDecimal creditos;
        BigDecimal debitos;
        BigDecimal saldo;
        ViewSpedI155VoId i155Credito;
        ViewSpedI155VoId i155Debito;
        for (int i = 0; i < listaContabilConta.size(); i++) {
            // REGISTRO I155: DETALHE DOS SALDOS PERI�DICOS
            //busca o saldo anterior
            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.mesAno", Filtro.IGUAL, Biblioteca.mesAno(Biblioteca.mesAnterior(dataInicial))));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.idContabilConta", Filtro.IGUAL, listaContabilConta.get(i).getId()));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.tipo", Filtro.IGUAL, "C"));
            i155Credito = viewSpedI155VoIdDao.getBean(ViewSpedI155VoId.class, filtros);
            if (i155Credito != null) {
                creditos = i155Credito.getViewSpedI155VO().getSomaValor();
            } else {
                creditos = BigDecimal.ZERO;
            }

            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.mesAno", Filtro.IGUAL, Biblioteca.mesAno(Biblioteca.mesAnterior(dataInicial))));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.idContabilConta", Filtro.IGUAL, listaContabilConta.get(i).getId()));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.tipo", Filtro.IGUAL, "D"));
            i155Debito = viewSpedI155VoIdDao.getBean(ViewSpedI155VoId.class, filtros);
            if (i155Debito != null) {
                debitos = i155Credito.getViewSpedI155VO().getSomaValor();
            } else {
                debitos = BigDecimal.ZERO;
            }

            saldo = creditos.subtract(debitos);

            RegistroI155 registroI155 = new RegistroI155();
            registroI155.setCodCta(listaContabilConta.get(i).getClassificacao());
            registroI155.setCodCcus("");
            registroI155.setVlSldIni(saldo);
            if (saldo.compareTo(BigDecimal.ZERO) == -1) {
                registroI155.setIndDcIni("D");
            } else {
                registroI155.setIndDcIni("C");
            }

            //busca o saldo atual
            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.mesAno", Filtro.IGUAL, Biblioteca.mesAno(dataInicial)));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.idContabilConta", Filtro.IGUAL, listaContabilConta.get(i).getId()));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.tipo", Filtro.IGUAL, "C"));
            i155Credito = viewSpedI155VoIdDao.getBean(ViewSpedI155VoId.class, filtros);
            if (i155Credito != null) {
                creditos = i155Credito.getViewSpedI155VO().getSomaValor();
            } else {
                creditos = BigDecimal.ZERO;
            }

            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.mesAno", Filtro.IGUAL, Biblioteca.mesAno(dataInicial)));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.idContabilConta", Filtro.IGUAL, listaContabilConta.get(i).getId()));
            filtros.add(new Filtro(Filtro.AND, "viewSpedI155VO.tipo", Filtro.IGUAL, "D"));
            i155Debito = viewSpedI155VoIdDao.getBean(ViewSpedI155VoId.class, filtros);
            if (i155Debito != null) {
                debitos = i155Credito.getViewSpedI155VO().getSomaValor();
            } else {
                debitos = BigDecimal.ZERO;
            }

            saldo = creditos.subtract(debitos);

            registroI155.setVlDeb(debitos);
            registroI155.setVlCred(creditos);
            registroI155.setVlSldFin(saldo);
            if (saldo.compareTo(BigDecimal.ZERO) == -1) {
                registroI155.setIndDcFin("D");
            } else {
                registroI155.setIndDcFin("C");
            }

            registroI150.getRegistroi155List().add(registroI155);

            // REGISTRO I157: TRANSFER�NCIA DE SALDOS DE PLANO DE CONTAS ANTERIOR
            // Implementado a crit�rio do Participante do T2Ti ERP
        }
        sped.getBlocoI().getListaRegistroI150().add(registroI150);

        // REGISTRO I200: LAN�AMENTO CONT�BIL
        filtros.clear();
        filtros.add(new Filtro(Filtro.AND, "dataLancamento", Filtro.MAIOR_OU_IGUAL, dataInicial));
        filtros.add(new Filtro(Filtro.AND, "dataLancamento", Filtro.MENOR_OU_IGUAL, dataFinal));
        List<ContabilLancamentoCabecalho> listaLancamentoCabecalho = contabilLancamentoCabecalhoDao.getBeans(ContabilLancamentoCabecalho.class, filtros);
        RegistroI200 registroI200;
        RegistroI250 registroI250;
        for (int i = 0; i < listaLancamentoCabecalho.size(); i++) {
            registroI200 = new RegistroI200();
            registroI200.setNumLcto(String.valueOf(listaLancamentoCabecalho.get(i).getId()));
            registroI200.setDtLcto(listaLancamentoCabecalho.get(i).getDataLancamento());
            registroI200.setVlLcto(listaLancamentoCabecalho.get(i).getValor());
            registroI200.setIndLcto("N");

            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "contabilLancamentoCabecalho", Filtro.IGUAL, listaLancamentoCabecalho.get(i)));
            List<ContabilLancamentoDetalhe> lancamentoDetalhe = contabilLancamentoDetalheDao.getBeans(ContabilLancamentoDetalhe.class, filtros);
            for (int j = 0; j < lancamentoDetalhe.size(); j++) {
                registroI250 = new RegistroI250();
                registroI250.setCodCta(lancamentoDetalhe.get(j).getContabilConta().getClassificacao());
                //registroI250.setCodCcus("");
                registroI250.setVlDc(lancamentoDetalhe.get(j).getValor());
                registroI250.setIndDc(lancamentoDetalhe.get(j).getTipo());
                //registroI250.setNumArq("");
                registroI250.setCodHistPad(String.valueOf(lancamentoDetalhe.get(j).getContabilHistorico().getId()));
                registroI250.setHist(lancamentoDetalhe.get(j).getHistorico());
                //registroI250.setCodPart("");

                registroI200.getRegistroi250List().add(registroI250);
            }

            sped.getBlocoI().getListaRegistroI200().add(registroI200);
        }

        // REGISTRO I300: BALANCETES DI�RIOS � IDENTIFICA��O DA DATA
        // REGISTRO I310: DETALHES DO BALANCETE DI�RIO
        // Implementados a crit�rio do Participante do T2Ti ERP
        // REGISTRO I350: SALDOS DAS CONTAS DE RESULTADO ANTES DO ENCERRAMENTO � IDENTIFICA��O DA DATA
        // REGISTRO I355: DETALHES DOS SALDOS DAS CONTAS DE RESULTADO ANTES DO ENCERRAMENTO
        // Implementados a crit�rio do Participante do T2Ti ERP
        // REGISTRO I500: PAR�METROS DE IMPRESS�O E VISUALIZA��O DO LIVRO RAZ�O AUXILIAR COM LEIAUTE PARAMETRIZ�VEL
        // REGISTRO I510: DEFINI��O DE CAMPOS DO LIVRO RAZ�O AUXILIAR COM LEIAUTE PARAMETRIZ�VEL
        // REGISTRO I550: DETALHES DO LIVRO AUXILIAR COM LEIAUTE PARAMETRIZ�VEL
        // REGISTRO I555: TOTAIS NO LIVRO AUXILIAR COM LEIAUTE PARAMETRIZ�VEL
        // Implementados a crit�rio do Participante do T2Ti ERP
    }

    //Bloco J - Demonstra��es Cont�beis
    private void geraBlocoJ() throws Exception {
        // REGISTRO J001: ABERTURA DO BLOCO J
        sped.getBlocoJ().getRegistroJ001().setIndDad(0);

        // REGISTRO J005: DEMONSTRA��ES CONT�BEIS
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO J100: BALAN�O PATRIMONIAL
        // Implementado a crit�rio do Participante do T2Ti ERP
        //REGISTRO J150: DEMONSTRA��O DO RESULTADO DO EXERC�CIO
        //Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO J200: TABELA DE HIST�RICO DE FATOS CONT�BEIS QUE MODIFICAM A CONTA LUCROS ACUMULADOS OU A CONTA PREJU�ZOS ACUMULADOS OU TODO O PATRIM�NIO L�QUIDO
        // REGISTRO J210: DLPA � DEMONSTRA��O DE LUCROS OU PREJU�ZOS ACUMULADOS/DMPL � DEMONSTRA��O DE MUTA��ES DO PATRIM�NIO L�QUIDO
        // REGISTRO J215: FATO CONT�BIL QUE ALTERA A CONTA LUCROS ACUMULADOS OU A CONTA PREJU�ZOS ACUMULADOS OU TODO O PATRIM�NIO L�QUIDO
        // Implementados a crit�rio do Participante do T2Ti ERP
        // REGISTRO J310: DEMONSTRA��O DO FLUXO DE CAIXA
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO J410: DEMONSTRA��O DO VALOR ADICIONADO
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO J800: OUTRAS INFORMA��ES
        // Implementado a crit�rio do Participante do T2Ti ERP
        // REGISTRO J900: TERMO DE ENCERRAMENTO
        filtros.clear();
        filtros.add(new Filtro(Filtro.AND, "formaEscrituracao", Filtro.IGUAL, formaEscrituracao));
        filtros.add(new Filtro(Filtro.AND, "competencia", Filtro.IGUAL, Biblioteca.mesAno(dataInicial)));
        ContabilLivro contabilLivro = contabilLivroDao.getBean(ContabilLivro.class, filtros);
        if (contabilLivro != null) {
            filtros.clear();
            filtros.add(new Filtro(Filtro.AND, "contabilLivro", Filtro.IGUAL, contabilLivro));
            filtros.add(new Filtro(Filtro.AND, "aberturaEncerramento", Filtro.IGUAL, "E"));
            ContabilTermo contabilTermo = contabilTermoDao.getBean(ContabilTermo.class, filtros);
            if (contabilTermo == null) {
                throw new Exception("Termo de Encerramento n�o encontrado");
            }

            sped.getBlocoJ().getRegistroJ900().setNumOrd(contabilTermo.getNumeroRegistro());
            sped.getBlocoJ().getRegistroJ900().setNatLivro(contabilLivro.getDescricao());
            sped.getBlocoJ().getRegistroJ900().setNome(empresa.getRazaoSocial());
            sped.getBlocoJ().getRegistroJ900().setDtIniEscr(contabilTermo.getEscrituracaoInicio());
            sped.getBlocoJ().getRegistroJ900().setDtFinEscr(contabilTermo.getEscrituracaoFim());

            // REGISTRO J930: IDENTIFICA��O DOS SIGNAT�RIOS DA ESCRITURA��O
            List<Contador> contadores  = contadorDao.getBeans(Contador.class);
            RegistroJ930 registroJ930;
            for (int i = 0; i < contadores.size(); i++) {
                registroJ930 = new RegistroJ930();
                registroJ930.setIdentNom(contadores.get(i).getNome());
                registroJ930.setIdentCpf(contadores.get(i).getCpf());
                registroJ930.setIdentQualif("CONTADOR");
                registroJ930.setCodAssin("900");
                registroJ930.setIndCrc(contadores.get(i).getInscricaoCrc());

                sped.getBlocoJ().getListaRegistroJ930().add(registroJ930);
            }
        }
    }
	
	public String getFormaEscrituracao() {
		return formaEscrituracao;
	}

	public void setFormaEscrituracao(String formaEscrituracao) {
		this.formaEscrituracao = formaEscrituracao;
	}

	public String getVersaoLayout() {
		return versaoLayout;
	}

	public void setVersaoLayout(String versaoLayout) {
		this.versaoLayout = versaoLayout;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}
