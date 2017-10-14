package com.scarlatti

/**
 * Created by pc on 10/14/2017.
 */
class MethodMissingTest {

    static void main(String[] args) {
        PenguinDelegate delegateA = new PenguinDelegate("A")
        delegateA.doSomething()

        PenguinDelegate delegateB = new PenguinDelegate("B")
        delegateB.doSomething()

        PenguinDelegate delegateC = new PenguinDelegate("C")
        delegateC.something

        PenguinDelegate delegateD = new PenguinDelegate("D")
        delegateD.something
    }
}
