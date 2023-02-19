package com.yintp.design.pattern.template;

import org.junit.Test;

/**
 * @author yintp
 */
public class TemplateTest {
    @Test
    public void testCase1() {
        Game game = new Football();
        game.play();
    }

    @Test
    public void testCase2() {
        Game game = new Basketball();
        game.play();
    }
}
