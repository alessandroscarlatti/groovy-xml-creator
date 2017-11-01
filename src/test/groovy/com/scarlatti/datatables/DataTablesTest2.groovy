package com.scarlatti.datatables

import spock.lang.Specification
import spock.lang.Unroll

import java.awt.Color

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Wednesday, 10/25/2017
 */
class DataTablesTest2 extends Specification {

    @Unroll
    "can I call a self-invoking closure with built params: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << ScenarioUtil.scenarios {
                scenario(
                        a: doSomethingComplexToGetAnInteger(),
                        b: 0,
                        c: 1
                )
                scenario(
                        a: 1,
                        b: 2,
                        c: 2
                )
                scenario(
                        a: 4,
                        b: 5,
                        c: 5
                )
            }

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I get better scenario language: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << [
                    [
                            a: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1),
                            b: 0,
                            c: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1)
                    ]
                    , [
                            a: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1),
                            b: 0,
                            c: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1)
                    ]
                    , [
                            a: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1),
                            b: 0,
                            c: ComplexType.newComplexType2()
                                    .name("asdf")
                                    .age(4)
                                    .hairColor(Color.BLACK)
                                    .heightInFeet(55)
                                    .build()
                                    .getInteger(1)
                    ]
            ]

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I use a list of maps: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << [
                [
                    a: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1),
                    b: 0,
                    c: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1)
                ],
                [
                    a: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1),
                    b: 0,
                    c: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1)
                ],
                [
                    a: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1),
                    b: 0,
                    c: ComplexType.newComplexType2()
                            .name("asdf")
                            .age(4)
                            .hairColor(Color.BLACK)
                            .heightInFeet(55)
                            .build()
                            .getInteger(1)
                ]
            ]

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I call a list of map params: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << ParamUtil3.loadTestCases {
                testCase(
                        a: ComplexType.newComplexType2()
                                .name("asdf")
                                .age(4)
                                .hairColor(Color.BLACK)
                                .heightInFeet(55)
                                .build()
                                .getInteger(1),
                        b: 0,
                        c: ComplexType.newComplexType2()
                                .name("asdf")
                                .age(4)
                                .hairColor(Color.BLACK)
                                .heightInFeet(55)
                                .build()
                                .getInteger(1)
                )

                testCase(
                        a: ComplexType.newComplexType2()
                                .name("asdf")
                                .age(4)
                                .hairColor(Color.BLACK)
                                .heightInFeet(55)
                                .build()
                                .getInteger(1),
                        b: 2,
                        c: ComplexType.newComplexType2()
                                .name("asdf")
                                .age(4)
                                .hairColor(Color.BLACK)
                                .heightInFeet(55)
                                .build()
                                .getInteger(2)
                )
                testCase(
                        a: ComplexType.newComplexType2()
                        .name("asdf")
                        .age(4)
                        .hairColor(Color.BLACK)
                        .heightInFeet(55)
                        .build()
                        .getInteger(1),
                    b: 5,
                    c: 5
                )
            }

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I call a named params constructor: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << new ScenarioUtil(a: 4)

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I call reusable params method: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:

            params << dataTable()

            a = params.a
            b = params.b
            c = params.c
    }

    @Unroll
    "can I call reusable params method without params: max of #a and #b gives #c"() {
        expect:
            Math.max(a, b) == c
        where:

            params << dataTable()

            a = params.a
            b = params.b
            c = params.c
    }


        def dataTable() {
            return [
                // case 1
                [
                    a: 1,
                    b: 0,
                    c: 1
                ],

                // case 2
                [
                    a: 1,
                    b: 2,
                    c: 2
                ],

                // case 3
                [
                    a: 4,
                    b: 5,
                    c: 5
                ]
            ]
        }


    static int doSomethingComplexToGetAnInteger() {
        return 1
    }

}

class ScenarioUtil {

    List<Map<String, Object>> spockCaseList = new LinkedList<>()

    ScenarioUtil() {

    }

    static scenarios(Closure params) {

        ScenarioUtil paramUtilDelegate = new ScenarioUtil()
        params.setDelegate(paramUtilDelegate)
        params()

        return paramUtilDelegate.spockCaseList
    }

    def propertyMissing(String name, value) {
        println "property missing $name"
    }

    void scenario(Map<String, Object> params) {

        // create a new case in the spockCaseList
        spockCaseList.add(params)

//        // for running a closure
//        for (String key : params.keySet()) {
//            Object param = params.get(key)
//            if (param != null) {
//                if (param instanceof Closure) {
//                    param()
//                }
//            }
//        }
    }
}

class ParamUtil3 {

    List<Map<String, Object>> spockCaseList = new LinkedList<>()

    ParamUtil3() {

    }

    static loadTestCases(Closure params) {

        ParamUtil3 paramUtilDelegate = new ParamUtil3()
        params.setDelegate(paramUtilDelegate)
        params()

        return paramUtilDelegate.spockCaseList
    }

    def propertyMissing(String name, value) {
        println "property missing $name"
        spockCaseList.add(value)
    }

    void testCase(params) {

        // create a new case in the spockCaseList
        spockCaseList.add(params)

//        // for running a closure
//        for (String key : params.keySet()) {
//            Object param = params.get(key)
//            if (param != null) {
//                if (param instanceof Closure) {
//                    param()
//                }
//            }
//        }
    }
}

class Scenario extends HashMap<String, Object> {

    Scenario(params) {

    }

    Scenario() {

    }

    static scenarios(Closure params) {

        ScenarioDelegate delegate = new ScenarioDelegate()
        params.setDelegate(delegate)
        params()

        return delegate.spockCaseList
    }

    static class ScenarioDelegate {
        List<Map<String, Object>> spockCaseList = new LinkedList<>()

        void "#scenario"(Map<String, Object> params) {
            spockCaseList.add(params)
        }
    }
}

class ScenarioUtil2 {
    def propertyMissing(String name, value) {
        println "property missing: $name"
    }
}