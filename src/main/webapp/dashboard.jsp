<%@page import="br.com.wanclaybarreto.dashboard.application.ContasService"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="br.com.wanclaybarreto.dashboard.domain.Usuario"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.FiltroService"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="br.com.wanclaybarreto.dashboard.domain.ProdutoVendido"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.ProdutoVendidoService"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="br.com.wanclaybarreto.dashboard.util.ColorRgbaUtils"%>
<%@page import="br.com.wanclaybarreto.dashboard.application.VendasService"%>
<%@page import="br.com.wanclaybarreto.dashboard.domain.Vendas"%>
<%@page import="java.util.List"%>
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

					<input type="submit" value="FILTRAR">
				</form>
			</div>
			<!-- FILTRO (final) -->
			
			<!-- CARDS (início) -->
			<div class="cardBox">
				
				<%
					BigDecimal valorVendas = VendasService.getValorVendas(period, startDate, endDate);
					Integer quantVendas = VendasService.getQuantVendas(period, startDate, endDate);
				%>
				
				<div class="card">
					<div class="content">
						<div class="numbers colorOrange">
							<div class="a"> <% out.print(valorVendas != null ? "R$ " + df.format(valorVendas) : "-"); %> </div>
							<div class="b"> <% out.print(quantVendas != null ? "Quant.: " + quantVendas : "-"); %> </div>
						</div>
						<span class="icon"> <img src="icons/icon-vendas.png" /></span>
					</div>
					<div class="title bgOrange">
						<span class="name"> Vendas </span>
						<span class="icon"></span>
					</div>
				</div>
				
				<%
					BigDecimal valorRecebimentos = ContasService.getValorContasRecebidas(period, startDate, endDate);
					Integer quantRecebimentos = ContasService.getQuantContasRecebidas(period, startDate, endDate);
				%>
				
				<div class="card">
					<div class="content">
						<div class="numbers colorGreen">
							<div class="a"> <% out.print(valorRecebimentos != null ? "R$ " + df.format(valorRecebimentos) : "-"); %> </div>
							<div class="b"> <% out.print(quantRecebimentos != null ? "Quant.: " + quantRecebimentos : "-"); %> </div>
						</div>
						<span class="icon"> <img src="icons/icon-recebimentos.png" /> </span>
					</div>
					<div class="title bgGreen">
						<span class="name"> Recebimentos </span>
						<span class="icon"></span>
					</div>
				</div>
				
				<%
					BigDecimal valorPagamentos = ContasService.getValorContasPagas(period, startDate, endDate);
					Integer quantPagamentos = ContasService.getQuantContasPagas(period, startDate, endDate);
				%>
				
				<div class="card">
					<div class="content">
						<div class="numbers colorRed">
							<div class="a"> <% out.print(valorPagamentos != null ? "R$ " + df.format(valorPagamentos) : "-"); %> </div>
							<div class="b"> <% out.print(quantPagamentos != null ? "Quant.: " + quantPagamentos : "-"); %> </div>
						</div>
						<span class="icon"> <img src="icons/icon-pagamentos.png" /> </span>
					</div>
					<div class="title bgRed">
						<span class="name"> Pagamentos </span>
						<span class="icon"></span>
					</div>
				</div>
				
				<%
					List<Vendas> listaVendasPorVendedor = VendasService.getListVendasByVendedor(period, startDate, endDate);
					Vendas vendasVendedorDestaque = null;
					
					if (listaVendasPorVendedor != null && !listaVendasPorVendedor.isEmpty()) {
						vendasVendedorDestaque = listaVendasPorVendedor.get(0);
					}
				%>
				
				<div class="card">
					<div class="content">
						<div class="numbers colorBlue">
							<div class="a"> <% out.print(vendasVendedorDestaque != null ? vendasVendedorDestaque.getVendedor().getCodigo() + " - " + vendasVendedorDestaque.getVendedor().getNome() : "-"); %> </div>
							<div class="b"> <% out.print(vendasVendedorDestaque != null ? "R$ " + df.format(vendasVendedorDestaque.getValor()) : "-"); %> </div>
						</div>
						<span class="icon"> <img src="icons/icon-vendedor-destaque.png" /> </span>
					</div>
					<div class="title bgBlue">
						<span class="name"> Vendedor(a) em destaque </span>
						<span class="icon"></span>
					</div>
				</div>
			</div>
			<!-- CARDS (final) -->

			<!-- GRÁFICOS (início) -->
			<div class="graphBox">
				<div class="graph">
					<div class="title"> Vendas por Operador(a) </div>
					<div class="content">
						<canvas id="chart-vendas-por-operador"></canvas>
						<div class="no-graph vendas-por-operador">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>

				<div class="graph">
					<div class="title"> Vendas por Vendedor(a) </div>
					<div class="content">
						<canvas id="chart-vendas-por-vendedor"></canvas>
						<div class="no-graph vendas-por-vendedor">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>

				<div class="graph">
					<div class="title"> Clientes que mais compraram </div>
					<div class="content">
						<canvas id="chart-vendas-por-cliente"></canvas>
						<div class="no-graph vendas-por-cliente">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>

				<div class="graph">
					<div class="title"> Vendas por Formas de Pagamento </div>
					<div class="content">
						<canvas id="chart-vendas-por-forma-pagamento"></canvas>
						<div class="no-graph vendas-por-forma-pagamento">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>
				
				<div class="graph">
					<div class="title"> Produtos mais vendidos </div>
					<div class="content">
						<canvas id="chart-produtos-mais-vendidos"></canvas>
						<div class="no-graph produtos-mais-vendidos">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>
				
				<div class="graph">
					<div class="title"> Horários de pico </div>
					<div class="content">
						<canvas id="chart-horarios-de-pico"></canvas>
						<div class="no-graph horarios-de-pico">
							<img src="images/warning-no-graph.png" />
							<span> Sem dados para gerar este relatório considerando o período especificado! </span>
						</div>
					</div>
				</div>
			</div>
			<!-- GRÁFICOS (início) -->

			<div style="color: #eee;">_</div>
		</div>
		<!-- CONTEÚDO CENTRAL (final) -->
	</div>

	<script src="js/chart.min.js"></script>
    <script src="js/chartjs-plugin-datalabels.min.js"></script>
	
	<!-- POPULANDO OS GRÁFICOS (início) -->
	<script>
		function generateColor(opacidade = 0.5) {
		    let r = Math.random() * 255;
		    let g = Math.random() * 255;
		    let b = Math.random() * 255;
		    
		    return "rgba("+r+", "+g+", "+b+", "+opacidade+")";
		}
	
		function drawCharts() {
			
			<%
				List<Vendas> listaVendasPorOperador = VendasService.getListVendasByOperador(period, startDate, endDate);
				
				if (listaVendasPorOperador != null && !listaVendasPorOperador.isEmpty()) {
			%>

			var chartVendasPorOperador = new Chart (
				document.getElementById("chart-vendas-por-operador").getContext("2d"),
				
				{
					type: "bar",
					
					data: {
						labels: [<%
							for (int i = 0; i < listaVendasPorOperador.size(); i++) {
								out.print("\"" + listaVendasPorOperador.get(i).getOperador() +
										  " (qnt.: "+ listaVendasPorOperador.get(i).getQuant() +" |" +
										  " % da qnt.: " + listaVendasPorOperador.get(i).getPercentualQuant().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") +"%)\"");
								
								if (i < listaVendasPorOperador.size() - 1) {
									out.print(", ");
								}
							}
						%>],

						datasets: [
							{
								label: "Valor",
								backgroundColor: [<%
				                    for (int i = 0; i < listaVendasPorOperador.size(); i++) {
										out.print(ColorRgbaUtils.gerenateColor());
										
										if (i < listaVendasPorOperador.size() - 1) {
											out.print(", ");
										}
									}
								%>],
								data: [<%
							        for (int i = 0; i < listaVendasPorOperador.size(); i++) {
										out.print(listaVendasPorOperador.get(i).getValor().setScale(2, RoundingMode.HALF_UP));
										
										if (i < listaVendasPorOperador.size() - 1) {
											out.print(", ");
										}
									}
								%>]
							}
						]
					},

					options: {
						layout: {
				            padding: {
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value) + "\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},

						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}],
							xAxes: [{
								ticks: {
									display: false
								},
							}]
						},

						legend: {
							position: "bottom",

							labels: {
								generateLabels: (chart) => {
									const { data } = chart;
									
									if (data.labels.length && data.datasets.length) {
										return data.labels.map((label, i) => {
										const meta = chart.getDatasetMeta(0);
										const ds = data.datasets[0];
										const arc = meta.data[i];
										const custom = (arc && arc.custom) || {};
										const { getValueAtIndexOrDefault } = Chart.helpers;
										const arcOpts = chart.options.elements.arc;
										const fill = custom.backgroundColor ? custom.backgroundColor : getValueAtIndexOrDefault(ds.backgroundColor, i, arcOpts.backgroundColor);
										const stroke = custom.borderColor ? custom.borderColor : getValueAtIndexOrDefault(ds.borderColor, i, arcOpts.borderColor);
										const bw = custom.borderWidth ? custom.borderWidth : getValueAtIndexOrDefault(ds.borderWidth, i, arcOpts.borderWidth);
										const value = chart.config.data.datasets[arc._datasetIndex].data[arc._index];
										
										return {
											text: label,
											fillStyle: fill,
											strokeStyle: stroke,
											lineWidth: bw,
											hidden: Number.isNaN(ds.data[i]) || meta.data[i].hidden,
											index: i,
										};
										});
									}
									return [];
								},

								fontColor: "#000"
							},

							onClick: (event, legendItem) => {}
						}
					}
				}
			);
			
			<%
				} else {%>
				
					document.getElementById("chart-vendas-por-operador").style.display = "none";
					document.querySelector(".no-graph.vendas-por-operador").style.display = "flex";
					
				<%}
			%>
			
			
			<%
				//Lista de vendas por vendedor declarada na parte de Cards do código HTML.
			
				if (listaVendasPorVendedor != null && !listaVendasPorVendedor.isEmpty()) { 
			%>
			
			var chartVendasPorVendedor = new Chart (
				document.getElementById("chart-vendas-por-vendedor").getContext("2d"),
				
				{
					type: "bar",
					
					data: {
						labels: [<%
							for (int i = 0; i < listaVendasPorVendedor.size(); i++) {
								out.print("\"" + listaVendasPorVendedor.get(i).getVendedor().getNome() +
										  " (qnt.: "+ listaVendasPorVendedor.get(i).getQuant() +" |" +
										  " % da qnt.: " + listaVendasPorVendedor.get(i).getPercentualQuant().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") +"%)\"");
								
								if (i < listaVendasPorVendedor.size() - 1) {
									out.print(", ");
								}
							}
						%>],

						datasets: [
							{
								label: "Valor",
								backgroundColor: [<%
				                    for (int i = 0; i < listaVendasPorVendedor.size(); i++) {
										out.print(ColorRgbaUtils.gerenateColor());
										
										if (i < listaVendasPorVendedor.size() - 1) {
											out.print(", ");
										}
									}
								%>],
								data: [<%
							        for (int i = 0; i < listaVendasPorVendedor.size(); i++) {
										out.print(listaVendasPorVendedor.get(i).getValor().setScale(2, RoundingMode.HALF_UP));
										
										if (i < listaVendasPorVendedor.size() - 1) {
											out.print(", ");
										}
									}
								%>]
							}
						]
					},

					options: {
						layout: {
				            padding: {
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value) + "\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},

						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}],
							xAxes: [{
								ticks: {
									display: false
								},
							}]
						},

						legend: {
							position: "bottom",

							labels: {
								generateLabels: (chart) => {
									const { data } = chart;
									
									if (data.labels.length && data.datasets.length) {
										return data.labels.map((label, i) => {
										const meta = chart.getDatasetMeta(0);
										const ds = data.datasets[0];
										const arc = meta.data[i];
										const custom = (arc && arc.custom) || {};
										const { getValueAtIndexOrDefault } = Chart.helpers;
										const arcOpts = chart.options.elements.arc;
										const fill = custom.backgroundColor ? custom.backgroundColor : getValueAtIndexOrDefault(ds.backgroundColor, i, arcOpts.backgroundColor);
										const stroke = custom.borderColor ? custom.borderColor : getValueAtIndexOrDefault(ds.borderColor, i, arcOpts.borderColor);
										const bw = custom.borderWidth ? custom.borderWidth : getValueAtIndexOrDefault(ds.borderWidth, i, arcOpts.borderWidth);
										const value = chart.config.data.datasets[arc._datasetIndex].data[arc._index];
										
										return {
											text: label,
											fillStyle: fill,
											strokeStyle: stroke,
											lineWidth: bw,
											hidden: Number.isNaN(ds.data[i]) || meta.data[i].hidden,
											index: i,
										};
										});
									}
									return [];
								},

								fontColor: "#000"
							},

							onClick: (event, legendItem) => {}
						}
					}
				}
			);
			
			<%
				} else {%>
					
					document.getElementById("chart-vendas-por-vendedor").style.display = "none";
					document.querySelector(".no-graph.vendas-por-vendedor").style.display = "flex";
					
				<%}
			%>
			
			<%
				List<Vendas> listaVendasPorCliente = VendasService.getListVendasByCliente(period, startDate, endDate);
				
				if (listaVendasPorCliente != null && !listaVendasPorCliente.isEmpty()) {
			%>
			
			var chartVendasPorCliente = new Chart (
				document.getElementById("chart-vendas-por-cliente").getContext("2d"),
				
				{
					type: "bar",
					
					data: {
						labels: [<%
			                for (int i = 0; i < listaVendasPorCliente.size(); i++) {
			                    out.print("\"" + listaVendasPorCliente.get(i).getCliente().getNome() +
			                              " (qnt.: "+ listaVendasPorCliente.get(i).getQuant() + " | " +
			                              " % da qnt.: " + listaVendasPorCliente.get(i).getPercentualQuant().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") +"%)\"");
			                    
			                    if (i < listaVendasPorCliente.size() - 1) {
			                        out.print(", ");
			                    }
			                }
			            %>],

						datasets: [
							{
								label: "Valor",
								backgroundColor: [<%
			                        for (int i = 0; i < listaVendasPorCliente.size(); i++) {
			                            out.print(ColorRgbaUtils.gerenateColor());
			                            
			                            if (i < listaVendasPorCliente.size() - 1) {
			                                out.print(", ");
			                            }
			                        }
			                    %>],
								data: [<%
			                        for (int i = 0; i < listaVendasPorCliente.size(); i++) {
			                            out.print(listaVendasPorCliente.get(i).getValor().setScale(2, RoundingMode.HALF_UP));
			                            
			                            if (i < listaVendasPorCliente.size() - 1) {
			                                out.print(", ");
			                            }
			                        }
			                    %>]
							}
						]
					},

					options: {
						layout: {
				            padding: {
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value) + "\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},

						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}],
							xAxes: [{
								ticks: {
									display: false
								},
							}]
						},

						legend: {
							position: "bottom",

							labels: {
								generateLabels: (chart) => {
									const { data } = chart;
									
									if (data.labels.length && data.datasets.length) {
										return data.labels.map((label, i) => {
										const meta = chart.getDatasetMeta(0);
										const ds = data.datasets[0];
										const arc = meta.data[i];
										const custom = (arc && arc.custom) || {};
										const { getValueAtIndexOrDefault } = Chart.helpers;
										const arcOpts = chart.options.elements.arc;
										const fill = custom.backgroundColor ? custom.backgroundColor : getValueAtIndexOrDefault(ds.backgroundColor, i, arcOpts.backgroundColor);
										const stroke = custom.borderColor ? custom.borderColor : getValueAtIndexOrDefault(ds.borderColor, i, arcOpts.borderColor);
										const bw = custom.borderWidth ? custom.borderWidth : getValueAtIndexOrDefault(ds.borderWidth, i, arcOpts.borderWidth);
										const value = chart.config.data.datasets[arc._datasetIndex].data[arc._index];
										
										return {
											text: label,
											fillStyle: fill,
											strokeStyle: stroke,
											lineWidth: bw,
											hidden: Number.isNaN(ds.data[i]) || meta.data[i].hidden,
											index: i,
										};
										});
									}
									return [];
								},

								fontColor: "#000"
							},

							onClick: (event, legendItem) => {}
						}
					}
				}
			);
			
			<%
				} else {%>
					
					document.getElementById("chart-vendas-por-cliente").style.display = "none";
					document.querySelector(".no-graph.vendas-por-cliente").style.display = "flex";
					
				<%}
			%>
			
			<%
				List<Vendas> listaVendasPorFormaPagamento = VendasService.getListVendasByFormaPagamento(period, startDate, endDate);
				
				if (listaVendasPorFormaPagamento != null && !listaVendasPorFormaPagamento.isEmpty()) {
			%>
			
			var chartVendasPorFormaPagamento = new Chart (
				document.getElementById("chart-vendas-por-forma-pagamento").getContext('2d'),
				
				{
					type: "bar",
					
					data: {
						labels: [<%
			                for (int i = 0; i < listaVendasPorFormaPagamento.size(); i++) {
			                    out.print("\"" + listaVendasPorFormaPagamento.get(i).getFormaPagamento() + "\"");
			                    
			                    if (i < listaVendasPorFormaPagamento.size() - 1) {
			                        out.print(", ");
			                    }
			                }
			            %>],

						datasets: [
							{
								label: "Valor",
								backgroundColor: [<%
			                        for (int i = 0; i < listaVendasPorFormaPagamento.size(); i++) {
			                            out.print(ColorRgbaUtils.gerenateColor());
			                            
			                            if (i < listaVendasPorFormaPagamento.size() - 1) {
			                                out.print(", ");
			                            }
			                        }
			                    %>],
								data: [<%
			                        for (int i = 0; i < listaVendasPorFormaPagamento.size(); i++) {
			                            out.print(listaVendasPorFormaPagamento.get(i).getValor().setScale(2, RoundingMode.HALF_UP));
			                            
			                            if (i < listaVendasPorFormaPagamento.size() - 1) {
			                                out.print(", ");
			                            }
			                        }
			                    %>]
							}
						]
					},

					options: {
						layout: {
				            padding: {
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value) + "\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},

						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}],
							xAxes: [{
								ticks: {
									display: false
								},
							}]
						},

						legend: {
							position: "bottom",

							labels: {
								generateLabels: (chart) => {
									const { data } = chart;
									
									if (data.labels.length && data.datasets.length) {
										return data.labels.map((label, i) => {
										const meta = chart.getDatasetMeta(0);
										const ds = data.datasets[0];
										const arc = meta.data[i];
										const custom = (arc && arc.custom) || {};
										const { getValueAtIndexOrDefault } = Chart.helpers;
										const arcOpts = chart.options.elements.arc;
										const fill = custom.backgroundColor ? custom.backgroundColor : getValueAtIndexOrDefault(ds.backgroundColor, i, arcOpts.backgroundColor);
										const stroke = custom.borderColor ? custom.borderColor : getValueAtIndexOrDefault(ds.borderColor, i, arcOpts.borderColor);
										const bw = custom.borderWidth ? custom.borderWidth : getValueAtIndexOrDefault(ds.borderWidth, i, arcOpts.borderWidth);
										const value = chart.config.data.datasets[arc._datasetIndex].data[arc._index];
										
										return {
											text: label,
											fillStyle: fill,
											strokeStyle: stroke,
											lineWidth: bw,
											hidden: Number.isNaN(ds.data[i]) || meta.data[i].hidden,
											index: i,
										};
										});
									}
									return [];
								},

								fontColor: "#000"
							},

							onClick: (event, legendItem) => {}
						}
					}
				}
			);
			
			<%
				} else {%>
					
					document.getElementById("chart-vendas-por-forma-pagamento").style.display = "none";
					document.querySelector(".no-graph.vendas-por-forma-pagamento").style.display = "flex";
					
				<%}
			%>
			
			<%
				List<ProdutoVendido> listaProdutosMaisVendidos = ProdutoVendidoService.getListProdutosMaisVendidos(period, startDate, endDate);
				
				if (listaProdutosMaisVendidos != null && !listaProdutosMaisVendidos.isEmpty()) {
			%>
			
			var chartProdutosMaisVendidos = new Chart (
				document.getElementById("chart-produtos-mais-vendidos").getContext("2d"),
				
				{
					type: "line",
					
					data: {
						labels: [<%
							for (int i = 0; i < listaProdutosMaisVendidos.size(); i++) {
								out.print("\"" + listaProdutosMaisVendidos.get(i).getId() + " - " + listaProdutosMaisVendidos.get(i).getNome() +
										  " (subtotal: R$ "+ df.format(listaProdutosMaisVendidos.get(i).getSubtotal()) + " |" +
										  " % do subtotal: " + listaProdutosMaisVendidos.get(i).getPercentualSubtotal().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") +"%)\"");
								
								if (i < listaProdutosMaisVendidos.size() - 1) {
									out.print(", ");
								}
							}
						%>],

						datasets: [
							{
								label: "Quant.",
								backgroundColor: [<%
				                    for (int i = 0; i < listaProdutosMaisVendidos.size(); i++) {
										out.print(ColorRgbaUtils.gerenateColor());
										
										if (i < listaProdutosMaisVendidos.size() - 1) {
											out.print(", ");
										}
									}
								%>],
								data: [<%
							        for (int i = 0; i < listaProdutosMaisVendidos.size(); i++) {
										out.print(listaProdutosMaisVendidos.get(i).getQuant().setScale(2, RoundingMode.HALF_UP));
										
										if (i < listaProdutosMaisVendidos.size() - 1) {
											out.print(", ");
										}
									}
								%>]
							}
						]
					},

					options: {
						layout: {
				            padding: {
				                right: 50,
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value).substring(3) + "\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},

						scales: {
							yAxes: [{
								ticks: {
									beginAtZero: true
								}
							}],
							xAxes: [{
								ticks: {
									display: false
								},
							}]
						},

						legend: {
							position: "bottom",

							labels: {
								generateLabels: (chart) => {
									const { data } = chart;
									
									if (data.labels.length && data.datasets.length) {
										return data.labels.map((label, i) => {
										const meta = chart.getDatasetMeta(0);
										const ds = data.datasets[0];
										const arc = meta.data[i];
										const custom = (arc && arc.custom) || {};
										const { getValueAtIndexOrDefault } = Chart.helpers;
										const arcOpts = chart.options.elements.arc;
										const fill = custom.backgroundColor ? custom.backgroundColor : getValueAtIndexOrDefault(ds.backgroundColor, i, arcOpts.backgroundColor);
										const stroke = custom.borderColor ? custom.borderColor : getValueAtIndexOrDefault(ds.borderColor, i, arcOpts.borderColor);
										const bw = custom.borderWidth ? custom.borderWidth : getValueAtIndexOrDefault(ds.borderWidth, i, arcOpts.borderWidth);
										const value = chart.config.data.datasets[arc._datasetIndex].data[arc._index];
										
										return {
											text: label,
											fillStyle: fill,
											strokeStyle: stroke,
											lineWidth: bw,
											hidden: Number.isNaN(ds.data[i]) || meta.data[i].hidden,
											index: i,
										};
										});
									}
									return [];
								},

								fontColor: "#000"
							},

							onClick: (event, legendItem) => {}
						}
					}
				}
			);
			
			<%
				} else {%>
					
					document.getElementById("chart-produtos-mais-vendidos").style.display = "none";
					document.querySelector(".no-graph.produtos-mais-vendidos").style.display = "flex";
					
				<%}
			%>
			
			<%
				List<Vendas> listaVendasPorHoraPico = VendasService.getListVendasByHoraPico(period, startDate, endDate);
				
				if (listaVendasPorHoraPico != null && !listaVendasPorHoraPico.isEmpty()) {
			%>
	
			var chartVendasPorOperador = new Chart (
				document.getElementById("chart-horarios-de-pico").getContext("2d"),
				
				{
					type: "horizontalBar",
					
					data: {
						labels: [<%
							for (int i = 0; i < listaVendasPorHoraPico.size(); i++) {
								out.print("\"" + listaVendasPorHoraPico.get(i).getHora() + "\"");
								
								if (i < listaVendasPorHoraPico.size() - 1) {
									out.print(", ");
								}
							}
						%>],
	
						datasets: [
							{
								label: "Vendas",
								backgroundColor: [<%
				                    for (int i = 0; i < listaVendasPorHoraPico.size(); i++) {
										out.print(ColorRgbaUtils.gerenateColor());
										
										if (i < listaVendasPorHoraPico.size() - 1) {
											out.print(", ");
										}
									}
								%>],
								data: [<%
							        for (int i = 0; i < listaVendasPorHoraPico.size(); i++) {
										out.print(listaVendasPorHoraPico.get(i).getQuant());
										
										if (i < listaVendasPorHoraPico.size() - 1) {
											out.print(", ");
										}
									}
								%>]
							}
						]
					},
	
					options: {
						layout: {
				            padding: {
				            	right: 50,
				                top: 15
				            }
				        },
				        
						plugins: {
							datalabels: {
								formatter: function (value, ctx) {
					                let sum = 0;
					                let dataArr = ctx.chart.data.datasets[0].data;
					                dataArr.map(data => {
					                    sum += data;
					                });
					                let percentage = (value * 100 / sum).toFixed(2) + "%";
					                
					                return value + " vendas\n(" + percentage.replace(".", ",") + ")";
					            },
								anchor: "end",
								color: "#000",
								font: {
									weight: 'bold'
								},
								backgroundColor: function(context) {
									return context.dataset.backgroundColor;
								},
								borderWidth: 1,
								borderColor: "#000",
								borderRadius: 1
							}
						},
	
						scales: {
							yAxes: [{
								ticks: {
									callback: function(value, index, values) {
					                    return value + "h";
					                }
								}
							}]
						},
	
						legend: {
							display: false
						}
					}
				}
			);
			
			<%
				} else {%>
					
					document.getElementById("chart-horarios-de-pico").style.display = "none";
					document.querySelector(".no-graph.horarios-de-pico").style.display = "flex";
					
				<%}
			%>
		}
		
		<%
			if (period.index() < PeriodUtils.values().length) {%>
				
				document.querySelector(".periodFilter").innerHTML = "<% out.print(period.description().toUpperCase()); %>";
				
			<%} else {
				
				String[] initialDate = startDate.split("-");
				String[] finalDate = endDate.split("-");
				
				String startDateFormated = initialDate[2] + "/" + initialDate[1] + "/" + initialDate[0];
				String endDateFormated = finalDate[2] + "/" + finalDate[1] + "/" + finalDate[0];
				
				%> document.querySelector(".periodFilter").innerHTML = "" <% out.print("+\"De " + startDateFormated + " até " + endDateFormated + "\""); %> ; <%
				
			}
		%>
	</script>
	<!-- POPULANDO OS GRÁFICOS (final) -->
	
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