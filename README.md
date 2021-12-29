<h1>DASHBOARD</h1>

Dashboard com design responsivo criado para gerenciar informações sobre vendas. Ele foi feito para uma empresa de software do ramo de gestão empresarial.
Tecnologias utilizadas: Java; JSP; JDBC; MySQL; HTML; CSS; Javascript; ChartJS.

Vale ressaltar que os dados (logotipo, endereço de e-mail, número de telefone e outros) da empresa (cliente) em questão foram removidos por uma questão de
segurança e privacidade.

Servidor web utilizado: Tomcat 9.

<h3>Gráficos:</h3>

<ul>
	<li>Vendas (considerando valor e quantidade) por operador;</li>
	<li>Vendas (considerando valor e quantidade) por vendedor;</li>
	<li>Vendas (considerando apenas valor) por forma de pagamento;</li>
	<li>Produtos (considerando valor e quantidade) mais vendidos;</li>
	<li>Clientes com mais compras (considerando valor e quantidade) no que se refere a valores (R$);</li>
	<li>Vendas (considerando apenas quantidade) por horário.</li>
</ul>

<h3>Ordens de Serviço:</h3>

	É possível obter informações sobre as ordens de serviço de forma agrupada na sua respectiva página. Esse agrupamento é feito pelo tipo da ordem de serviço.
	Esses tipos são definidos de forma dinâmica em um outro sistema pertencente ao ecossistema da empresa (cliente). Contudo, existem alguns tipos pré-definidos
	de forma padrão: "aberta", "baixada", "cancelada", "finalizada" e "orçamento". Então, a partir do período especificado, podemos obter valores relacionados
	a todas ordens de serviço do tipo "aberta" por exemplo.

<h3>Configurações:</h3>

	Na tela de configurações podemos especificar o filtro padrão a ser utilizado na criação dos gráficos e na obtenção das informações relacionadas às
	ordens de serviço.

<h3>Filtros:</h3>

	Os filtros implementados são baseados em datas: "DUIA ATUAL", "MÊS ATUAL", e assim por diante. Além desses filtros com intervalos de datas fixos,
	foi implementado um tipo de filtro onde o próprio usuário pode especificar o intervalo desejado.

	É importante salientar que a página de ordens de serviços possui um filtro a mais que determina se é para buscar as ordens de serviços que foram
	emitidas ou então as que foram fechadas dentro do período informado.

<h3>Uma página de suporte, contendo informações de contato, também foi implementada.</h3>

<h3>Foram definidas configurações de duração de sessão, para impedir que usuários fiquem "logados" de forma inativa por muito tempo.</h3>

<h3>Observações:</h3>

<ul>	
	<li>Apesar de ter recebido uma base de dados já criada pela empresa em questão, as queries utilizadas nas classes DAO também foram feitas por mim;</li>
	<li>Esse sistema foi desenvolvido para rodar em redes locais com o auxílio do Tomcat;</li>
	<li>A empresa (cliente) disse que não seria necessário implementar criptografia de senhas, assim como outros recursos relacionados à segurança.</li>
</ul>
<br/>

Autor: Wanclay Barreto <br/>
Github: https://github.com/wanclaybarreto