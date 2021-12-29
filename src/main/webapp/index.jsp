<%@page import="br.com.wanclaybarreto.dashboard.application.UsuarioService"%>
<%@page import="br.com.wanclaybarreto.dashboard.util.PairUtils"%>
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

	<div class="entrar">
		<div class="entrar-in">
			<img src="images/user.png">

			<form action="" method="POST">
				<input type="text"     name="usuario" placeholder="Usuário" required />
				<input type="password" name="senha"   placeholder="Senha" required />
				<input type="submit" value="Entrar" />
			</form>
			
			<%
                session.setMaxInactiveInterval(60*60*12);
                                	
               	Usuario u = (Usuario) session.getAttribute("usuario");
    			
    			if (u != null) {
    				response.sendRedirect("dashboard.jsp");
    			}	
               	
               	String nome  = request.getParameter("usuario");
               	String senha = request.getParameter("senha");
               	
               	if(nome != null && senha != null && !nome.isEmpty() && !senha.isEmpty()) {
               		
               		PairUtils<Usuario, String> pairUsrMsg = UsuarioService.entrar(nome, senha);
               		
               		if (pairUsrMsg.getA() == null) {
               			
               			out.print("<div class=\"entrar-return\">");
               			out.print(pairUsrMsg.getB());
               			out.print("</div>");
               			
               			out.print("<script> document.getElementsByName('usuario')[0].value = '"+nome+"' </script>");
               			
               		} else {
               			
               			session.setAttribute("usuario", pairUsrMsg.getA());
               			
               			response.sendRedirect("dashboard.jsp");
               			
               		}
               	}
            %>
		</div>
	</div>
	
	<!-- Bloqueando pop-up de solicitação de reenvio de formulário: -->
	<script type="text/javascript">
		if ( window.history.replaceState ) {
		  window.history.replaceState( null, null, window.location.href );
		}
	</script>

</body>
</html>