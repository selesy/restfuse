/*******************************************************************************
 * Copyright (c) 2013 Andreas Mihm.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andreas Mihm - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.restfuse.example.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.eclipsesource.restfuse.HttpJUnitRunner;

/**
 * this class makes sure, that all methods of a test class are run in alphabetical order
 * @author mihm
 *
 */
public class OrderedHttpJUnitRunner extends HttpJUnitRunner {

	public OrderedHttpJUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> list = super.computeTestMethods();
		List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(list);
		Collections.sort(copy, new Comparator<FrameworkMethod>() {
			public int compare(FrameworkMethod o1, FrameworkMethod o2) {
				return ((FrameworkMethod) o1).getName().compareTo(((FrameworkMethod) o2).getName());
			}
		});
		return copy;
	}
}
