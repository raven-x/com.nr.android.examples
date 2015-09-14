package com.nimura.flawors;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    Model model;

    protected void setUp(){
        model = new Model();
    }

    public void test(){
        assertEquals(model.someFunc(), 4);
    }

    public void test2(){
        assertEquals(model.someFunc(), 5);
    }
}