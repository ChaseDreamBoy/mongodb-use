package com.xh.mongodb.service.impl;

import com.xh.mongodb.entity.MongoArticle;
import com.xh.mongodb.mapper.MongoArticleMapper;
import com.xh.mongodb.service.IMongoArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * mongo 社区技术讨论文章详情 服务实现类
 * </p>
 *
 * @author xiaohe
 * @since 2020-01-23
 */
@Service
public class MongoArticleServiceImpl extends ServiceImpl<MongoArticleMapper, MongoArticle> implements IMongoArticleService {

}
