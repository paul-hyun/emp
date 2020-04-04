package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_util.excel.UtilExcel;

/**
 * <p>
 * Page4NeStatisticsAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NeStatisticsAdvisor {

	public EMP_MODEL_NE_INFO[] getListInfo_code(final EMP_MODEL_NE ne_def) throws EmpException {
		return EMP_MODEL.current().getNe_statisticss_by_ne(ne_def == null ? 0 : ne_def.getCode());
	}

	public ModelDisplay4NeStatistics queryListNeStatistics(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def, final NE_INFO_INDEX info_index, final STATISTICS_TYPE statistics_type, final Date fromDate, final Date toDate) throws EmpException {
		return (ModelDisplay4NeStatistics) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeStatistics(request, model4Ne.getNe_id(), ne_info_def.getCode(), info_index, statistics_type, fromDate, toDate);
			}
		});
	}

	protected byte[] saveExcelNeStatistics(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def, final NE_INFO_INDEX info_index, final STATISTICS_TYPE statistics_type, final Date fromDate, final Date toDate) throws EmpException {
		ModelDisplay4NeStatistics ne_statistics = (ModelDisplay4NeStatistics) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListNeStatistics(request, model4Ne.getNe_id(), ne_info_def.getCode(), info_index, statistics_type, fromDate, toDate);
			}
		});
		DataTableIf data_table = new DataTable4NeStatistics(ne_info_def);
		data_table.setDatas((Object) ne_statistics);
		return UtilExcel.saveExcel(data_table, ne_info_def.getName(), 10000);
	}

}
