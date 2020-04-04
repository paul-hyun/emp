package com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Page4NetworkViewAdvisor;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

/**
 * <p>
 * Page4NetworkTreeAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NetworkTreeAdvisor extends Page4NetworkViewAdvisor {

	public ModelDisplay4Network queryNetwork(final long sequenceNetwork, final long sequenceFault) throws EmpException {
		return (ModelDisplay4Network) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryNetwork(request, sequenceNetwork, sequenceFault);
			}
		});
	}

}
