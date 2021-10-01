CREATE USER 'pizzaiolo'@'%' IDENTIFIED WITH mysql_native_password BY 'PizzaioloDePizzaHut';

GRANT ALL PRIVILEGES ON `pizzeria`.* TO 'pizzaiolo'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;