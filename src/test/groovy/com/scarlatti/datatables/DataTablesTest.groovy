package com.scarlatti.datatables

import spock.lang.Specification
import spock.lang.Unroll

class SampleTestOrig extends Specification {

    @Unroll
    "Using individual params: max of #a and #b gives #c"() {
        expect:
            Math.max(a, b) == c
        where:
        a << aProvider()
        b << bProvider()
        c << cProvider()
    }

    private List<Integer> aProvider() {
        [1 ,2 ,4]
    }
    private List<Integer> bProvider() {
        [0 ,2 ,5]
    }

    private List<Integer> cProvider() {
        [1 ,2 ,5]
    }

    @Unroll
    "Using map params: max of #params.a and #params.b gives #params.c"(Map<String, Integer> params) {
        expect:
            Math.max(params.a, params.b) == params.c
        where:
            params = {
                println "self execution works."
                return [a: 1, b: 0, c: 1]
            }()
    }

    @Unroll
    "Using self-referencing params: max of #a and #b gives #c"(int a, int b, int c) {
        expect:
            Math.max(a, b) == c
        where:
            a << [1]
            b << [a - 1]
            c = 1
    }

    @Unroll
    "Using self-referencing params with map literal: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
        Math.max(a, b) == c
        where:

        params << [
                [a: 1, b: 0, c: 1],
                [a: 2, b: -5, c: 2],
                [a: -10, b: -15, c: -10]
        ]

        a = params.a
        b = params.b
        c = params.c
    }

    @Unroll
    "can I call a self-invoking closure with built params: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
        Math.max(a, b) == c
        where:

        params << ParamUtil.buildParams {
            println "self execution works."
            va { 1 }
            vb { 0 }
            vc { 1 }

            va { 1 }
            vb { 2 }
            vc { 2 }

            va { 4 }
            vb { 5 }
            vc { 5 }
        }

        a = params.va
        b = params.vb
        c = params.vc
    }

    @Unroll
    "can I call a self-invoking closure: max of #a and #b gives #c"(Map params, int a, int b, int c) {
        expect:
        Math.max(a, b) == c
        where:

        params << {
            return [
                [a: 1, b: 0, c: 1],
                [a: 2, b: -5, c: 2],
                [a: -10, b: -15, c: -10]
        ]}()

        a = params.a
        b = params.b
        c = params.c
    }

    // this does not work!!
    @Unroll
    "can I set params inside closure: max of #a and #b gives #c"(Map params, int a, int b, int c) {
//        expect:
//        Math.max(a, b) == c
//        where:
//
//        a = 2
//        b = 0
//        c = 1
//        params << {
//
//            a = 1
//            b = 0
//            c = 1
//
//            return [
//                    [a: 1, b: 0, c: 1],
//                    [a: 2, b: -5, c: 2],
//                    [a: -10, b: -15, c: -10]
//            ]}()
    }


    @Unroll
    "Using object params: max of #params.a and #params.b gives #params.c"() {
        expect:
        Math.max(params.a, params.b) == params.c
        where:
        params = ParamUtil.buildParams {
            println "self execution works."
            return [a: 1, b: 0, c: 1]
        }
    }

    @Unroll
    "Using util params: max of #params.a and #params.b gives #params.c"() {
        expect:
        Math.max(params.a, params.b) == params.c
        where:
        params << ParamUtil.buildParams {
            println "self execution works."
            a { 1 }
            b { 0 }
            c { 1 }

            a { 1 }
            b { 2 }
            c { 2 }

            a { 4 }
            b { 5 }
            c { 5 }
        }
    }

    @Unroll
    "Using util params with type safety: max of #a and #b gives #c"(int a, int b, int c, Object params) {
//        when:
//            println "running type safety experiment..."
//        then:
//            Math.max(a, b) == c
//        where:
//            params = ParamUtil.loadTestCases {
//                println "self execution works."
//
//                va { 1 }
//                vb { 0 }
//                vc { 1 }
//
//                va { 1 }
//                vb { 2 }
//                vc { 2 }
//
//                va { 4 }
//                vb { 5 }
//                vc { 5 }
//
//
////                va = 1
////                vb = 0
////                vc = 1
////
////                va = 1
////                vb = 2
////                vc = 2
////
////                va = 4
////                vb = 5
////                vc = 5
//            }
//            a = {
//                println "params is ${params}"
//                return params.va
//            }()
//            b = params.vb
//            c = params.vc
    }

    @Unroll
    "Using self-executing closure: max of #a and #b gives #c"() {
        expect:
        Math.max(a, b) == c
        where:
        a << {
            println "self execution works."
            return [1 ,2 ,4]
        }.call()
        b << bProvider()
        c << cProvider()
    }
}

class ParamUtil {

    Map<String, Integer> caseIndexMap
    List<Map<String, Object>> spockParamList = new ArrayList<>()

    ParamUtil() {
        spockParamList = new ArrayList<>()
        caseIndexMap = new HashMap<>()
    }

    static buildParams(Closure params) {

        ParamUtil paramUtilDelegate = new ParamUtil()
        params.setDelegate(paramUtilDelegate)
        params()

        return paramUtilDelegate.spockParamList
    }

    def methodMissing(String name, args) {

        println "missing method $name"

        if (args != null && args.length == 1 && args[0] instanceof Closure) {

            Integer caseIndex = caseIndexMap.get(name)

            // does this case index map have the parameter name in it?
            if (caseIndex == null) {
                caseIndexMap.put(name, 0)  // always start cases at 0 to correspond with the list
            }

            caseIndex = caseIndexMap.get(name)

            // do we need to add a new case to accommodate to the current case index?
            if (caseIndex >= spockParamList.size()) {
                spockParamList.add(new HashMap<>())
            }

            Closure param = (Closure) args[0]

            // use whatever caseIndex is already in the map
            spockParamList.get(caseIndex).put(name, param())

            // be sure to update it...
            caseIndexMap.put(name, caseIndex + 1)
        }
    }

    def propertyMissing(String name, args) {
        println "property missing..."
    }
}