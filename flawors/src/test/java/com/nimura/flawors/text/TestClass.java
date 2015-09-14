package com.nimura.flawors.text;

import com.nimura.flawors.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Limi on 26.06.2015.
 */
public class TestClass{
    private Model model;

    @Before
    public void setUp(){
        System.out.println("before");
        model = new Model();
    }

    @Test
    public void test(){
        assert  model.someFunc() == 4;
    }

    @Test
    public void test2(){
        assert  model.someFunc() == 5;
    }

    @After
    public void tearDown(){
        System.out.println("after");
    }
}
