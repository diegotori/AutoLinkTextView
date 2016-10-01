package com.luseen.autolinklibrary;

import android.graphics.Color;
import android.support.test.filters.SmallTest;
import android.text.TextPaint;
import android.view.View;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by diegotori on 10/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
@SmallTest
public class TouchableSpanTest {
    @Mock
    private TextPaint mockTextPaint;

    private TouchableSpan touchableSpan;


    @After
    public void tearDown(){
        touchableSpan = null;
    }

    @Test
    public void updateDrawState(){
        final int mockNormalTextColor = 12345;
        final int mockPressedTextColor = 54321;
        touchableSpan = new MockTouchableSpan(mockNormalTextColor, mockPressedTextColor);

        touchableSpan.updateDrawState(mockTextPaint);

        verify(mockTextPaint).setColor(mockNormalTextColor);
        verify(mockTextPaint).setUnderlineText(false);
        assertThat(mockTextPaint.bgColor)
                .isEqualTo(Color.TRANSPARENT);
    }

    @Test
    public void updateDrawState__Pressed(){
        final int mockNormalTextColor = 12345;
        final int mockPressedTextColor = 54321;
        touchableSpan = new MockTouchableSpan(mockNormalTextColor, mockPressedTextColor);
        touchableSpan.setPressed(true);

        touchableSpan.updateDrawState(mockTextPaint);

        verify(mockTextPaint).setColor(mockPressedTextColor);
        verify(mockTextPaint).setUnderlineText(false);
        assertThat(mockTextPaint.bgColor)
                .isEqualTo(Color.TRANSPARENT);
    }


    static class MockTouchableSpan extends TouchableSpan {

        MockTouchableSpan(int normalTextColor, int pressedTextColor) {
            super(normalTextColor, pressedTextColor);
        }

        @Override
        public void onClick(View widget) {}
    }
}
