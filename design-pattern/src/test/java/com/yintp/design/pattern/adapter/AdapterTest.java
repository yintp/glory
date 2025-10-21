package com.yintp.design.pattern.adapter;

import org.junit.Test;

/**
 * @author zihao.yin
 * @since 2025/10/21 14:38
 */
public class AdapterTest {
    @Test
    public void testAdapter() {
        // 使用类适配器 输出：被适配者：执行特定的请求
        Target classTarget = new ClassAdapter();
        classTarget.request();
    }
}
