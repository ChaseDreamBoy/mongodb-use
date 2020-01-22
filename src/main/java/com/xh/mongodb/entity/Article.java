package com.xh.mongodb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author xiaohe
 * @since 2020-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后修改时间
     */
    private String updateTime;

    /**
     * 标签，以英文逗号隔开
     */
    private String tags;

    /**
     * 回复次数
     */
    private Integer replyCount;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 提交人列表，以逗号隔开
     */
    private String posters;


}
