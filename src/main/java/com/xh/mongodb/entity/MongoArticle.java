package com.xh.mongodb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * mongo 社区技术讨论文章详情
 * </p>
 *
 * @author xiaohe
 * @since 2020-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MongoArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 详情url
     */
    private String detailUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 问题类型
     */
    private String types;

    /**
     * 发布人
     */
    private String publishUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 回复数量
     */
    private Integer answerNum;

    /**
     * 阅读数量
     */
    private Integer readNum;

    /**
     * 投票数量
     */
    private Integer voteNum;

    /**
     * 回复id，多个以逗号隔开
     */
    private String answers;


}
