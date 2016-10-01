package com.luseen.autolinklibrary;

import android.support.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by diegotori on 10/1/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, constants = BuildConfig.class)
@SmallTest
public class UtilsTest {

    @Test
    public void getRegexByAutoLinkMode__Mode_Hashtag() {
        //Given Mode Hashtag
        final String expected = "(?:^|\\s|$)#[\\p{L}0-9_]*";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_HASHTAG, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Mention() {
        //Given Mode Mention
        final String expected = "(?:^|\\s|$|[.])@[\\p{L}0-9_]*";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_MENTION, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_URL() {
        //Given Mode URL
        final String expected = "(^|[\\s.:;?\\-\\]<\\(])" +
                "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#]" +
                "(\\(\\))?)" +
                "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_URL, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Phone() {
        //Given Mode Phone
        final String expected = "(\\+[0-9]+[\\- \\.]*)?"        // +<digits><sdd>*
                + "(\\([0-9]+\\)[\\- \\.]*)?"   // (<digits>)<sdd>*
                + "([0-9][0-9\\- \\.]+[0-9])";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_PHONE, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Email() {
        //Given Mode Email
        final String expected = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_EMAIL, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Custom() {
        //Given Mode Custom
        final String expected = "custom_regex";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_CUSTOM, expected);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Custom_NullValue() {
        //Given Mode Custom with a null regex
        final String expected = "(^|[\\s.:;?\\-\\]<\\(])" +
                "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#]" +
                "(\\(\\))?)" +
                "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_CUSTOM, null);

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Custom_EmptyValue() {
        //Given Mode Custom with an empty regex
        final String expected = "(^|[\\s.:;?\\-\\]<\\(])" +
                "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#]" +
                "(\\(\\))?)" +
                "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_CUSTOM, "");

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    public void getRegexByAutoLinkMode__Mode_Custom_ValueLengthNotGreaterThanTwo() {
        //Given Mode Custom with a regex whose length is not greater than two
        final String expected = "(^|[\\s.:;?\\-\\]<\\(])" +
                "((https?://|www\\.|pic\\.)[-\\w;/?:@&=+$\\|\\_.!~*\\|'()\\[\\]%#,☺]+[\\w/#]" +
                "(\\(\\))?)" +
                "(?=$|[\\s',\\|\\(\\).:;?\\-\\[\\]>\\)])";

        //When getting the actual regex
        final String actual = Utils.getRegexByAutoLinkMode(AutoLinkMode.MODE_CUSTOM, "ab");

        //Then verify that it's equal to our expected value
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }
}
