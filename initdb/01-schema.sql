drop schema if exists `pizzeria`;
create schema if not exists `pizzeria`;
use `pizzeria`;

create table if not exists Pizza (
  name varchar(20) not null,
  price numeric not null,
  constraint pk_Pizza primary key (name)
);

create table if not exists StockPizza (
  id integer not null auto_increment,
  pizza varchar(20),
  quantity integer NULL,
  constraint pk_StockPizza primary key (id)
);

alter table StockPizza add constraint fk_StockPizza_Pizza_1 foreign key (pizza) references Pizza (name) on delete restrict on update restrict;
create index ix_StockPizza_Pizza_1 on StockPizza (pizza);
  
create table if not exists Ingredient (
  name varchar(20) not null,
  price numeric not null,
  constraint pk_Ingredient primary key (name)
);

create table if not exists IngredientStock (
  id integer not null auto_increment,
  ingredient varchar(20),
  quantity integer NULL,
  constraint pk_IngredientStock primary key (id)
);

alter table IngredientStock add constraint fk_IngredientStock_Ingredient_1 foreign key (ingredient) references Ingredient (name) on delete restrict on update restrict;
create index ix_IngredientStock_Ingredient_1 on IngredientStock (ingredient);
  
create table if not exists ArticleAutre (
  name varchar(20) not null,
  price numeric not null,
  constraint pk_ArticleAutre primary key (name)
);

create table if not exists AutreStock (
  id integer not null auto_increment,
  article varchar(20),
  quantity integer NULL,
  constraint pk_AutreStock primary key (id)
);

alter table AutreStock add constraint fk_AutreStock_ArticleAutre_1 foreign key (article) references ArticleAutre (name) on delete restrict on update restrict;
create index ix_AutreStock_ArticleAutre_1 on AutreStock (article);
  
create table if not exists MtM_Pizza_Ingredient (
  id integer not null auto_increment,
  pizza varchar(20),
  ingredient varchar(20),
  constraint pk_MtM_Pizza_Ingredient primary key (id)
);

alter table MtM_Pizza_Ingredient add constraint fk_MtM_Pizza_Ingredient_Pizza_1 foreign key (pizza) references Pizza (name) on delete restrict on update restrict;
create index ix_MtM_Pizza_Ingredient_Pizza_1 on MtM_Pizza_Ingredient (pizza);
alter table MtM_Pizza_Ingredient add constraint fk_MtM_Pizza_Ingredient_Ingredient_1 foreign key (ingredient) references Ingredient (name) on delete restrict on update restrict;
create index ix_MtM_Pizza_Ingredient_Ingredient_1 on MtM_Pizza_Ingredient (ingredient);
