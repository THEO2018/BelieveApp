package com.netset.believeapp.Fragment;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;

public abstract class Java7Support
{
    private final static Java7Support IMPL;

    static {
        Java7Support impl = null;
        try {
            Class cls = Class.forName("com.fasterxml.jackson.databind.ext.Java7SupportImpl");
            impl = (Java7Support) cls.newInstance();
        } catch (Throwable t) {
            // 24-Nov-2015, tatu: Should we log or not?
            java.util.logging.Logger.getLogger(Java7Support.class.getName())
                    .warning("Unable to load JDK7 types (annotations, java.nio.file.Path): no Java7 support added");
        }
        IMPL = impl;
    }

    public static Java7Support instance() {
        return IMPL;
    }

    public abstract Boolean findTransient(Annotated a);

    public abstract Boolean hasCreatorAnnotation(Annotated a);

    public abstract PropertyName findConstructorName(AnnotatedParameter p);

    public abstract Class getClassJavaNioFilePath();

    public abstract JsonDeserializer getDeserializerForJavaNioFilePath(Class rawType);

    public abstract JsonSerializer getSerializerForJavaNioFilePath(Class rawType);
}
