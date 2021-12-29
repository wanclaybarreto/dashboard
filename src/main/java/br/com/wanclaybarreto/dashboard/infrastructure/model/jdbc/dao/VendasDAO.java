package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.domain.Cliente;
import br.com.wanclaybarreto.dashboard.domain.Vendas;
import br.com.wanclaybarreto.dashboard.domain.Vendedor;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class VendasDAO {
	
	public List<Vendas> listVendasByOperador(String startDate, String endDate) throws PersistenceException {
		
		List<Vendas> listaVendasPorOperador;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    cabecalhovendas.operador,\r\n"
					   + "    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,\r\n"
					   + "    (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO') as valor_total,\r\n"
					   + "    ( (sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) * 100) / (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO')) as percentual_valor,\r\n"
					   + "    count(operador) as quant,\r\n"
					   + "    (select count(operador) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?) as quant_total,\r\n"
					   + "    ((count(operador) / (select count(operador) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?)) * 100) as percentual_quant\r\n"
					   + "from\r\n"
					   + "    cabecalhovendas\r\n"
					   + "where\r\n"
					   + "    cabecalhovendas.situacao = 'FINALIZADA' and\r\n"
					   + "    cabecalhovendas.datavenda between ? and ?\r\n"
					   + "group by\r\n"
					   + "    cabecalhovendas.operador\r\n"
					   + "order by\r\n"
					   + "    valor desc";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, startDate);
			ps.setString(6, endDate);
			ps.setString(7, startDate);
			ps.setString(8, endDate);
			ps.setString(9, startDate);
			ps.setString(10, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaVendasPorOperador = new ArrayList<Vendas>();
			
			while (rs.next()) {
				Vendas v = new Vendas();
				v.setOperador(rs.getString("operador"));
				v.setValor(rs.getBigDecimal("valor"));
				v.setValorTotal(rs.getBigDecimal("valor_total"));
				v.setPercentualValor(rs.getBigDecimal("percentual_valor"));
				v.setQuant(rs.getInt("quant"));
				v.setQuantTotal(rs.getInt("quant_total"));
				v.setPercentualQuant(rs.getBigDecimal("percentual_quant"));
				
				listaVendasPorOperador.add(v);
			}
			
		} catch (Exception e) {
			
			listaVendasPorOperador = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaVendasPorOperador;
		
	}
	
	public List<Vendas> listVendasByVendedor(String startDate, String endDate) throws PersistenceException {
		
		List<Vendas> listaVendasPorVendedor;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    vendedor.codigo as vendedor_cod,\r\n"
					   + "    vendedor.nome as vendedor_nome,\r\n"
					   + "    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,\r\n"
					   + "    (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO') as valor_total,\r\n"
					   + "    ( (sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) * 100) / (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO')) as percentual_valor,\r\n"
					   + "    count(id_vendedor) as quant,\r\n"
					   + "    (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?) as quant_total,\r\n"
					   + "    ((count(id_vendedor) / (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?)) * 100) as percentual_quant\r\n"
					   + "from\r\n"
					   + "    cabecalhovendas\r\n"
					   + "    inner join vendedor on vendedor.codigo = cabecalhovendas.id_vendedor\r\n"
					   + "where\r\n"
					   + "    cabecalhovendas.situacao = 'FINALIZADA' and\r\n"
					   + "    cabecalhovendas.datavenda between ? and ?\r\n"
					   + "group by\r\n"
					   + "    cabecalhovendas.id_vendedor\r\n"
					   + "order by\r\n"
					   + "    valor desc";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, startDate);
			ps.setString(6, endDate);
			ps.setString(7, startDate);
			ps.setString(8, endDate);
			ps.setString(9, startDate);
			ps.setString(10, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaVendasPorVendedor = new ArrayList<Vendas>();
			
			while (rs.next()) {
				Vendedor vendedor = new Vendedor(rs.getInt("vendedor_cod"), rs.getString("vendedor_nome"));
				
				Vendas v = new Vendas();
				v.setVendedor(vendedor);
				v.setValor(rs.getBigDecimal("valor"));
				v.setValorTotal(rs.getBigDecimal("valor_total"));
				v.setPercentualValor(rs.getBigDecimal("percentual_valor"));
				v.setQuant(rs.getInt("quant"));
				v.setQuantTotal(rs.getInt("quant_total"));
				v.setPercentualQuant(rs.getBigDecimal("percentual_quant"));
				
				listaVendasPorVendedor.add(v);
			}
			
		} catch (Exception e) {
			
			listaVendasPorVendedor = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaVendasPorVendedor;
		
	}
	
	public List<Vendas> listVendasByCliente(String startDate, String endDate) throws PersistenceException {
		
		List<Vendas> listaVendasPorCliente;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    cabecalhovendas.id_cliente as cliente_id,\r\n"
					   + "    cliente.nome as cliente_nome,\r\n"
					   + "    count(cabecalhovendas.id_cliente) as quant,\r\n"
					   + "    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor\r\n"
					   + "from\r\n"
					   + "    cabecalhovendas\r\n"
					   + "    inner join cliente on cliente.codigo = cabecalhovendas.id_cliente\r\n"
					   + "where \r\n"
					   + "    cabecalhovendas.situacao = 'FINALIZADA' and\r\n"
					   + "    cabecalhovendas.datavenda between ? and ?\r\n"
					   + "group by\r\n"
					   + "    cabecalhovendas.id_cliente\r\n"
					   + "order by\r\n"
					   + "    valor DESC\r\n"
					   + "LIMIT 5";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaVendasPorCliente = new ArrayList<Vendas>();
			
			int quantTotal = 0;
			
			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("cliente_nome"));
				
				Vendas v = new Vendas();
				v.setCliente(cliente);
				v.setValor(rs.getBigDecimal("valor"));
				v.setQuant(rs.getInt("quant"));
				
				listaVendasPorCliente.add(v);
				
				quantTotal += rs.getInt("quant");
			}
			
			for (Vendas v : listaVendasPorCliente) {
				v.setQuantTotal(quantTotal);
				
				double percentQnt = (double) (v.getQuant() * 100) / quantTotal;
				
				v.setPercentualQuant(new BigDecimal(String.valueOf(percentQnt)));
			}
			
		} catch (Exception e) {
			
			listaVendasPorCliente = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaVendasPorCliente;
		
	}
	
	public List<Vendas> listVendasByFormaPagamento(String startDate, String endDate) throws PersistenceException {
		
		List<Vendas> listaVendasPorFormaPagamento;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    tipo.nome as forma_pagamento,\r\n"
					   + "    ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) as valor_total_pagamento,\r\n"
					   + "    (select ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) from modalidadedevenda inner join cabecalhovendas on cabecalhovendas.id_venda = modalidadedevenda.codigovenda where modalidadedevenda.data between ? and ? and cabecalhovendas.situacao = 'FINALIZADA') as valor_de_todos_pgto,\r\n"
					   + "    ( ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) ) * ( 100 ) / (select ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) )  from modalidadedevenda inner join cabecalhovendas on cabecalhovendas.id_venda = modalidadedevenda.codigovenda where modalidadedevenda.data between ? and ? and cabecalhovendas.situacao = 'FINALIZADA') as porcentagem\r\n"
					   + "from\r\n"
					   + "    modalidadedevenda\r\n"
					   + "    inner join tipo               on tipo.codigo = modalidadedevenda.tipovenda\r\n"
					   + "    inner join cabecalhovendas    on cabecalhovendas.id_venda = modalidadedevenda.codigovenda\r\n"
					   + "where\r\n"
					   + "    modalidadedevenda.data between ? and ? and\r\n"
					   + "    cabecalhovendas.situacao = 'FINALIZADA'\r\n"
					   + "group by\r\n"
					   + "    tipo.nome\r\n"
					   + "order by\r\n"
					   + "    valor_total_pagamento desc";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, startDate);
			ps.setString(6, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaVendasPorFormaPagamento = new ArrayList<Vendas>();
			
			while (rs.next()) {
				Vendas v = new Vendas();
				v.setFormaPagamento(rs.getString("forma_pagamento"));
				v.setValor(rs.getBigDecimal("valor_total_pagamento"));
				v.setValorTotal(rs.getBigDecimal("valor_de_todos_pgto"));
				v.setPercentualValor(rs.getBigDecimal("porcentagem"));
				
				listaVendasPorFormaPagamento.add(v);
			}
			
		} catch (Exception e) {
			
			listaVendasPorFormaPagamento = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaVendasPorFormaPagamento;
		
	}
	
	public List<Vendas> listVendasByHoraPico(String startDate, String endDate) throws PersistenceException {
		
		List<Vendas> listaVendasPorHoraPico;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			//Sem desconsiderar as vendas canceladas
			String sql = "select\r\n"
					   + "    HOUR(hora) as horario,\r\n"
					   + "    count(*) as quant_vendas,\r\n"
					   + "    ( select count(*) from cabecalhovendas where datavenda between ? and ?) as quant_total_vendas,\r\n"
					   + "    ( count(*) ) * ( 100 ) / ( select count(*) from cabecalhovendas where datavenda between ? and ? ) as percentual\r\n"
					   + "from\r\n"
					   + "    cabecalhovendas\r\n"
					   + "where\r\n"
					   + "    cabecalhovendas.datavenda between ? and ?\r\n"
					   + "group by\r\n"
					   + "    horario";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, startDate);
			ps.setString(6, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaVendasPorHoraPico = new ArrayList<Vendas>();
			
			while (rs.next()) {
				Vendas v = new Vendas();
				v.setHora(rs.getInt("horario"));
				v.setQuant(rs.getInt("quant_vendas"));
				v.setQuantTotal(rs.getInt("quant_total_vendas"));
				v.setPercentualQuant(rs.getBigDecimal("percentual"));
				
				listaVendasPorHoraPico.add(v);
			}
			
		} catch (Exception e) {
			
			listaVendasPorHoraPico = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaVendasPorHoraPico;
		
	}
	
	public Integer findQuantVendas(String startDate, String endDate) throws PersistenceException {
		
		Integer quantVendas = 0;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select count(*) as quant_vendas from cabecalhovendas where datavenda between ? and ? and situacao = 'FINALIZADA'";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				quantVendas = rs.getInt("quant_vendas");
			}
			
			if (quantVendas == 0) quantVendas = null;
			
		} catch (Exception e) {
			
			quantVendas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantVendas;
		
	}
	
	public BigDecimal findValorVendas(String startDate, String endDate) throws PersistenceException {
		
		BigDecimal quantVendas = null;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select sum(valortotal + acrescimo - desconto) as valor_vendas from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO'";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				quantVendas = rs.getBigDecimal("valor_vendas");
			}
			
		} catch (Exception e) {
			
			quantVendas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantVendas;
		
	}
	
	public Vendas findVendasVendedorDestaque(String startDate, String endDate) throws PersistenceException {
		
		Vendas vendas = null;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    vendedor.codigo as vendedor_cod,\r\n"
					   + "    vendedor.nome as vendedor_nome,\r\n"
					   + "    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,\r\n"
					   + "    (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO') as valor_total,\r\n"
					   + "    ( (sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) * 100) / (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda between ? and ? and situacao = 'FINALIZADO')) as percentual_valor,\r\n"
					   + "    count(id_vendedor) as quant,\r\n"
					   + "    (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?) as quant_total,\r\n"
					   + "    ((count(id_vendedor) / (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda between ? and ?)) * 100) as percentual_quant\r\n"
					   + "from\r\n"
					   + "    cabecalhovendas\r\n"
					   + "    inner join vendedor on vendedor.codigo = cabecalhovendas.id_vendedor\r\n"
					   + "where\r\n"
					   + "    cabecalhovendas.situacao = 'FINALIZADA' and\r\n"
					   + "    cabecalhovendas.datavenda between ? and ? and\r\n"
					   + "    vendedor.codigo > 1\r\n"
					   + "group by\r\n"
					   + "    cabecalhovendas.id_vendedor\r\n"
					   + "order by\r\n"
					   + "    valor desc\r\n"
					   + "limit 1";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.setString(5, startDate);
			ps.setString(6, endDate);
			ps.setString(7, startDate);
			ps.setString(8, endDate);
			ps.setString(9, startDate);
			ps.setString(10, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Vendedor vendedor = new Vendedor(rs.getInt("vendedor_cod"), rs.getString("vendedor_nome"));
				
				vendas = new Vendas();
				vendas.setVendedor(vendedor);
				vendas.setValor(rs.getBigDecimal("valor"));
			}
			
		} catch (Exception e) {
			
			vendas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return vendas;
		
	}
	
}
