/* 	Script to create 'poi' table and
	populate it with default PoIspoi
*/

CREATE  TABLE `poi` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `x` INT NOT NULL ,
  `y` INT NOT NULL ,
  PRIMARY KEY (`id`) );

INSERT INTO poi VALUES 
	(1,'Lanchonete',27,12),
	(2,'Posto',31,18),
	(3,'Joalheria',15,12),
	(4,'Floricultura',19,21),
	(5,'Pub',12,8),
	(6,'Supermercado',23,6),
	(7,'Churrascaria',28,2)