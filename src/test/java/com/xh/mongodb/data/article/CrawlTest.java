package com.xh.mongodb.data.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * CrawlTest
 *
 * @author xiaohe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlTest {

    @Autowired
    private Crawl crawl;

    @Test
    public void getInfoListTest() {
        crawl.getInfoList();
    }

}
