-- Dados de teste para produtos
INSERT INTO produtos (nome, categoria, preco, quantidade_estoque, descricao, ativo) VALUES
('Big Mac', 'LANCHE', 25.90, 50, 'Dois hambúrgueres, alface, queijo, molho especial, cebola, picles em um pão com gergelim', true),
('Quarter Pounder', 'LANCHE', 28.50, 30, 'Hambúrguer de carne bovina, queijo, cebola, picles, ketchup e mostarda', true),
('McChicken', 'LANCHE', 22.90, 40, 'Frango empanado, alface e maionese em pão com gergelim', true),
('Cheeseburger', 'LANCHE', 12.90, 60, 'Hambúrguer, queijo, cebola, picles, ketchup e mostarda', true),
('Batata Frita Pequena', 'ACOMPANHAMENTO', 8.90, 100, 'Batatas fritas crocantes - tamanho pequeno', true),
('Batata Frita Média', 'ACOMPANHAMENTO', 10.90, 80, 'Batatas fritas crocantes - tamanho médio', true),
('Batata Frita Grande', 'ACOMPANHAMENTO', 12.90, 60, 'Batatas fritas crocantes - tamanho grande', true),
('McNuggets 10 unidades', 'ACOMPANHAMENTO', 18.90, 40, 'Nuggets de frango crocantes - 10 unidades', true),
('Coca-Cola 300ml', 'BEBIDA', 6.90, 120, 'Refrigerante Coca-Cola - 300ml', true),
('Coca-Cola 500ml', 'BEBIDA', 8.90, 100, 'Refrigerante Coca-Cola - 500ml', true),
('Suco de Laranja', 'BEBIDA', 7.90, 50, 'Suco natural de laranja - 300ml', true),
('Água Mineral', 'BEBIDA', 3.90, 80, 'Água mineral sem gás - 500ml', true),
('Sundae Chocolate', 'SOBREMESA', 9.90, 30, 'Sorvete de baunilha com calda de chocolate', true),
('Torta de Maçã', 'SOBREMESA', 8.90, 25, 'Torta de maçã quentinha com canela', true);

-- Dados de teste para clientes
INSERT INTO clientes (nome, cpf, email, telefone) VALUES
('João Silva', '12345678901', 'joao.silva@email.com', '(11) 99999-1111'),
('Maria Santos', '98765432100', 'maria.santos@email.com', '(11) 99999-2222'),
('Pedro Oliveira', '11122233344', 'pedro.oliveira@email.com', '(11) 99999-3333');

-- Dados de teste para pedidos
INSERT INTO pedidos (cliente_id, status, valor_total, data_criacao, observacoes) VALUES
(1, 'RECEBIDO', 34.80, '2024-01-15 10:30:00', 'Sem cebola no hambúrguer'),
(2, 'EM_PREPARACAO', 52.70, '2024-01-15 11:15:00', 'Batata bem passada'),
(3, 'PRONTO', 28.80, '2024-01-15 12:00:00', null);

-- Dados de teste para itens de pedido
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario, subtotal, observacoes) VALUES
-- Pedido 1
(1, 1, 1, 25.90, 25.90, 'Sem cebola'),
(1, 9, 1, 8.90, 8.90, null),
-- Pedido 2  
(2, 2, 1, 28.50, 28.50, null),
(2, 6, 1, 10.90, 10.90, 'Bem passada'),
(2, 10, 1, 8.90, 8.90, null),
(2, 13, 1, 9.90, 9.90, null),
-- Pedido 3
(3, 3, 1, 22.90, 22.90, null),
(3, 5, 1, 8.90, 8.90, null);

-- Dados de teste para pagamentos
INSERT INTO pagamentos (pedido_id, valor, status, metodo_pagamento, transacao_id, data_criacao, qr_code_data) VALUES
(1, 34.80, 'PENDENTE', 'PIX', 'TXN-ABC12345', '2024-01-15 10:30:30', '00020126580014BR.GOV.BCB.PIX0136123e4567-e89b-12d3-a456-426614174000520400005303986540534.805802BR6009SAO PAULO62070503***6304'),
(2, 52.70, 'APROVADO', 'PIX', 'TXN-DEF67890', '2024-01-15 11:15:30', '00020126580014BR.GOV.BCB.PIX0136987f6543-e21c-43d2-b567-537725285111520400005303986540552.705802BR6009SAO PAULO62070503***6304'),
(3, 28.80, 'APROVADO', 'CARTAO', 'TXN-GHI11111', '2024-01-15 12:00:30', null);

