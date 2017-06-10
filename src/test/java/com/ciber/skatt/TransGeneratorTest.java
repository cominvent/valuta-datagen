package com.ciber.skatt;

import static org.junit.Assert.*;

/**
 * Created by janhoy on 11.06.2017.
 */
public class TransGeneratorTest {
    @org.junit.Test
    public void generate() throws Exception {
        TransGenerator tg = TransGenerator.getInstance();
        tg.setSeed(4);
        tg.init(new TransGenerator.Config());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
        System.out.println(tg.generate());
    }

}