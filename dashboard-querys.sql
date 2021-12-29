use asn;

-- Vendas por operador (OLD)
select
	sum(cabecalhovendas.totalvenda) as totalvenda,
	cabecalhovendas.operador,
	count(id_vendedor) as quantidade_de_vendas,
	( select count(id_vendedor) from cabecalhovendas inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo ) as somatotal,
	( count(id_vendedor) ) * ( 100 ) / ( select count(id_vendedor) from cabecalhovendas inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo ) as percentual
from
	cabecalhovendas
    inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo
where
	cabecalhovendas.datavenda = '2021-09-22'
group by
	cabecalhovendas.operador
order by quantidade_de_vendas desc;

-- Vendas por operador
select
	cabecalhovendas.operador,
    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,
    (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO') as valor_total,
    ( (sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) * 100) / (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO')) as percentual_valor,
    count(operador) as quant,
    (select count(operador) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda = '2021-09-22') as quant_total,
    ((count(operador) / (select count(operador) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda = '2021-09-22')) * 100) as percentual_quant
from
	cabecalhovendas
where
	cabecalhovendas.situacao = 'FINALIZADA' and
	cabecalhovendas.datavenda = '2021-09-22'
group by 
	cabecalhovendas.operador
order by
	valor desc;



-- Vendas por vendedor (OLD)
select
	sum(cabecalhovendas.totalvenda) as totalvenda,
	vendedor.codigo as codigo,
	vendedor.nome as nome,
	count(id_vendedor) as quantidade_de_vendas,
	( select count(id_vendedor) from cabecalhovendas inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo ) as somatotal,
	( count(id_vendedor) ) * ( 100 ) / ( select count(id_vendedor) from cabecalhovendas inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo ) as percentual
from
	cabecalhovendas
    inner join vendedor on cabecalhovendas.id_vendedor=vendedor.codigo
where
	cabecalhovendas.datavenda = '2021-09-22'
group by
	id_vendedor
order by
	quantidade_de_vendas desc;

-- Vendas por vendedor    
select
	vendedor.codigo as vendedor_cod,
    vendedor.nome as vendedor_nome,
    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,
    (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO') as valor_total,
    ( (sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) * 100) / (select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO')) as percentual_valor,
    count(id_vendedor) as quant,
    (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda = '2021-09-22') as quant_total,
    ((count(id_vendedor) / (select count(id_vendedor) from cabecalhovendas where situacao = 'FINALIZADA' and cabecalhovendas.datavenda = '2021-09-22')) * 100) as percentual_quant
from
	cabecalhovendas
    inner join vendedor on vendedor.codigo = cabecalhovendas.id_vendedor
where
	cabecalhovendas.situacao = 'FINALIZADA' and
	cabecalhovendas.datavenda = '2021-09-22'
group by 
	cabecalhovendas.id_vendedor
order by
	quant desc;



-- Produtos mais vendidos
select
	itensvenda.id_item as id_item,
	itensvenda.nome as nome,
	sum(itensvenda.quantidade) as quantidadevenda ,
	sum(itensvenda.acrescimo) as acrescimo,
	sum(valortotal) as valortotal,
	sum(itensvenda.desconto) as  desconto,
	( sum(valortotal) ) + ( sum(itensvenda.acrescimo) ) - ( sum(itensvenda.desconto) ) as subtotal
from
	itensvenda
	inner join produto on produto.codigo = itensvenda.id_item
where
	itensvenda.datavenda = '2021-09-22'
group by
	id_item
order by
	quantidadevenda desc
limit 5;



-- Cliente que mais comprou (modified)
select
	cabecalhovendas.id_cliente as cliente_id,
    cliente.nome as cliente_nome,
    sum((select sum(valortotal + acrescimo - desconto) from itensvenda where id_venda = cabecalhovendas.id_venda and situacao = 'FINALIZADO')) as valor,
    count(cabecalhovendas.id_cliente) as quant
from
	cabecalhovendas
	inner join cliente on cliente.codigo = cabecalhovendas.id_cliente
where 
	cabecalhovendas.situacao = 'FINALIZADA' and
    cabecalhovendas.datavenda between '2020-01-01' and '2021-12-30'
group by
	cabecalhovendas.id_cliente
order by
	valor DESC
LIMIT 5;



-- Vendas por forma de pagamento (adjusted - date)
select
	tipo.nome as forma_pagamento,
	( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) as valor_total_pagamento,
	(select ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) from modalidadedevenda inner join cabecalhovendas on cabecalhovendas.id_venda = modalidadedevenda.codigovenda where modalidadedevenda.data = '2020-09-22' and cabecalhovendas.situacao = 'FINALIZADA') as valor_de_todos_pgto,
	( ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) ) ) * ( 100 ) / (select ( sum(modalidadedevenda.valorpago) ) - ( sum(modalidadedevenda.troco) )  from modalidadedevenda inner join cabecalhovendas on cabecalhovendas.id_venda = modalidadedevenda.codigovenda where modalidadedevenda.data = '2020-09-22' and cabecalhovendas.situacao = 'FINALIZADA') as porcentagem
from
	modalidadedevenda
	inner join tipo               on tipo.codigo = modalidadedevenda.tipovenda
    inner join cabecalhovendas    on cabecalhovendas.id_venda = modalidadedevenda.codigovenda
where
	modalidadedevenda.data = '2020-09-22' and
    cabecalhovendas.situacao = 'FINALIZADA'
group by
	tipo.nome
order by
	valor_total_pagamento desc;



-- Horários de pico (adjusted - date) (INCLUI AS VENDAS CANCELADAS)
select
	HOUR(hora) as horario,
	count(*) as quant_vendas,
	( select count(*) from cabecalhovendas where datavenda = '2021-09-22') as quant_total_vendas,
	( count(*) ) * ( 100 ) / ( select count(*) from cabecalhovendas where datavenda = '2021-09-22' ) as percentual
from
	cabecalhovendas
where
	cabecalhovendas.datavenda = '2021-09-22'
group by
	horario;



-- Quantidade de vendas
select count(*) as quant_vendas from cabecalhovendas where datavenda = '2021-09-22' and situacao = 'FINALIZADA';

-- Valor das vendas
select sum(valortotal + acrescimo - desconto) as valor_vendas from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO';

-- Quantidade de contas recebidas
select count(*) as quant_contas_recebidas from contasreceber where datapagamento = '2021-09-22';

-- Valor de contas recebidas
select sum(valorpago - troco) as valor_contas_recebidas from contasreceber where datapagamento = '2021-09-22';

-- Quantidade de contas pagas
select count(*) as quant_contas_pagas from contaspagar where datapagamento = '2021-09-22';

-- Valor de contas pagas
select sum(valorpago - troco) as valor_contas_pagas from contaspagar where datapagamento = '2021-09-22';
    


-- Ordens de serviço
select
	status, sum(valortotal_av) as valor_av, sum(valortotal_ap) as valor_ap, sum(valorfinal) as valor_final, count(status) as quant
from
	os2
where
	datafechamento between '2021-10-06' and '2021-10-06'
group
	by status;


CREATE USER 'asnweb'@'%' IDENTIFIED BY 'webasnsoft@';
GRANT ALL ON *.* TO 'asnweb'@'%';
SELECT user, host FROM mysql.user;
SHOW GRANTS FOR 'asnweb'@'%';


select * from itensvenda;
select sum(totalvenda) from cabecalhovendas where situacao = 'FINALIZADA' and datavenda = '2021-09-22';
select * from vendedor;
select sum(valortotal + acrescimo - desconto) from itensvenda where datavenda = '2021-09-22' and situacao = 'FINALIZADO';
select * from modalidadedevenda group by data;
select * from cabecalhovendas;
select * from configuracoes;
select * from usuario;

select * from contasreceber where valorpago <> 0 and valorpago <> valor;
select * from contaspagar;

update contasreceber set troco = 0.00 where datapagamento is not null;
update contaspagar set troco = 0.00 where datapagamento is not null;

select * from os2 where datafechamento between '2020-09-22' and '2020-12-22';