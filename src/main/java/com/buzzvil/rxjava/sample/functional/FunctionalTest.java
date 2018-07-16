package com.buzzvil.rxjava.sample.functional;

import java.util.function.Function;

/**
 * 함수형 프로그래밍 관련 테스트
 */
public class FunctionalTest {
    public static void main(String[] args) {
        FunctionalTest functionalTest = new FunctionalTest();
        functionalTest.testPureFunctionComposite();
    }


    /**
     * 순수 함수간의 결합은 새로운 순수 함수를 만들어내며, 이는 스레드 안전한 함수.
     */
    public void testPureFunctionComposite(){
        for (int v = 0; v < 100; v++) {
            final int x = v;
            new Thread(() -> System.out.println(i.apply(x))).start();
        }
    }

    Function<Integer, Integer> f = x -> x + 2;

    Function<Integer, Integer> g = x -> x * 2;

    Function<Integer, Integer> h = x -> x - 2;

    Function<Integer, Integer> i = f.andThen(g).andThen(h);
}
