
create database agricultura;

use agricultura;

CREATE TABLE `Produtos` (
  `id` int(11) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `tipo` varchar(200) NOT NULL,
  `rating` int(11) NOT NULL,
  `descricao` text(200) NOT NULL,
  `quantidade` varchar(200) NOT NULL,
  `preco` varchar(20) NOT NULL,
  `fornecedor` varchar(200) NOT NULL
  

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO `Produtos` (`id`,`nome`, `tipo`, `rating`, `descricao`, `quantidade`, `preco`, `fornecedor`) 
VALUES (
	1,
	'Banana prata',
	'Fruta', 
	5, 
	'A Banana é a fruta mais consumida em todo o mundo! No Brasil, o IBGE estima que cada brasileiro consome em média 7 kilos de banana por ano e nosso país é um dos líderes em produção mundial dessa amarelinha.',
	'600g - 800g',
	'8,99',
	'João'
	), 

	(2,
	'Açucar',
	'Mercearia', 
	4, 
	'Experimente o Açúcar Orgânico Demerara Native na versão Dourado, que adoça conferindo um sabor todo especial a caldas e bebidas quentes. ',
	'1Kg',
	'4,99',
	'Native'
	),

	(3,
	'Abacaxi Orgânico',
	'Fruta', 
	5, 
	'O Abacaxi é simbolo da tropicalidade brasileira! Não é por menos, essa fruta de aparência única tem origem aquí!',
	'unidade',
	'14,99',
	'Native'
	);
	

ALTER TABLE `Produtos`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `Produtos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

