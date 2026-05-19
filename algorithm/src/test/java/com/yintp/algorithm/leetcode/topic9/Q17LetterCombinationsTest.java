package com.yintp.algorithm.leetcode.topic9;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class Q17LetterCombinationsTest {

    private final Q17LetterCombinations solution = new Q17LetterCombinations();

    @Test
    public void testCase1() {
        List<String> result = solution.letterCombinations("23");
        Assert.assertEquals(9, result.size());
    }

    @Test
    public void testEmpty() {
        List<String> result = solution.letterCombinations("");
        Assert.assertTrue(result.isEmpty());
    }
}
