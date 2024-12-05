package reflection.uml;

import java.util.*;
import reflection.uml.ReflectionData.*;

public class ProcessClasses {

    List<Link> getSuperclasses(Class<?> c, List<Class<?>> javaClasses) {
        List<Link> links = new ArrayList<>();

        // Check and add the immediate superclass if it's in javaClasses
        Class<?> superclass = c.getSuperclass();
        if (superclass != null && javaClasses.contains(superclass)) {
            links.add(new Link(c.getSimpleName(), superclass.getSimpleName(), LinkType.SUPERCLASS));
        }

        // Check and add all interfaces implemented by this class if they're in javaClasses
        for (Class<?> iface : c.getInterfaces()) {
            if (javaClasses.contains(iface)) {
                links.add(new Link(c.getSimpleName(), iface.getSimpleName(), LinkType.SUPERCLASS));
            }
        }

        return links;
    }

    ClassType getClassType(Class<?> c) {
        if (c.isInterface()) {
            return ClassType.INTERFACE;
        } else if (java.lang.reflect.Modifier.isAbstract(c.getModifiers())) {
            return ClassType.ABSTRACT;
        } else {
            return ClassType.CLASS;
        }
    }

    List<FieldData> getFields(Class<?> c) {
        List<FieldData> fields = new ArrayList<>();
        for (java.lang.reflect.Field f : c.getDeclaredFields()) {
            fields.add(new FieldData(f.getName(), f.getType().getSimpleName()));
        }
        return fields;
    }

    List<MethodData> getMethods(Class<?> c) {
        List<MethodData> methods = new ArrayList<>();
        for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
            String methodName = m.getName();
            String returnType = m.getReturnType().getSimpleName();
            methods.add(new MethodData(methodName, returnType));
        }
        return methods;
    }

    List<Link> getFieldDependencies(Class<?> c, List<Class<?>> javaClasses) {
        List<Link> links = new ArrayList<>();
        for (java.lang.reflect.Field f : c.getDeclaredFields()) {
            Class<?> fieldType = f.getType();
            if (javaClasses.contains(fieldType)) {
                links.add(new Link(c.getSimpleName(), fieldType.getSimpleName(), LinkType.DEPENDENCY));
            }
        }
        return links;
    }

    List<Link> getMethodDependencies(Class<?> c, List<Class<?>> javaClasses) {
        List<Link> links = new ArrayList<>();
        for (java.lang.reflect.Method m : c.getDeclaredMethods()) {
            // Check return type dependency
            Class<?> returnType = m.getReturnType();
            if (javaClasses.contains(returnType)) {
                links.add(new Link(c.getSimpleName(), returnType.getSimpleName(), LinkType.DEPENDENCY));
            }
            // Check parameter dependencies
            for (java.lang.reflect.Parameter p : m.getParameters()) {
                Class<?> paramType = p.getType();
                if (javaClasses.contains(paramType)) {
                    links.add(new Link(c.getSimpleName(), paramType.getSimpleName(), LinkType.DEPENDENCY));
                }
            }
        }
        return links;
    }

    DiagramData process(List<Class<?>> javaClasses) {
        // we're going to process the classes here to build up the class data
        List<ClassData> classData = new ArrayList<>();
        Set<Link> links = new HashSet<>();

        for (Class<?> c : javaClasses) {
            String className = c.getSimpleName();
            ClassType classType = getClassType(c);
            List<FieldData> fields = getFields(c);
            List<MethodData> methods = getMethods(c);
            classData.add(new ClassData(className, classType, fields, methods));
            links.addAll(getSuperclasses(c, javaClasses));
            links.addAll(getFieldDependencies(c, javaClasses));
            links.addAll(getMethodDependencies(c, javaClasses));
        }
        return new DiagramData(classData, links);
    }


    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        // add in all the classes we wish to generate UML for
        classes.add(MyShape.class);
        classes.add(MyCircle.class);
        classes.add(MyCircle.InnerStatic.class);
        classes.add(MyEllipse.class);
        classes.add(Connector.class);
        System.out.println(classes);
        System.out.println();
        DiagramData dd = new ProcessClasses().process(classes);
        System.out.println(dd);
        System.out.println();
        for (ClassData cd : dd.classes()) {
            System.out.println(cd);
        }
        System.out.println();
        for (Link l : dd.links()) {
            System.out.println(l);
        }
    }
}