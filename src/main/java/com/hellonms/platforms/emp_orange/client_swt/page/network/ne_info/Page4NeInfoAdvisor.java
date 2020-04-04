package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.excel.UtilExcel;

/**
 * <p>
 * Page4NeInfoAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NeInfoAdvisor {

	public EMP_MODEL_NE_INFO[] getListNe_info_def(final EMP_MODEL_NE ne_def) throws EmpException {
		return EMP_MODEL.current().getNe_infos_by_ne(ne_def == null ? 0 : ne_def.getCode());
	}

	public ModelDisplay4NeInfo queryListNeInfo(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def, final boolean isQuery) throws EmpException {
		return (ModelDisplay4NeInfo) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeInfo(request, model4Ne.getNe_id(), ne_info_def.getCode(), isQuery);
			}
		});
	}

	public Model4NeInfoIf newInstanceNeInfo(final EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		return EMP_MODEL.current().newInstanceNe_info(ne_info_def.getCode());
	}

	public ModelDisplay4NeInfo createNeInfo(final Model4NeInfoIf model4NeInfo) throws EmpException {
		// return (ModelDisplay4NeInfo) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
		// @Override
		// public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
		// return invoker.createNeInfo(request, model4NeInfo);
		// }
		// });
		return null;
	}

	public ModelDisplay4NeInfo updateNeInfo(final Model4NeInfoIf model4NeInfo) throws EmpException {
		return (ModelDisplay4NeInfo) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateNeInfo(request, model4NeInfo);
			}
		});
	}

	public ModelDisplay4NeInfo deleteNeInfo(final Model4NeInfoIf model4NeInfo) throws EmpException {
		// return (ModelDisplay4NeInfo) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
		// @Override
		// public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
		// return invoker.deleteNeInfo(request, model4NeInfo);
		// }
		// });
		return null;
	}

	public byte[] saveExcelNeInfo(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		ModelDisplay4NeInfo ne_infos = (ModelDisplay4NeInfo) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeInfo(request, model4Ne.getNe_id(), ne_info_def.getCode(), true);
			}
		});
		DataTableIf data_table = new DataTable4NeInfo(ne_info_def);
		data_table.setDatas((Object) ne_infos.getModel4NeInfos());
		return UtilExcel.saveExcel(data_table, ne_info_def.getName(), 10000);
	}

}
