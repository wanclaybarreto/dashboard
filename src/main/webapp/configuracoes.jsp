<%@page import="br.com.wanclaybarreto.dashboard.domain.Usuario"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.FiltroService"%>
<%@page import="br.com.wanclaybarreto.dashboard.util.PeriodUtils"%>
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

			<div class="configBox">
				<div class="config">
					<div class="title">Filtro padrão a ser utilizado na abertura do Dashboard</div>

					<form name="frm-filter" method="POST" action="">
						<%
							for (PeriodUtils period : PeriodUtils.values()) {
								
								if (period.index() == PeriodUtils.values().length) break;
								
							%>
								<label>
									<input type="radio" id="<% out.print(period.index()); %>" name="filtroPadrao" value="<% out.print(period.index()); %>">
									<span><% out.print(period.description()); %></span>
								</label>
							<%}
						%>

						<input type="submit" value="Salvar">
					</form>
					
					<%
						Integer filtroPadrao =
							(request.getParameter("filtroPadrao") != null && !request.getParameter("filtroPadrao").isEmpty()) ?
								Integer.parseInt(request.getParameter("filtroPadrao")) :
									null;
					
						if (filtroPadrao != null) {
							String msg = FiltroService.setIndexFilter(filtroPadrao);
							
							if (msg == null) {
								out.print("<span class=\"msgReturn msgSucess\">Filtro padrão redefinido!</span>");
							} else {
								out.print("<span class=\"msgReturn msgError\">Não foi posível definir o filtro padrão!</span>");
								out.print("<script>console.log("+msg+")</script>");
							}
						}
						
						int indexFilter = FiltroService.getIndexFilter();
						
						if (indexFilter > 0) {
							out.print("<script> document.querySelector(\"form[name='frm-filter'] input[value='"+indexFilter+"']\").checked = true; </script>");
						} else {
							out.print("<script> document.querySelector(\"form[name='frm-filter'] input[value='1']\").checked = true; </script>");
						}
					%>
					
				</div>
			</div>
		</div>
		<!-- CONTEÚDO CENTRAL (final) -->
	</div>

	<script src="js/chart.min.js"></script>
    <script src="js/chartjs-plugin-datalabels.min.js"></script>

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