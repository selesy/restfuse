package com.eclipsesource.restfuse;


public enum RuleStrategy {
  /**
   * default behaviour:
   * <p>destination rule is executed as junit does</p>
   */
  DEFAULT,
  /**
   * default behaviour:
   * <p>destination rule is wrapped around the actual method invocation.
   * This means that <code>@Before</code> methods are executed before the rest service is called</p>
   */
  HTTP_CALL_AFTER_BEFORE
}
