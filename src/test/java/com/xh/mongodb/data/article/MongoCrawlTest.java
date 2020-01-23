package com.xh.mongodb.data.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MongoCrawlTest
 *
 * @author xiaohe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCrawlTest {

    @Autowired
    private MongoCrawl mongoCrawl;

    @Test
    public void saveDate() {
        mongoCrawl.saveDate();
    }

}
