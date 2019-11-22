CREATE DATABASE  IF NOT EXISTS `demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `demo`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `demo`
--

DROP TABLE IF EXISTS `demo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `demo` (
  `id` varchar(50) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `ts` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demo`
--

LOCK TABLES `demo` WRITE;
/*!40000 ALTER TABLE `demo` DISABLE KEYS */;
INSERT INTO `demo` VALUES ('320ea7b2-90e7-4b0f-8f18-7ef9cb90df08','测试11555555555','内容2','2019-08-21 00:15:12'),('339e22fb-264b-4807-b4e5-e28b84037e61','测试11555555555','内容2','2019-08-21 21:25:07'),('4db28df6-4c53-4c12-ae08-9095b3e870ba','测试11555555555','内容2','2019-08-21 22:46:27'),('75afad48-a4f7-44dd-8f4c-a7554d1048c6','测试11555555555','内容2','2019-08-21 22:55:41'),('c183bb0f-875a-4829-8485-0e08e496730e','测试11555555555','内容2','2019-08-21 22:39:58'),('f9cef570-0d09-46f1-92a2-d39287775b6d','测试11555555555','内容2','2019-08-21 22:37:04'),('fd5a59aa-4978-413d-a00d-22e199797601','测试11555555555','内容2','2019-08-21 00:09:07');
/*!40000 ALTER TABLE `demo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `democontent`
--

DROP TABLE IF EXISTS `democontent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `democontent` (
  `id` varchar(50) NOT NULL,
  `content` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `democontent`
--

LOCK TABLES `democontent` WRITE;
/*!40000 ALTER TABLE `democontent` DISABLE KEYS */;
/*!40000 ALTER TABLE `democontent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_dict`
--

DROP TABLE IF EXISTS `mtnj_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `DictCode` int(11) DEFAULT NULL,
  `DictName` varchar(45) NOT NULL,
  `ParentId` int(11) DEFAULT '0',
  `Category` int(11) NOT NULL,
  `Remark` varchar(45) DEFAULT NULL,
  `AddUser` varchar(45) DEFAULT NULL,
  `AddDate` datetime DEFAULT NULL,
  `UpdateUser` varchar(45) DEFAULT NULL,
  `UpdateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `LogicPK` (`DictCode`,`Category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_dict`
--

LOCK TABLES `mtnj_dict` WRITE;
/*!40000 ALTER TABLE `mtnj_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `mtnj_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_menu`
--

DROP TABLE IF EXISTS `mtnj_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_menu` (
  `id` varchar(36) NOT NULL,
  `menuCode` varchar(20) NOT NULL,
  `menuName` varchar(20) NOT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `url` varchar(200) NOT NULL,
  `parentId` varchar(36) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `sortCode` int(11) NOT NULL DEFAULT '1000',
  `status` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_menu`
--

LOCK TABLES `mtnj_menu` WRITE;
/*!40000 ALTER TABLE `mtnj_menu` DISABLE KEYS */;
INSERT INTO `mtnj_menu` VALUES ('1','1','应用系统','folder','0','0','根目录',1000,_binary ''),('2','1_1','菜单管理','folder','0','1',NULL,1000,_binary ''),('3','1_2','角色管理','folder','0','1',NULL,1000,_binary ''),('4','1_3','用户管理','folder','0','1',NULL,1000,_binary '');
/*!40000 ALTER TABLE `mtnj_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_role`
--

DROP TABLE IF EXISTS `mtnj_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_role` (
  `Id` varchar(36) NOT NULL,
  `ParentId` varchar(36) NOT NULL DEFAULT '0',
  `RoleName` varchar(45) NOT NULL DEFAULT '',
  `Status` bit(1) NOT NULL DEFAULT b'1' COMMENT '0:停用 1：启用',
  `sortCode` int(11) NOT NULL DEFAULT '100',
  `Remark` varchar(200) DEFAULT '' COMMENT '备注',
  `AddUser` varchar(36) NOT NULL DEFAULT '0',
  `AddDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateUser` varchar(36) DEFAULT '0',
  `UpdateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_mtnj_role_ParentId_RoleName` (`ParentId`,`RoleName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_role`
--

LOCK TABLES `mtnj_role` WRITE;
/*!40000 ALTER TABLE `mtnj_role` DISABLE KEYS */;
INSERT INTO `mtnj_role` VALUES ('17104c90-8463-456f-9bcd-4034d823e147','-','操作员',_binary '',1000,'操作员操作','1','2019-11-05 09:52:26','0',NULL),('99ea0df3-7fc0-470c-9ff8-1bf0ab1508f9','-','管理员',_binary '\0',1000,'超级管理员','1','2019-11-05 09:52:05','0',NULL);
/*!40000 ALTER TABLE `mtnj_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_role_menu`
--

DROP TABLE IF EXISTS `mtnj_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_role_menu` (
  `Id` varchar(36) NOT NULL,
  `MenuId` varchar(36) DEFAULT NULL,
  `RoleId` varchar(36) DEFAULT NULL,
  `AddDate` datetime DEFAULT NULL,
  `AddUser` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_role_menu`
--

LOCK TABLES `mtnj_role_menu` WRITE;
/*!40000 ALTER TABLE `mtnj_role_menu` DISABLE KEYS */;
INSERT INTO `mtnj_role_menu` VALUES ('13ce352e-448f-4760-acb8-a5918b75dcd4','4','17104c90-8463-456f-9bcd-4034d823e147','2019-11-08 11:08:54','1'),('1bfa0da2-7d4c-4025-9874-92babceb7e7c','2','99ea0df3-7fc0-470c-9ff8-1bf0ab1508f9','2019-11-08 11:07:33','1'),('9d17d351-d57b-420e-b9e5-4e6d805da4db','1','99ea0df3-7fc0-470c-9ff8-1bf0ab1508f9','2019-11-08 11:07:33','1'),('bad11a6f-e901-4b76-9a99-8797e5a062dc','3','99ea0df3-7fc0-470c-9ff8-1bf0ab1508f9','2019-11-08 11:07:33','1'),('c44d5baa-141e-4182-853d-ddf7c36dcfea','2','17104c90-8463-456f-9bcd-4034d823e147','2019-11-08 19:11:27','1'),('e568b0d8-6d35-424d-babe-dafd8e3dcaac','4','99ea0df3-7fc0-470c-9ff8-1bf0ab1508f9','2019-11-08 19:11:44','1'),('ea4c1962-3ce3-48b4-894e-22b11008b0d1','1','17104c90-8463-456f-9bcd-4034d823e147','2019-11-08 11:08:54','1');
/*!40000 ALTER TABLE `mtnj_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_user`
--

DROP TABLE IF EXISTS `mtnj_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_user` (
  `Id` varchar(36) NOT NULL,
  `UserAccount` varchar(50) NOT NULL COMMENT '用户账号',
  `Password` varchar(50) NOT NULL COMMENT '密码',
  `UserName` varchar(20) DEFAULT '' COMMENT '姓名',
  `avatar` varchar(500) DEFAULT NULL,
  `Sex` tinyint(1) NOT NULL DEFAULT '1' COMMENT '性别 0：女  1：男',
  `Birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `Tel` varchar(20) DEFAULT '' COMMENT '电话',
  `CellPhone` varchar(11) DEFAULT '' COMMENT '手机',
  `Email` varchar(50) NOT NULL COMMENT '邮箱',
  `Status` bit(1) NOT NULL DEFAULT b'1' COMMENT '0：冻结 1:可用 ',
  `SuperAdmin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:否  1：是',
  `AddUser` varchar(36) NOT NULL DEFAULT '0' COMMENT '添加人',
  `AddDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加日期',
  `UpdateUser` varchar(36) DEFAULT '0' COMMENT '修改人',
  `UpdateDate` datetime DEFAULT NULL COMMENT '修改日期',
  `LastLoginDate` datetime DEFAULT NULL,
  `LastLoginArea` varchar(50) DEFAULT NULL,
  `ParentId` varchar(36) DEFAULT NULL,
  `ParentName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_user`
--

LOCK TABLES `mtnj_user` WRITE;
/*!40000 ALTER TABLE `mtnj_user` DISABLE KEYS */;
INSERT INTO `mtnj_user` VALUES ('1','admin','21232F297A57A5A743894A0E4A801FC3','超级管理员','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1,'2019-10-29 00:00:00','15901057811','15901057811','450152518@qq.com',_binary '',1,'0','2019-10-29 09:49:31','0',NULL,'2019-11-15 00:43:05','北京-昌平','0',NULL),('2','lizhaoyang','21232F297A57A5A743894A0E4A801FC3','李朝阳','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1,'2019-10-29 00:00:00','15901057811','15901057811','450152519@qq.com',_binary '',0,'1','2019-10-29 09:49:31','0',NULL,'2019-11-05 19:59:08','北京-昌平','0',NULL);
/*!40000 ALTER TABLE `mtnj_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mtnj_user_role`
--

DROP TABLE IF EXISTS `mtnj_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mtnj_user_role` (
  `Id` varchar(36) NOT NULL,
  `UserId` varchar(36) NOT NULL,
  `RoleId` varchar(36) NOT NULL,
  `AddUser` varchar(36) NOT NULL,
  `AddDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtnj_user_role`
--

LOCK TABLES `mtnj_user_role` WRITE;
/*!40000 ALTER TABLE `mtnj_user_role` DISABLE KEYS */;
INSERT INTO `mtnj_user_role` VALUES ('2f069718-1572-4a86-9397-0c6637927ff7','2','17104c90-8463-456f-9bcd-4034d823e147','1','2019-11-08 19:00:53');
/*!40000 ALTER TABLE `mtnj_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-20 22:13:41
