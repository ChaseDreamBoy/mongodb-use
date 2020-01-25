package com.xh.mongodb.sync;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SimpleSyncTest
 *
 * @author xiaohe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleSyncTest {

    @Autowired
    private SimpleSync simpleSync;

    @Test
    public void syncTest() {
        this.simpleSync.sync();
    }

}
