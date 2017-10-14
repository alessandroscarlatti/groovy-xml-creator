package com.scarlatti

/**
 * Created by pc on 10/6/2017.
 */
class XMLCreatorGroovy extends Expando {

    StringBuilder front
    StringBuilder back

    XMLCreatorGroovy self

    XMLCreatorGroovy() {
        front = new StringBuilder()
        back = new StringBuilder()
        self = this
    }

    def invokeMethod(String name, args) {
        this.metaClass."${name}"  <<
                { Closure<?> nestedElementClosure -> element(name, nestedElementClosure)
                } << { -> element(name) }

        if (args != null && args.length == 0) {
            this."${name}"()
        } else {
            this."${name}"((Closure)args[0])
        }
    }

    void simpleElement() {
        front.append("<simpleElement />")       // add the  closed tag
    }

    // only add opening tag for now
    void simpleElement(Closure<?> c) {
        front.append("<simpleElement>")       // add the opening tag
        back.insert(0, "</simpleElement>")      // add the closing tag

        int index = back.length()

        String result = c.call()
        if (result != null) {
            back.insert(back.length() - index, result)
        }
    }

    void element(String name) {
        front.append("<$name />")       // add the  closed tag
    }

    void element(String name, Closure<?> c) {
        front.append("<$name>")       // add the opening tag
        back.insert(0, "</$name>")      // add the closing tag

        int index = back.length()

        String result = c.call()

        // how do I tell if this node has included other nodes or if it is an empty node

        // this is a text node
        if (result != null) {
            back.insert(back.length() - index, result)
        }
    }





    String build() {
        return front + back
    }
}
