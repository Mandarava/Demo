SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `ID` int(11) NOT NULL,
  `PID` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ISPARENT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', null, '菜单', '1');
INSERT INTO `dept` VALUES ('11', '1', '角色管理', '1');
INSERT INTO `dept` VALUES ('12', '1', '部门管理', '1');
INSERT INTO `dept` VALUES ('13', '1', '人员管理', '1');
INSERT INTO `dept` VALUES ('111', '11', '角色查询', '0');
INSERT INTO `dept` VALUES ('121', '12', '部门查询', '0');
INSERT INTO `dept` VALUES ('131', '13', '人员查询', '0');
