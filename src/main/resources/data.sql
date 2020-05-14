INSERT INTO Produto (gtin, nome) VALUES ('7894900011517', 'REFRIGERANTE COCA-COLA 2LT');
INSERT INTO Produto (gtin, nome) VALUES ('7891910000197', 'AÇÚCAR REFINADO UNIÃO 1KG');
INSERT INTO Produto (gtin, nome) VALUES ('7892840222949', 'SALGADINHO FANDANGOS QUEIJO');
INSERT INTO Produto (gtin, nome) VALUES ('7891910007110', 'AÇÚCAR DE CONFEITEIRO UNIÃO GLAÇÚCAR');
INSERT INTO Produto (gtin, nome) VALUES ('7891000053508', 'ACHOCOLATADO NESCAU 2.0');
INSERT INTO Produto (gtin, nome) VALUES ('7891000100103', 'LEITE CONDENSADO MOÇA');
INSERT INTO Produto (gtin, nome) VALUES ('7891991010856', 'CERVEJA BUDWEISER');


INSERT INTO fornecedor (id, nome, cnpj) VALUES(1, 'Fornecedor 1','56.918.868/0001-20');
INSERT INTO fornecedor (id, nome, cnpj) VALUES(2, 'Fornecedor 2','37.563.823/0001-35');

insert into pedido(id,id_fornecedor)VALUES(100,1);	

insert into item_pedido(id,id_pedido,id_produto, quantidade,preco,total )VALUES(10,100,'7894900011517',1, 5.98, 5.98);
insert into item_pedido(id,id_pedido,id_produto, quantidade,preco,total )VALUES(20,100,'7892840222949',1, 3.98, 3.98);



