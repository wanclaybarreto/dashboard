<%@page import="java.math.BigDecimal"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.OrdensServicoService"%>
<%@page import="br.com.wanclaybarreto.dashboard.domain.OrdensServico"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.FiltroService"%>
<%@page import="br.com.wanclaybarreto.dashboard.util.PeriodUtils"%>
<%@page import="br.com.wanclaybarreto.dashboard.domain.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta lang="pt-br" />
	<meta id="viewport" name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1, user-scalable=no" />

	<title>Dashboard</title>

	<!-- FONTE UBUNTU: -->
	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500&display=swap" rel="stylesheet">

	<!-- CSS: -->
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>

<body>
	
	<%
		Usuario usr = (Usuario) session.getAttribute("usuario");
		
		if (usr == null || request.getParameter("logout") != null) {
			session.invalidate();
		 	response.sendRedirect("index.jsp");
		} else {
			
			Integer indiceFiltro =
				(request.getParameter("filtro") != null && !request.getParameter("filtro").isEmpty()) ?
						Integer.parseInt(request.getParameter("filtro")) :
							null;
			
			PeriodUtils period;
			String startDate;
			String endDate;
			
			if (indiceFiltro != null) {
				if (indiceFiltro < PeriodUtils.values().length) {
					period = PeriodUtils.period(indiceFiltro);
					startDate = null;
					endDate = null;
				} else {
					period = PeriodUtils.None;
					startDate = request.getParameter("dataIn");
					endDate = request.getParameter("dataFin");
				}
			} else {
				int indexFilter = FiltroService.getIndexFilter();
				if (indexFilter > 0) {
					period = PeriodUtils.period(indexFilter);
					startDate = null;
					endDate = null;
				} else {
					period = PeriodUtils.Day;
					startDate = null;
					endDate = null;
				}	
			}
			
			String tipoDataBusca =
					(request.getParameter("tipoData") != null && !request.getParameter("tipoData").isEmpty()) ?
							request.getParameter("tipoData") :
								"emissao";
			
			DecimalFormat df = new DecimalFormat("#,###,##0.00");
	        
	        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	        dfs.setDecimalSeparator(',');
	        dfs.setGroupingSeparator('.');
	        
	        df.setDecimalFormatSymbols(dfs);
	%>
	
	<div class="container">
		<!-- MENU (início) -->
		<nav>
			<ul>
				<li>
					<a href="">
						<span class="icon">  </span>
						<span class="title">  </span>
					</a>						
				</li>

				<li>
					<a href="dashboard.jsp" title="Dashboard">
						<span class="icon"> <img src="icons/icon-dashboard.png" /> </span>
						<span class="title"> Dashboard </span>
					</a>
				</li>
				
				<li>
					<a href="ordensdeservico.jsp" title="Ordens de Serviço">
						<span class="icon"> <img src="icons/icon-ordensdeservico.png" /> </span>
						<span class="title"> Ordens de Serviço </span>
					</a>
				</li>

				<li>
					<a href="configuracoes.jsp" title="Configurações">
						<span class="icon"> <img src="icons/icon-configuracoes.png" /> </span>
						<span class="title"> Configurações </span>
					</a>
				</li>

				<li>
					<a href="suporte.jsp" title="Suporte">
						<span class="icon"> <img src="icons/icon-suporte.png" /> </span>
						<span class="title"> Suporte </span>
					</a>
				</li>
				
				<!-- Ao clicar nesse item (logout) no menu, o JS (arquivo script.js) popula o input logout e realiza o submit do form frmLogout, que é tratado no início do próprio JSP. -->
				<li>
					<a href="" id="logout" title="Sair">
						<span class="icon"> <img src="icons/icon-sair.png" /> </span>
						<span class="title"> Sair </span>
					</a>
				</li>
			</ul>
		</nav>
		<!-- MENU (final) -->
		
		<form name="frmLogout" method="POST" action="">
			<input type="text" name="logout" />
		</form>
		
		<!-- CONTEÚDO CENTRAL (início) -->
		<div class="main">
			<!-- BARRA SUPERIOR (início) -->
			<div class="topbar">
				<div class="toggle">
					<div class="line"></div>
					<div class="line"></div>
					<div class="line"></div>
				</div>
				<div class="user"> <% out.print(usr.getNome()); %> </div>
			</div>
			<!-- BARRA SUPERIOR (final) -->
			
			<!-- FILTRO (início) -->
			<div class="periodFilter"></div>
			
			<div class="filterIcon">
				<img src="icons/icon-filtro.png" >
			</div>

			<div class="filters">
				<div class="close">
					<img src="icons/icon-fechar.png" >
				</div>

				<form method="GET" action="">
					<select name="filtro" id="filtro">
						<option></option>
						<%
							for (PeriodUtils periodFilter : PeriodUtils.values()) {%>
								
								<option value="<% out.print(periodFilter.index()); %>"> <% out.print(periodFilter.description()); %> </option>
								
							<%}
						%>
					</select>

					<div class="period">
						<div>
							<label for="dataIn">De </label>
							<input type="date" name="dataIn" />
						</div>

						<div>
							<label for="dataFin"> até</label>
							<input type="date" name="dataFin" />
						</div>
					</div>
					
					<div class="typeDate">
						<label>
							<input type="radio" name="tipoData" value="emissao" checked>
							<span>Por data de emisssão.</span>
						</label>
						<label>
							<input type="radio" name="tipoData" value="fechamento">
							<span>Por data de fechamento.</span>
						</label>
					</div>

					<input type="submit" value="FILTRAR">
				</form>
			</div>
			<!-- FILTRO (final) -->
			
			<!-- ORDENS DE SERVIÇO (início) -->
			<div class="ordensdeservicoBox">
				<%
					List<OrdensServico> listaOrdensServico;
					
					if (tipoDataBusca.equals("emissao")) {
						listaOrdensServico = OrdensServicoService.getListOrdensServicoByDtEmissao(period, startDate, endDate);
					} else {
						listaOrdensServico = OrdensServicoService.getListOrdensServicoByDtFechamento(period, startDate, endDate);
					}
					
					for (OrdensServico ordensServicos : listaOrdensServico) {
				%>
						<div class="ordensdeservico">
							<div class="status"> <% out.print(ordensServicos.getStatus()); %> </div>
		
							<div class="content">
								<div class="payment">
									<span class="title"> Valor à vista: </span>
									<span class="desc"> <% out.print("R$" + df.format(ordensServicos.getValorAVista())); %> </span>
								</div>
								<div class="payment">
									<span class="title">Valor à prazo:</span>
									<span class="desc"> <% out.print("R$" + df.format(ordensServicos.getValorAPrazo())); %> </span>
								</div>
								
								<%
									if(ordensServicos.getValorFinal() != null && ordensServicos.getValorFinal().signum() != 0) {%>
										<div class="payment">
											<span class="title">Valor final:</span>
											<span class="desc"> <% out.print("R$" + df.format(ordensServicos.getValorFinal())); %> </span>
										</div>
									<%}
								%>
								
								<div class="quant">
									<span class="title">Quantidade:</span>
									<span class="desc"> <% out.print(ordensServicos.getQuant()); %> </span>
								</div>
							</div>
						</div>
				
				<%
					}
				%>
			</div>
			<!-- ORDENS DE SERVIÇO (final) -->

			<div style="color: #eee;">_</div>
		</div>
	</div>

	<script src="js/chart.min.js"></script>
    <script src="js/chartjs-plugin-datalabels.min.js"></script>

	<script>
		<%
			if (period.index() < PeriodUtils.values().length) {%>
				
				document.querySelector(".periodFilter").innerHTML = "<% out.print(period.description().toUpperCase() + " (" + (tipoDataBusca.equals("emissao") ? "E" : "F") +")"); %>";
				
			<%} else {
				
				String[] initialDate = startDate.split("-");
				String[] finalDate = endDate.split("-");
				
				String startDateFormated = initialDate[2] + "/" + initialDate[1] + "/" + initialDate[0];
				String endDateFormated = finalDate[2] + "/" + finalDate[1] + "/" + finalDate[0];
				
				%> document.querySelector(".periodFilter").innerHTML = "" <% out.print("+\"De " + startDateFormated + " até " + endDateFormated + " (" + (tipoDataBusca.equals("emissao") ? "E" : "F") +")" + "\""); %> ; <%
				
			}
		%>
	</script>
	
	<!-- Bloqueando pop-up de solicitação de reenvio de formulário: -->
	<script type="text/javascript">
		if ( window.history.replaceState ) {
		  window.history.replaceState( null, null, window.location.href );
		}
	</script>
	
	<script src="js/script.js"></script>
	
	<%
		}
	%>

</body>
</html>