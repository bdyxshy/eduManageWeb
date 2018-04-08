/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : school

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2018-01-12 10:05:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `banji`
-- ----------------------------
DROP TABLE IF EXISTS `banji`;
CREATE TABLE `banji` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nums` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banji
-- ----------------------------
INSERT INTO `banji` VALUES ('1', 'java一班', '4');
INSERT INTO `banji` VALUES ('2', 'java二班', '7');
INSERT INTO `banji` VALUES ('4', '.net班', '3');
INSERT INTO `banji` VALUES ('6', 'php班', '2');

-- ----------------------------
-- Table structure for `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `sub_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `grade` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('1', '1', '1', '90', '优秀');
INSERT INTO `score` VALUES ('2', '1', '2', '82', '良好');
INSERT INTO `score` VALUES ('3', '1', '3', '79', '一般');
INSERT INTO `score` VALUES ('4', '1', '4', '68', '及格');
INSERT INTO `score` VALUES ('5', '1', '5', '96', '优秀');
INSERT INTO `score` VALUES ('6', '2', '1', '65', '及格');
INSERT INTO `score` VALUES ('7', '21', '5', '80', '良好');
INSERT INTO `score` VALUES ('8', '21', '4', '60', '及格');
INSERT INTO `score` VALUES ('9', '2', '2', '80', '良好');
INSERT INTO `score` VALUES ('10', '2', '3', '79', '一般');
INSERT INTO `score` VALUES ('12', '2', '4', '60', '及格');
INSERT INTO `score` VALUES ('13', '2', '5', '80', '良好');
INSERT INTO `score` VALUES ('14', '1', '6', '0', '不及格');
INSERT INTO `score` VALUES ('15', '1', '7', '96', '优秀');
INSERT INTO `score` VALUES ('16', '20', '1', '60', '及格');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `sex` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `bj_id` int(11) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '小明', '男', '30', '1', null);
INSERT INTO `student` VALUES ('2', '山里来的二大王', '男', '35', '6', '');
INSERT INTO `student` VALUES ('3', '小李', '男', '20', '2', null);
INSERT INTO `student` VALUES ('4', '小字', '男', '21', '2', null);
INSERT INTO `student` VALUES ('5', '小红', '女', '20', '2', null);
INSERT INTO `student` VALUES ('6', '小刘', '男', '28', '1', null);
INSERT INTO `student` VALUES ('7', '小强', '男', '24', '1', null);
INSERT INTO `student` VALUES ('8', '小东', '男', '21', '4', null);
INSERT INTO `student` VALUES ('9', '小飞', '女', '21', '2', null);
INSERT INTO `student` VALUES ('15', '小亮', '男', '27', '4', null);
INSERT INTO `student` VALUES ('16', '小赵', '男', '22', '2', null);
INSERT INTO `student` VALUES ('17', '赵子龙', '男', '20', '2', null);
INSERT INTO `student` VALUES ('18', '刘备', '男', '36', '4', null);
INSERT INTO `student` VALUES ('20', '冯程程', '女', '26', '6', null);
INSERT INTO `student` VALUES ('23', '卿籁', '男', '23', '2', null);
INSERT INTO `student` VALUES ('24', '贺小风', '男', '22', '1', null);

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '安卓');
INSERT INTO `subject` VALUES ('2', '数据结构');
INSERT INTO `subject` VALUES ('3', '编译原理');
INSERT INTO `subject` VALUES ('4', '计算机组成');
INSERT INTO `subject` VALUES ('5', '高数');
INSERT INTO `subject` VALUES ('6', '计算机网络');
INSERT INTO `subject` VALUES ('7', '通信原理');
INSERT INTO `subject` VALUES ('13', '机械');
INSERT INTO `subject` VALUES ('14', '土木工程');
INSERT INTO `subject` VALUES ('15', '单元测试');

-- ----------------------------
-- Table structure for `sub_bj`
-- ----------------------------
DROP TABLE IF EXISTS `sub_bj`;
CREATE TABLE `sub_bj` (
  `bjId` int(11) NOT NULL DEFAULT '0',
  `subId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bjId`,`subId`),
  KEY `subId` (`subId`),
  CONSTRAINT `sub_bj_ibfk_1` FOREIGN KEY (`bjId`) REFERENCES `banji` (`id`),
  CONSTRAINT `sub_bj_ibfk_2` FOREIGN KEY (`subId`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_bj
-- ----------------------------
INSERT INTO `sub_bj` VALUES ('1', '1');
INSERT INTO `sub_bj` VALUES ('2', '1');
INSERT INTO `sub_bj` VALUES ('6', '1');
INSERT INTO `sub_bj` VALUES ('1', '2');
INSERT INTO `sub_bj` VALUES ('2', '2');
INSERT INTO `sub_bj` VALUES ('6', '2');
INSERT INTO `sub_bj` VALUES ('1', '3');
INSERT INTO `sub_bj` VALUES ('2', '3');
INSERT INTO `sub_bj` VALUES ('6', '3');
INSERT INTO `sub_bj` VALUES ('1', '4');
INSERT INTO `sub_bj` VALUES ('2', '4');
INSERT INTO `sub_bj` VALUES ('4', '4');
INSERT INTO `sub_bj` VALUES ('4', '5');
INSERT INTO `sub_bj` VALUES ('1', '6');
INSERT INTO `sub_bj` VALUES ('4', '6');
INSERT INTO `sub_bj` VALUES ('1', '7');
INSERT INTO `sub_bj` VALUES ('4', '7');
INSERT INTO `sub_bj` VALUES ('4', '13');
INSERT INTO `sub_bj` VALUES ('4', '14');
INSERT INTO `sub_bj` VALUES ('2', '15');
INSERT INTO `sub_bj` VALUES ('4', '15');

-- ----------------------------
-- View structure for `v_bj_sub`
-- ----------------------------
DROP VIEW IF EXISTS `v_bj_sub`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_bj_sub` AS (select `bj`.`id` AS `bjId`,`bj`.`name` AS `bjName`,`bj`.`nums` AS `nums`,`sub`.`id` AS `subId`,`sub`.`name` AS `subName` from ((`banji` `bj` left join `sub_bj` `m` on((`bj`.`id` = `m`.`bjId`))) left join `subject` `sub` on((`m`.`subId` = `sub`.`id`)))) ;

-- ----------------------------
-- View structure for `v_stu_bj_sub_score`
-- ----------------------------
DROP VIEW IF EXISTS `v_stu_bj_sub_score`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stu_bj_sub_score` AS (select `stu`.`id` AS `stuId`,`stu`.`name` AS `stuName`,`bj`.`id` AS `bjId`,`bj`.`name` AS `bjName`,`sub`.`id` AS `subId`,`sub`.`name` AS `subName`,`sc`.`id` AS `scId`,`sc`.`score` AS `scScore`,`sc`.`grade` AS `scGrade` from ((((`student` `stu` join `banji` `bj` on((`stu`.`bj_id` = `bj`.`id`))) join `sub_bj` `m` on((`bj`.`id` = `m`.`bjId`))) join `subject` `sub` on((`m`.`subId` = `sub`.`id`))) left join `score` `sc` on(((`sc`.`stu_id` = `stu`.`id`) and (`sc`.`sub_id` = `sub`.`id`))))) ;
DROP TRIGGER IF EXISTS `aa`;
DELIMITER ;;
CREATE TRIGGER `aa` BEFORE INSERT ON `score` FOR EACH ROW BEGIN
	declare grade varchar(50);
declare sc int;
set sc=new.score;
	if sc>=90
	then set grade='优秀';
	elseif sc>=80
	then set grade='良好';
	elseif sc>=70
	then set grade='一般';
	elseif sc>=60
	then set grade='及格';
	else 
  set grade='不及格';
	end if;
set new.grade=grade;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `bb`;
DELIMITER ;;
CREATE TRIGGER `bb` BEFORE UPDATE ON `score` FOR EACH ROW BEGIN
	declare grade varchar(50);
declare sc int;
set sc=new.score;
	if sc>=90
	then set grade='优秀';
	elseif sc>=80
	then set grade='良好';
	elseif sc>=70
	then set grade='一般';
	elseif sc>=60
	then set grade='及格';
	else 
  set grade='不及格';
	end if;
set new.grade=grade;

END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `a`;
DELIMITER ;;
CREATE TRIGGER `a` BEFORE INSERT ON `student` FOR EACH ROW update banji set nums=nums+1 where id=new.bj_id
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `b`;
DELIMITER ;;
CREATE TRIGGER `b` BEFORE UPDATE ON `student` FOR EACH ROW begin
update banji set nums=nums+1 where id=new.bj_id;
update banji set nums=nums-1 where id=old.bj_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `c`;
DELIMITER ;;
CREATE TRIGGER `c` BEFORE DELETE ON `student` FOR EACH ROW update banji set nums=nums-1 where id=old.bj_id
;;
DELIMITER ;
