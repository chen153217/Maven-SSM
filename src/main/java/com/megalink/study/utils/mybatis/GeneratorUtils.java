package com.megalink.study.utils.mybatis;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
/**
 * Created by thinkpad on 2017-01-15.
 */
public class GeneratorUtils {
    public GeneratorUtils() {
    }

    public static void addPropertyToClass(TopLevelClass cl, FullyQualifiedJavaType type, String name, boolean hasSetter, boolean hasGetter) {
        Field startField = new Field(name, type);
        startField.setVisibility(JavaVisibility.PRIVATE);
        cl.addField(startField);
        Method getter;
        if(hasSetter) {
            getter = new Method("set" + name.substring(0, 1).toUpperCase() + name.substring(1));
            getter.setReturnType((FullyQualifiedJavaType)null);
            getter.setVisibility(JavaVisibility.PUBLIC);
            getter.addParameter(new Parameter(type, name));
            getter.addBodyLine("this." + name + "=" + name + ";");
            cl.addMethod(getter);
        }

        if(hasGetter) {
            getter = new Method("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
            getter.setReturnType(type);
            getter.setVisibility(JavaVisibility.PUBLIC);
            getter.addBodyLine("return " + name + ";");
            cl.addMethod(getter);
        }

    }
}

