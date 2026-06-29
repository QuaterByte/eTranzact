package com.example.eTransact.utility;

import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@ValueGenerationType(generatedBy = MySequenceGenerator.class)
public @interface MySequence {
    String name() default "Acc_Sequence";
    int initialValue() default 1000;
    int incrementSize() default 1;
}
