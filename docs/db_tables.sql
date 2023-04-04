;;
;; in table pproperty change column name accessbie
;; the signle quot is the one under ~ key
;;
ALTER TABLE pproperty CHANGE `accessible` access_friendly char(1);
;;
;;
  create table nhoods(    
   id mediumint NOT NULL AUTO_INCREMENT,                                 
   name varchar(80) DEFAULT NULL,     
   PRIMARY KEY (id))engine=InnoDB;
;;
;; adding the neighborhoods
;;
 insert into nhoods values(1, 'N/A'),
 (2,'Arden Place'),
 (43,'Arlington Valley'),
       (3,'Autumn View'),
       (42,'Barclay Gardens'),
       (4,'Bitner Woods'),
       (5,'Blue Ridge'),
       (6,'Broadview'),
       (7,'Bryan Park'),
       (8,'Crestmont'),
       (9,'Eastside'),
       (10,'Elm Heights'),
       (11,'Fritz Terrace'),
       (12,'Garden Hill'),
       (13,'Gentry Estates'),
       (15,'Grandview Estates'),
       (14,'Green Acres'),
       (16,'Highland Village'),
       (17,'Hoosier Acres'),
       (18,'Hyde Park'),
       (19,'Kensington Park'),
       (40,'Maple Heights'),
       (20,'Matlock Heights'),
       (21,'McDoel Gardens'),
       (22,'Miller Drive'),
       (23,'Moss Creek Village'),
       (24,'Near West Side'),
       (25,'Northwood Estates'),
       (26,'Old Northeast Downtown'),
       (27,'Park Ridge'),
       (28,'Park Ridge East'),
       (29,'Prospect Hill'),
       (30,'Rockport Hills'),
       (31,'Sherwood Oaks'),
       (32,'Sixth and Ritter'),
       (33,'South Griffy'),
       (34,'Southern Pines'),
       (35,'Stands'),
       (41,'Sunny Slopes'),
       (36,'Sycamore Knolls'),
       (37,'Tamarron'),
       (38,'Waterman'),
       (39,'West Point');
;;
 CREATE TABLE `media` (
  `id` int unsigned NOT NULL,
  `file_name` varchar(80) DEFAULT NULL,
  `old_file_name` varchar(256) DEFAULT NULL,
  `obj_id` int DEFAULT NULL,
  `obj_type` enum('Loan','Property','Client') DEFAULT NULL,
  `date` date DEFAULT NULL,
  `notes` varchar(512) DEFAULT NULL,
  `user_id` mediumint DEFAULT NULL,
  UNIQUE KEY `id` (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `media_ibfk_1` FOREIGN KEY (`id`) REFERENCES `media_seq` (`id`),
  CONSTRAINT `media_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
;;
CREATE TABLE `media_seq` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb3 ;
;;
 CREATE TABLE `p_leakage_tests` (
  `id` mediumint NOT NULL AUTO_INCREMENT,
  `pid` mediumint DEFAULT NULL,
  `blowRead` varchar(5) NOT NULL,
  `flowRing` enum('A','B','C') DEFAULT NULL,
  `testDate` date DEFAULT NULL,
  `testEvent` enum('Before','After') DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ;
;;
;;
 CREATE TABLE `pclient` (
  `cid` mediumint NOT NULL AUTO_INCREMENT,
  `entry_date` date DEFAULT NULL,
  `f_name` varchar(30) DEFAULT NULL,
  `l_name2` varchar(30) DEFAULT NULL,
  `f_name2` varchar(30) DEFAULT NULL,
  `company` varchar(40) DEFAULT NULL,
  `w_phone` varchar(14) DEFAULT NULL,
  `h_phone` varchar(14) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  `STREET_NUM` varchar(8) DEFAULT NULL,
  `STREET_DIR` char(1) DEFAULT NULL,
  `STREET_NAME` varchar(30) DEFAULT NULL,
  `STREET_TYPE` varchar(4) DEFAULT NULL,
  `POST_DIR` varchar(1) DEFAULT NULL,
  `SUD_TYPE` varchar(8) DEFAULT NULL,
  `SUD_NUM` varchar(8) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip` varchar(12) DEFAULT NULL,
  `hh_size` varchar(2) DEFAULT NULL,
  `ethnicity` int DEFAULT NULL,
  `race` int DEFAULT NULL,
  `female_hhh` char(1) DEFAULT NULL,
  `AMI` int DEFAULT NULL,
  `hh_type` varchar(25) DEFAULT NULL,
  `pobox` varchar(20) DEFAULT NULL,
  `ben_type` varchar(20) DEFAULT NULL,
  `l_name` varchar(32) DEFAULT NULL,
  `house_ratio` double DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM AUTO_INCREMENT=887 DEFAULT CHARSET=utf8mb3;
;;
CREATE TABLE `pmortgage` (
  `lid` mediumint DEFAULT NULL,
  `id` mediumint DEFAULT NULL,
  `due_date` date NOT NULL,
  `paid_date` date DEFAULT NULL,
  `amount_paid` double DEFAULT '0',
  `interest` double DEFAULT '0',
  `principal` double DEFAULT '0',
  `principal_bal` double DEFAULT '0',
  `late_fee` double DEFAULT '0',
  `receipt` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 ;
;;
;;
CREATE TABLE `pproperty` (
  `pid` mediumint NOT NULL AUTO_INCREMENT,
  `STREET_NUM` varchar(10) DEFAULT NULL,
  `STREET_DIR` char(1) DEFAULT NULL,
  `STREET_NAME` varchar(30) DEFAULT NULL,
  `STREET_TYPE` varchar(4) DEFAULT NULL,
  `POST_DIR` char(1) DEFAULT NULL,
  `SUD_TYPE` varchar(8) DEFAULT NULL,
  `SUD_NUM` varchar(8) DEFAULT NULL,
  `ptype` int DEFAULT NULL,
  `bedrooms` int DEFAULT NULL,
  `tenure_type` int DEFAULT NULL,
  `nhood` int DEFAULT NULL,
  `district` int DEFAULT NULL,
  `block_group` varchar(10) DEFAULT NULL,
  `est_const_year` int DEFAULT NULL,
  `hist_elegible` char(1) DEFAULT NULL,
  `national_reg` char(1) DEFAULT NULL,
  `access_friendly` char(1) DEFAULT NULL,
  `rev_106` char(1) DEFAULT NULL,
  `idis_no` int DEFAULT NULL,
  `leverage` varchar(10) DEFAULT NULL,
  `insur_expire` date DEFAULT NULL,
  `notes` varchar(250) DEFAULT NULL,
  `contract` varchar(40) DEFAULT NULL,
  `cont_addr` varchar(50) DEFAULT NULL,
  `census_tract` varchar(10) DEFAULT NULL,
  `owner_type` varchar(15) DEFAULT NULL,
  `act_type` varchar(18) DEFAULT NULL,
  `chdo` char(1) DEFAULT NULL,
  `plan_ref` varchar(20) DEFAULT NULL,
  `eligible_act` varchar(30) DEFAULT NULL,
  `reference` varchar(30) DEFAULT NULL,
  `nation_obj` varchar(35) DEFAULT NULL,
  `envir_ref` varchar(5) DEFAULT NULL,
  `proj_manager` varchar(30) DEFAULT NULL,
  `units` int DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=MyISAM AUTO_INCREMENT=940 DEFAULT CHARSET=utf8mb3;
;;
;;
CREATE TABLE `punits` (
  `oid` mediumint NOT NULL AUTO_INCREMENT,
  `pid` mediumint DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  `bedrooms` int DEFAULT '0',
  `occupancy` varchar(6) DEFAULT NULL,
  `subsidy` double DEFAULT '0',
  `rent_total` double DEFAULT '0',
  `ami` int DEFAULT '0',
  `race` int DEFAULT '0',
  `hh_size` int DEFAULT '0',
  `hh_type` int DEFAULT '0',
  `rent_assist` varchar(15) DEFAULT NULL,
  `notes` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
;;
 CREATE TABLE `users` (
  `id` mediumint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `fullName` varchar(50) NOT NULL,
  `role` enum('Edit','Edit:Delete','Edit:Delete:Admin') DEFAULT NULL,
  `project_manager` char(1) DEFAULT NULL,
  `active` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 ;
;;





