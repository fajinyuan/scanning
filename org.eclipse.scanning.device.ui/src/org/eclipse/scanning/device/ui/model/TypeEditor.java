package org.eclipse.scanning.device.ui.model;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import org.eclipse.richbeans.api.binding.IBeanController;
import org.eclipse.richbeans.api.event.ValueAdapter;
import org.eclipse.richbeans.api.event.ValueEvent;
import org.eclipse.richbeans.binding.BeanService;
import org.eclipse.richbeans.widgets.internal.GridUtils;
import org.eclipse.scanning.api.annotation.scan.AnnotationManager;
import org.eclipse.scanning.api.annotation.ui.TypeDescriptor;
import org.eclipse.scanning.api.device.IRunnableDeviceService;
import org.eclipse.scanning.api.device.IScannableDeviceService;
import org.eclipse.scanning.device.ui.Activator;
import org.eclipse.scanning.device.ui.ServiceHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.services.IDisposable;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class TypeEditor extends Composite {
	
	private static final Logger logger = LoggerFactory.getLogger(TypeEditor.class);

	private IBeanController controller;
	
	public TypeEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		GridUtils.removeMargins(this);
	}
	
	public void setModel(Object model) throws Exception {
		
		deactivate();
	
		Object ui = createUserInterface(model);
		controller = BeanService.getInstance().createController(ui, model);
		controller.beanToUI();
		controller.switchState(true);
		controller.addValueListener(new ValueAdapter("ui change") {		
			@Override
			public void valueChangePerformed(ValueEvent e) {
				// Save the values
				try {
					controller.uiToBean();
				} catch (Exception e1) {
					logger.error("Problem recording bean data!", e1);
				}
			}
		});
	}
	
	public Object getModel() throws Exception {
		
		controller.uiToBean();
        return controller.getBean();
	}
	
	private Object createUserInterface(Object model) throws Exception {
		
		Class<?> uiClass = getModelEditorClass(model);
		
		Object ret;
		try {
			Constructor<?> constructor = uiClass.getConstructor(Composite.class, int.class);
			ret = constructor.newInstance(this, SWT.NONE);
			if (ret instanceof Composite) {
				Composite comp = (Composite)ret;
				comp.setLayoutData(new GridData(GridData.FILL_BOTH));
				layout(new Control[]{comp});
			}
		} catch (NoSuchMethodException ne) {
			Constructor<?> constructor = uiClass.getConstructor();
			ret = constructor.newInstance();
		}
		
		AnnotationManager manager = new AnnotationManager(Activator.getDefault(), Inject.class);
		manager.addContext((IScannableDeviceService)ServiceHolder.getRemote(IScannableDeviceService.class));
		manager.addContext((IRunnableDeviceService)ServiceHolder.getRemote(IRunnableDeviceService.class));
		manager.addContext(model);
		manager.addDevices(ret);	
		manager.invoke(Inject.class);
		
		return ret;
	}

	private Class<?> getModelEditorClass(Object model) throws ClassNotFoundException {
		
		TypeDescriptor des = model.getClass().getAnnotation(TypeDescriptor.class);
		final String edClass  = des.editor();
		final String edBundle = des.bundle();
		
		Bundle bundle = edBundle!=null&&edBundle.length()>0 ? getBundle(edBundle) : null;
		
		try {
			return bundle != null ? bundle.loadClass(edClass) : Class.forName(edClass);
		} catch (java.lang.ClassNotFoundException ne) {
			throw new ClassNotFoundException("Cannot find class "+edClass+" in bundle "+edBundle+". Bundle is "+bundle, ne);
		}
	}

	private Bundle getBundle(String bundleName) {
		
		BundleContext bcontext = ServiceHolder.getContext();
		if (bcontext==null)    return null;
		if (bundleName==null) return null;
		Bundle[] bundles = bcontext.getBundles();
		for (Bundle bundle : bundles) {
			if (bundleName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}
		return getOSGiBundle(bundleName, bcontext);
	}

	private Bundle getOSGiBundle(String symbolicName, BundleContext bcontext) {
		
		ServiceReference<PackageAdmin> ref = bcontext.getServiceReference(PackageAdmin.class);
		PackageAdmin packageAdmin = bcontext.getService(ref);
		if (packageAdmin == null)
			return null;
		Bundle[] bundles = packageAdmin.getBundles(symbolicName, null);
		if (bundles == null)
			return null;
		//Return the first bundle that is not installed or uninstalled
		for (int i = 0; i < bundles.length; i++) {
			if ((bundles[i].getState() & (Bundle.INSTALLED | Bundle.UNINSTALLED)) == 0) {
				return bundles[i];
			}
		}
		return null;
	}

	public void deactivate() throws Exception {
		
		if (controller==null) return;
		
		Object ui = controller.getUI();
		if (ui instanceof Composite) {
			Composite composite = (Composite)ui;
			GridUtils.setVisible(composite, false);
			composite.dispose();
		}
		if (ui instanceof IDisposable) {
			((IDisposable)ui).dispose();
		}
		controller.dispose();
	}

}
