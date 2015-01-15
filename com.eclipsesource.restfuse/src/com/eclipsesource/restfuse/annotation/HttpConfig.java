package com.eclipsesource.restfuse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eclipsesource.restfuse.RuleStrategy;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
public @interface HttpConfig {
  RuleStrategy ruleStrategy() default RuleStrategy.DEFAULT;
}
