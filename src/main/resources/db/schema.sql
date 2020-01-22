
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
