package com.scarlatti

class XMLCreatorGroovy2 {

    String xml(Closure closure) {

        NodeDelegate nodeDelegate = new NodeDelegate("createXML", new StringBuilder(), 0)
        nodeDelegate.nestNode(closure, nodeDelegate)

        return nodeDelegate.builder.toString()
    }


    static void main(String[] args) {

        println new XMLCreatorGroovy2().xml {
            root {
                nested1 {
                    nested2 {
                        nested3_1 "wert"
                        nested3_2 {
                            if (2 == 2) {
                                println 'found art not'
                                deepNest {
                                    branch doSomethingCoolWithThisText("whammo")
                                    twig doSomethingCoolWithThisText("dunno")
                                }
                            } else {
                                return "art"
                            }
                        }
                        nested3_3()

                        for (int i = 0; i < 5; i++) {
                            nested3_4 {}
                        }
                    }
                }
            }
        }
    }

    static String doSomethingCoolWithThisText(String text) {
        return text.toUpperCase(Locale.CHINA)
    }

}

class NodeDelegate {

    String name
    int indentLevel
    StringBuilder builder
    boolean hasChildren = false

    NodeDelegate(String name, StringBuilder builder, int indentLevel) {
        println "creating node with name: $name"
        this.name = name
        this.builder = builder
        this.indentLevel = indentLevel

        com.scarlatti.NodeDelegate.metaClass.methodMissing = { String methodName, argsv ->
            //            println "method invoked: $methodName with args: $arg"

            hasChildren = true

            if (argsv.length == 0) {
                nestNode(new NodeDelegate(methodName, builder, indentLevel + 1))
            } else if (argsv[0] instanceof String) {
                NodeDelegate delegate = new NodeDelegate(methodName, builder, indentLevel + 1)
                nestNode((String) argsv[0], delegate)

//                if (!delegate.hasChildren) {
//                    indentLevel--
//                }

            } else if (argsv[0] instanceof Closure) {
                NodeDelegate delegate = new NodeDelegate(methodName, builder, indentLevel + 1)
                nestNode((Closure) argsv[0], delegate)

//                if (!delegate.hasChildren) {
//                    indentLevel--
//                }
            } else {
                throw new RuntimeException("Syntax not supported for method ${methodName} with args $argsv")
            }

            // I don't return a value from the method call...
        }

        this.metaClass = NodeDelegate.metaClass

    }

//        def getProperty(String name) {
////            def meta = this.metaClass.getMetaProperty(name)
////            if (meta) {
////                meta.getProperty(this)
////            } else {
////                nestNode(new NodeDelegate(name))
////            }
//
////            if (name == "name") {
////                return this.metaClass.getMetaProperty(name).getProperty(this)
////            } else if (name == "indentLevel") {
////                return indentLevel
////            } else if (name == "builder") {
////                return builder
////            } else {
////                nestNode(new NodeDelegate(name))
////            }
//        }

    def propertyMissing(String name) {
        println "missing: $name"
        def meta = this.metaClass.getMetaProperty(name)
        if (meta) {
            meta.getProperty(this)
        } else {
            hasChildren = true
            nestNode(new NodeDelegate(name, builder, indentLevel + 1))
        }

    }

//    def methodMissing(String name, args) {
//        //            println "method invoked: $methodName with args: $arg"
//
//        hasChildren = true
//
//        if (args.length == 0) {
//            nestNode(new NodeDelegate(name, builder, indentLevel + 1))
//        } else if (args[0] instanceof String) {
//            nestNode((String) args[0], new NodeDelegate(name, builder, indentLevel + 1))
//        } else if (args[0] instanceof Closure) {
//            nestNode((Closure) args[0], new NodeDelegate(name, builder, indentLevel + 1))
//        } else {
//            throw new RuntimeException("Syntax not supported for method ${name} with args $args")
//        }
//
//        // I don't return a value from the method call...
//    }

    void nestNode(NodeDelegate delegate) {
        indent()
        builder.append("<${delegate.name} />\n")
        println "finished running node: ${delegate.name}, no value"
    }

    void nestNode(String val, NodeDelegate delegate) {
        indent()
        builder.append("<${delegate.name}>${val}</${delegate.name}>\n")
        indentLevel--
        println "finished running node: ${delegate.name}, got value $val"
    }

    void nestNode(Closure closure, NodeDelegate delegate) {
        closure.setDelegate(delegate)

        indent()
        builder.append("<${delegate.name}>")
        int potentialNewlineIndex = builder.length()

        String val = closure()
        println "finished running node: ${delegate.name}, got value: $val"


        if (val == null) {
//            // TODO check if has child nodes
            if (delegate.hasChildren) {
                builder.insert(potentialNewlineIndex, '\n')
                indent()
            }

            builder.append("</${delegate.name}>\n")
        } else {
            builder.append("$val</${delegate.name}>\n")
        }
    }

    void indent() {
        for (int i = 0; i < indentLevel; i++) {
            builder.append('\t')
        }
    }

    @Override
    String toString() {
        return "NodeDelegate{" +
                "name='" + name + '\'' +
                '}'
    }
}
