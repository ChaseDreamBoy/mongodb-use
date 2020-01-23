
CREATE TABLE `article` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `article_id` varchar(255) NULL DEFAULT '' COMMENT '文章id',
 `title` varchar(1000) NULL DEFAULT '' COMMENT '文章标题',
 `create_time` varchar(50) NULL DEFAULT '' COMMENT '创建时间',
 `update_time` varchar(50) NULL DEFAULT '' COMMENT '最后修改时间',
 `tags` varchar(1000) NULL DEFAULT '' COMMENT '标签，以英文逗号隔开',
 `reply_count` int(10) NULL DEFAULT 0 COMMENT '回复次数',
 `view_count` int(10) NULL DEFAULT 0 COMMENT '浏览次数',
 `posters` varchar(255) NULL DEFAULT '' COMMENT '提交人列表，以逗号隔开',
 PRIMARY KEY (`id`)
) COMMENT = '文章表';

CREATE TABLE `mongo_article`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id ',
  `detail_url` varchar(255) NULL DEFAULT '' COMMENT '详情url',
  `title` varchar(1000) NULL DEFAULT '' COMMENT '标题',
  `types` varchar(255) NULL DEFAULT '' COMMENT '问题类型',
  `publish_user` varchar(255) NULL DEFAULT '' COMMENT '发布人',
  `create_time` varchar(50) NULL DEFAULT '' COMMENT '创建时间',
  `content` text NULL COMMENT '文章内容',
  `answer_num` int(10) NULL DEFAULT 0 COMMENT '回复数量',
  `read_num` int(10) NULL DEFAULT 0 COMMENT '阅读数量',
  `vote_num` int(10) NULL DEFAULT 0 COMMENT '投票数量',
  `answers` varchar(255) NULL DEFAULT '' COMMENT '回复id，多个以逗号隔开',
  PRIMARY KEY (`id`)
) COMMENT = 'mongo 社区技术讨论文章详情';

CREATE TABLE `mongo_article_answer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `answer_content` text NULL COMMENT '回复内容',
  `answer_user` varchar(255) NULL DEFAULT '' COMMENT '回复人',
  `answer_time` varchar(100) NULL DEFAULT '' COMMENT '回复时间',
  PRIMARY KEY (`id`)
) COMMENT = 'mongo社区文章回复';
