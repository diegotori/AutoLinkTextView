package com.luseen.autolinklibrary;

import android.support.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by diegotori on 10/1/16.
 */
@RunWith(BlockJUnit4ClassRunner.class)
@SmallTest
public class AutoLinkItemTest {

    @Test
    public void getAutoLinkMode() {
        //Given a new instance
        final AutoLinkItem autoLinkItem  = new AutoLinkItem(0, 0, "Blah",
                AutoLinkMode.MODE_HASHTAG);

        //When getting the AutoLinkMode
        final AutoLinkMode actual = autoLinkItem.getAutoLinkMode();

        //Then verify that it's not null and equal to our expected value.
        assertThat(actual)
                .isNotNull()
                .isEqualTo(AutoLinkMode.MODE_HASHTAG);
    }

    @Test
    public void getMatchedText() {
        //Given a new instance
        final AutoLinkItem autoLinkItem  = new AutoLinkItem(0, 0, "Blah",
                AutoLinkMode.MODE_HASHTAG);

        //When getting the matched text
        final String actual = autoLinkItem.getMatchedText();

        //Then verify that it's not null and equal to our expected value.
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Blah");
    }

    @Test
    public void getStartPoint() {
        //Given a new instance
        final AutoLinkItem autoLinkItem  = new AutoLinkItem(100, 0, "Blah",
                AutoLinkMode.MODE_HASHTAG);

        //When getting the start point
        final int actual = autoLinkItem.getStartPoint();

        //Then verify that it's not null and equal to our expected value.
        assertThat(actual)
                .isNotZero()
                .isEqualTo(100);
    }

    @Test
    public void getEndPoint() {
        //Given a new instance
        final AutoLinkItem autoLinkItem  = new AutoLinkItem(0, 200, "Blah",
                AutoLinkMode.MODE_HASHTAG);

        //When getting the end point
        final int actual = autoLinkItem.getEndPoint();

        //Then verify that it's not null and equal to our expected value.
        assertThat(actual)
                .isNotZero()
                .isEqualTo(200);
    }
}
