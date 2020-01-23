package com.xh.mongodb.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * mongo社区文章回复
 * </p>
 *
 * @author xiaohe
 * @since 2020-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MongoArticleAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 回复内容
     */
    private String answerContent;

    /**
     * 回复人
     */
    private String answerUser;

    /**
     * 回复时间
     */
    private String answerTime;


}
