package com.luseen.autolinklibrary;

import android.support.test.filters.SmallTest;
import android.text.Spannable;
import android.view.MotionEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by diegotori on 10/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
@SmallTest
public class LinkTouchMovementMethodTest {
    @Mock
    private MotionEvent mockMotionEvent;

    @Mock
    private Spannable mockSpannable;

    @Mock
    private TouchableSpan mockTouchableSpan;

    private LinkTouchMovementMethod ltmMethod;

    @Before
    public void setUp() {
        ltmMethod = new LinkTouchMovementMethod();
    }

    @After
    public void tearDown() {
        ltmMethod = null;
    }

    @Test
    public void onTouchEvent() {
        
    }

}
