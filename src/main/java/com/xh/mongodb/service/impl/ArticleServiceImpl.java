package com.xh.mongodb.service.impl;

import com.xh.mongodb.entity.Article;
import com.xh.mongodb.mapper.ArticleMapper;
import com.xh.mongodb.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author xiaohe
 * @since 2020-01-22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
