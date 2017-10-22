package groovy.runtime.metaclass.com.scarlatti;

import groovy.lang.MetaClassImpl;
import groovy.lang.MetaClassRegistry;
import groovy.lang.MetaMethod;
import org.codehaus.groovy.runtime.MetaClassHelper;

/**
 * Created by pc on 10/18/2017.
 */
public class ElementHandlerMetaClassOld extends MetaClassImpl {


    public ElementHandlerMetaClassOld(Class theClass, MetaMethod[] add) {
        super(theClass, add);
    }

    public ElementHandlerMetaClassOld(Class theClass) {
        super(theClass);
    }

    public ElementHandlerMetaClassOld(MetaClassRegistry registry, Class theClass, MetaMethod[] add) {
        super(registry, theClass, add);
    }

    public ElementHandlerMetaClassOld(MetaClassRegistry registry, Class theClass) {
        super(registry, theClass);
    }

    @Override
    public Object invokeMethod(Class sender, Object object, String methodName, Object[] originalArguments, boolean isCallToSuper, boolean fromInsideClass) {
        checkInitalised();
        if (object == null) {
            throw new NullPointerException("Cannot invoke method: " + methodName + " on null object");
        }

        final Object[] arguments = originalArguments == null ? EMPTY_ARGUMENTS : originalArguments;
        MetaMethod method = getMethodWithCaching(sender, methodName, arguments, isCallToSuper);
        MetaClassHelper.unwrap(arguments);

        if (method != null) {
            return method.doMethodInvoke(object, arguments);
        } else {
            return invokeMissingMethod(object, methodName, arguments);
        }
    }
}
