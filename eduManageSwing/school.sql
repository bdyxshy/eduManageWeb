/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : school

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-09-04 09:59:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `banji`
-- ----------------------------
DROP TABLE IF EXISTS `banji`;
CREATE TABLE `banji` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `stuNums` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banji
-- ----------------------------
INSERT INTO `banji` VALUES ('1', 'Java1班', '2');
INSERT INTO `banji` VALUES ('4', '安卓班', null);
INSERT INTO `banji` VALUES ('5', '爆破班', null);

-- ----------------------------
-- Table structure for `score`
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `cou_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('1', '114', '1', '100', '良好');
INSERT INTO `score` VALUES ('7', '114', '2', '51', '不及格');
INSERT INTO `score` VALUES ('8', '125', '3', '80', '一般');
INSERT INTO `score` VALUES ('9', '114', '3', '0', '及格');
INSERT INTO `score` VALUES ('10', '114', '4', '90', '不及格');
INSERT INTO `score` VALUES ('11', '117', '4', '45', '不及格');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `bj_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `aa` (`bj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('114', '3', '2', '1', '1');
INSERT INTO `student` VALUES ('117', '1', '2', '3', '1');
INSERT INTO `student` VALUES ('125', '权威', '权威', '3', '4');

-- ----------------------------
-- Table structure for `stu_cou`
-- ----------------------------
DROP TABLE IF EXISTS `stu_cou`;
CREATE TABLE `stu_cou` (
  `bj_id` int(11) NOT NULL DEFAULT '0',
  `sub_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bj_id`,`sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_cou
-- ----------------------------
INSERT INTO `stu_cou` VALUES ('1', '1');
INSERT INTO `stu_cou` VALUES ('1', '2');
INSERT INTO `stu_cou` VALUES ('1', '3');
INSERT INTO `stu_cou` VALUES ('1', '4');
INSERT INTO `stu_cou` VALUES ('4', '2');
INSERT INTO `stu_cou` VALUES ('4', '3');
INSERT INTO `stu_cou` VALUES ('5', '6');

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '微机原理');
INSERT INTO `subject` VALUES ('2', '数据结构');
INSERT INTO `subject` VALUES ('3', '组成原理');
INSERT INTO `subject` VALUES ('4', '计算机网络');
INSERT INTO `subject` VALUES ('6', '数据挖掘');

-- ----------------------------
-- View structure for `v_stu_bj_sub_score`
-- ----------------------------
DROP VIEW IF EXISTS `v_stu_bj_sub_score`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stu_bj_sub_score` AS (select `sc`.`id` AS `scId`,`stu`.`id` AS `stuId`,`stu`.`name` AS `stuName`,`bj`.`id` AS `bjId`,`bj`.`name` AS `bjName`,`sub`.`id` AS `subId`,`sub`.`name` AS `subName`,`sc`.`score` AS `score`,`sc`.`grade` AS `grade` from ((((`student` `stu` join `banji` `bj` on((`stu`.`bj_id` = `bj`.`id`))) join `stu_cou` `m` on((`bj`.`id` = `m`.`bj_id`))) join `subject` `sub` on((`m`.`sub_id` = `sub`.`id`))) left join `score` `sc` on(((`sc`.`stu_id` = `stu`.`id`) and (`sc`.`cou_id` = `sub`.`id`))))) ;

-- ----------------------------
-- Procedure structure for `setGrade`
-- ----------------------------
DROP PROCEDURE IF EXISTS `setGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `setGrade`()
BEGIN
DECLARE tid int;
DECLARE tscore int;
DECLARE tgrade VARCHAR(50);
DECLARE isloop int DEFAULT 1;
DECLARE cur CURSOR for select id,score from score;
DECLARE CONTINUE HANDLER FOR NOT found set isloop=0;	
open cur;
fetch cur into tid,tscore;
while isloop>0 do
	
	if tscore>=90
  then set tgrade='优秀';
	elseif tscore>=80
  then set tgrade='良好';
	elseif tscore>=70
  then set tgrade='一般';
	elseif tscore>=60
  then set tgrade='及格';
	else
  set tgrade='不及格';
	end if;
update score set grade=tgrade where id=tid;
fetch cur into tid,tscore;
 end while;
CLOSE cur;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `PrintGrade`
-- ----------------------------
DROP FUNCTION IF EXISTS `PrintGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `PrintGrade`(`sc` int) RETURNS varchar(50) CHARSET utf8
BEGIN
  declare grade varchar(50);
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
	RETURN grade;
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `aa`;
DELIMITER ;;
CREATE TRIGGER `aa` BEFORE INSERT ON `score` FOR EACH ROW begin 
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
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `b`;
DELIMITER ;;
CREATE TRIGGER `b` BEFORE INSERT ON `student` FOR EACH ROW begin
update banji set stuNums=stuNums+1 where id=new.bj_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `a`;
DELIMITER ;;
CREATE TRIGGER `a` BEFORE UPDATE ON `student` FOR EACH ROW begin
update banji set stuNums=stuNums+1 where id=new.bj_id;
update banji set stuNums=stuNums-1 where id=old.bj_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `c`;
DELIMITER ;;
CREATE TRIGGER `c` BEFORE DELETE ON `student` FOR EACH ROW begin
update banji set stuNums=stuNums-1 where id=old.bj_id;
end
;;
DELIMITER ;
