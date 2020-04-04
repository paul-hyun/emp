package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view;

import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Ne;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;

/**
 * <p>
 * Page4NetworkViewAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NetworkViewAdvisor {

	public String[] queryListImagePath(final String path, final String[] extensions) {
		try {
			return (String[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
				@Override
				public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
					return invoker.queryListImagePath(request, path, extensions);
				}
			});
		} catch (EmpException e) {
			return new String[] {};
		}
	}

	public Model4NeGroup createNeGroup(final Model4NeGroup model4NeGroup) throws EmpException {
		return (Model4NeGroup) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.createNeGroup(request, model4NeGroup);
			}
		});
	}

	public Model4NeGroup[] queryListNeGroup() throws EmpException {
		return (Model4NeGroup[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeGroup(request);
			}
		});
	}

	public Model4NeGroup[] queryListNeGroup(final int ne_group_id) throws EmpException {
		return (Model4NeGroup[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeGroup(request, ne_group_id);
			}
		});
	}

	public TreeNode4NeGroup queryTreeNeGroup() throws EmpException {
		return (TreeNode4NeGroup) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryTreeNeGroup(request);
			}
		});
	}

	public Model4NeGroup updateNeGroup(final Model4NeGroup ne_group) throws EmpException {
		return (Model4NeGroup) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateNeGroup(request, ne_group);
			}
		});
	}

	public Model4NeGroup deleteNeGroup(final int neGroupId) throws EmpException {
		return (Model4NeGroup) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.deleteNeGroup(request, neGroupId);
			}
		});
	}

	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter() throws EmpException {
		return (Model4NeSessionDiscoveryFilterIf[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.newInstanceListDiscoveryFilter(request);
			}
		});
	}

	public Model4Ne[] discoveryListNe(final Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException {
		return (Model4Ne[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.discoveryListNe(request, ne_session_discovery_filters);
			}
		});
	}

	public void createListNe(final Model4Ne[] model4Nes) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				invoker.createListNe(request, model4Nes);
				return null;
			}
		});
	}

	public Model4Ne newInstanceNe() throws EmpException {
		return (Model4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.newInstanceNe(request);
			}
		});
	}

	public Model4Ne createNe(final Model4Ne model4Ne) throws EmpException {
		return (Model4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.createNe(request, model4Ne);
			}
		});
	}

	public Model4Ne queryNe(final int ne_id) throws EmpException {
		return (Model4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryNe(request, ne_id);
			}
		});
	}

	public Model4Ne[] queryListNe() throws EmpException {
		return (Model4Ne[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNe(request);
			}
		});
	}

	public Model4Ne[] queryListNe(final int ne_group_id) throws EmpException {
		return (Model4Ne[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNe(request, ne_group_id);
			}
		});
	}

	public Model4Ne updateNe(final Model4Ne ne) throws EmpException {
		return (Model4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateNe(request, ne);
			}
		});
	}

	public Model4Ne deleteNe(final int ne_id) throws EmpException {
		return (Model4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.deleteNe(request, ne_id);
			}
		});
	}

	public void updateMapLocation(final ModelIf[] models) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateMapLocation(request, models);
			}
		});
	}

	public Model4NetworkLink createNetworkLink(final Model4NetworkLink network_link) throws EmpException {
		return (Model4NetworkLink) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.createNetworkLink(request, network_link);
			}
		});
	}

	public Model4NetworkLink deleteNetworkLink(final int network_link_id) throws EmpException {
		return (Model4NetworkLink) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.deleteNetworkLink(request, network_link_id);
			}
		});
	}

	public ModelDisplay4Ne queryNetworkViewNe(final int ne_id, final Map<Integer, Integer> ne_statistics_index_map, final boolean auto_refresh) throws EmpException {
		return (ModelDisplay4Ne) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryNetworkViewNe(request, ne_id, ne_statistics_index_map, auto_refresh);
			}
		});
	}

	public String[] queryOrderNetworkViewNe(final EMP_MODEL_NE ne_def) throws EmpException {
		return (String[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryOrderNetworkViewNe(request, ne_def.getCode());
			}
		});
	}

	public void updateOrderNetworkViewNe(final EMP_MODEL_NE ne_def, final String[] order) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				invoker.updateOrderNetworkViewNe(request, ne_def.getCode(), order);
				return null;
			}
		});
	}

	public Model4ResourceNMS[] queryListResourceNMS(final boolean auto_refresh) throws EmpException {
		return (Model4ResourceNMS[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListResourceNMS(request, auto_refresh);
			}
		});
	}

}
