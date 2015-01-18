/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Holger Staudacher - initial API and
 * implementation
 ******************************************************************************/
package com.eclipsesource.restfuse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.eclipsesource.restfuse.annotation.HttpConfig;
import com.eclipsesource.restfuse.annotation.HttpTest;

/**
 * <p>
 * The <code>HttpJUnitRunner</code> can be used in your TestCase to avoid the double annotation of
 * test methods with the <code>Test</code> and </code>{@link HttpTest}</code> annotation. The runner
 * detects all <code>{@link HttpTest}</code> annotated methods and executes them as normal JUnit
 * test methods.
 * </p>
 */
public class HttpJUnitRunner extends BlockJUnit4ClassRunner {

  public HttpJUnitRunner( Class<?> klass ) throws InitializationError {
    super( klass );
  }

  RuleStrategy getRuleStrategy( Object test ) {
    HttpConfig annotation = test.getClass().getAnnotation( HttpConfig.class );
    if( annotation == null ) {
      return RuleStrategy.DEFAULT;
    }
    return annotation.ruleStrategy();
  }

  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
    result.addAll( getTestClass().getAnnotatedMethods( HttpTest.class ) );
    List<FrameworkMethod> testAnnotatedMethods = getTestClass().getAnnotatedMethods( Test.class );
    for( FrameworkMethod method : testAnnotatedMethods ) {
      if( !result.contains( method ) ) {
        result.add( method );
      }
    }
    Collections.sort( result, new HttpOrderComparator() );
    return result;
  }

  @Override
  protected Statement methodInvoker( FrameworkMethod method, Object test ) {
    if( getRuleStrategy( test ) == RuleStrategy.HTTP_CALL_AFTER_BEFORE ) {
      Statement methodInvoker = super.methodInvoker( method, test );
      return withDestinationRules( method, test, methodInvoker );
    }
    return super.methodInvoker( method, test );
  }

  @Override
  protected List<TestRule> getTestRules( Object target ) {
    if( getRuleStrategy( target ) == RuleStrategy.HTTP_CALL_AFTER_BEFORE ) {
      List<TestRule> testRules = super.getTestRules( target );
      List<TestRule> nonDestinationRules = new ArrayList<TestRule>( testRules );
      for( TestRule t : testRules ) {
        if( t instanceof Destination ) {
          nonDestinationRules.remove( t );
        }
      }
      return nonDestinationRules;
    }
    return super.getTestRules( target );
  }

  protected List<TestRule> getDestinationRules( Object target ) {
    List<TestRule> testRules = super.getTestRules( target );
    List<TestRule> destinationRules = new ArrayList<TestRule>();
    for( TestRule t : testRules ) {
      if( t instanceof Destination ) {
        destinationRules.add( t );
      }
    }
    return destinationRules;
  }

  private Statement withDestinationRules( FrameworkMethod method, Object target, Statement statement )
  {
    return new RunRules( statement, getDestinationRules( target ), describeChild( method ) );
  }
}
