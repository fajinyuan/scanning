/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.scanning.test.event.queues.dummy;

import org.eclipse.scanning.api.event.EventException;
import org.eclipse.scanning.api.event.core.IPublisher;
import org.eclipse.scanning.api.event.queues.beans.Queueable;

public class DummyBeanProcess<T extends Queueable> extends DummyProcess<DummyBean, T> {
	
	public static final String BEAN_CLASS_NAME = DummyBean.class.getName();
	
	public DummyBeanProcess(T bean, IPublisher<T> publisher, Boolean blocking) throws EventException {
		super(bean, publisher, blocking);
	}

	@Override
	public Class<DummyBean> getBeanClass() {
		return DummyBean.class;
	}
	
	@Override
	public void postMatchCompleted() throws EventException {
		//Not needed for DummyProcessing
	}

	@Override
	public void postMatchTerminated() throws EventException {
		//Not needed for DummyProcessing
	}

	@Override
	public void postMatchFailed() throws EventException {
		//Not needed for DummyProcessing
	}

}
