package com.hellonms.platforms.emp_orange.client_swt.util.resource;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.SOUND_ORANGE;

public class UtilResource4Orange {

	public enum SIZE_ORANGE {
		NETWORK_TREE_ICON(24), //
		NETWORK_VIEW_ICON(32), //
		NETWORK_DETAIL_ICON(64), //
		NETWORK_NESESSION_ICON(16), //
		;

		private final int size;

		private SIZE_ORANGE(int size) {
			this.size = size;
		}

		public int size() {
			return size;
		}
	}

	public static Image getNetworkTreeIcon(String path) {
		return getNetworkTreeIcon(path, SEVERITY.CLEAR);
	}

	public static Image getNetworkTreeIcon(String path, SEVERITY severity) {
		return getImage(path, SIZE_ORANGE.NETWORK_TREE_ICON.size(), SIZE_ORANGE.NETWORK_TREE_ICON.size(), severity);
	}

	public static Image getNetworkMapIcon(String path, SEVERITY severity) {
		return getImage(path, SIZE_ORANGE.NETWORK_VIEW_ICON.size(), SIZE_ORANGE.NETWORK_VIEW_ICON.size(), severity);
	}

	public static Image getNetworkDetailIcon(String path, SEVERITY severity) {
		return getImage(path, SIZE_ORANGE.NETWORK_DETAIL_ICON.size(), SIZE_ORANGE.NETWORK_DETAIL_ICON.size(), severity);
	}

	public static Image getRawImage(String path) {
		return getImage(path);
	}

	public static Image getImage(String path, SEVERITY severity) {
		int alpha = 128;

		switch (severity) {
		case COMMUNICATION_FAIL:
			return UtilResource.getImage(path, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CF), alpha + 32);
		case CRITICAL:
			return UtilResource.getImage(path, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CRITICAL), alpha);
		case MAJOR:
			return UtilResource.getImage(path, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MAJOR), alpha);
		case MINOR:
			return UtilResource.getImage(path, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MINOR), alpha);
		case CLEAR:
			return UtilResource.getImage(path);
		case WARNING:
			return UtilResource.getImage(path);
		case INFO:
			return UtilResource.getImage(path);
		default:
			return UtilResource.getImage(path);
		}
	}

	private static Image getImage(final String file_path, int width, int height, SEVERITY severity) {
		if (!UtilResource.containsImage(file_path)) {
			try {
				byte[] image = (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
					@Override
					public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
						return invoker.queryImage(request, file_path);
					}
				});
				UtilResource.putImage(file_path, image);
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int alpha = 128;

		switch (severity) {
		case COMMUNICATION_FAIL:
			return UtilResource.getImage(file_path, width, height, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CF), alpha + 32);
		case CRITICAL:
			return UtilResource.getImage(file_path, width, height, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CRITICAL), alpha);
		case MAJOR:
			return UtilResource.getImage(file_path, width, height, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MAJOR), alpha);
		case MINOR:
			return UtilResource.getImage(file_path, width, height, ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MINOR), alpha);
		case CLEAR:
			return UtilResource.getImage(file_path, width, height);
		case WARNING:
			return UtilResource.getImage(file_path, width, height);
		case INFO:
			return UtilResource.getImage(file_path, width, height);
		default:
			return UtilResource.getImage(file_path, width, height);
		}
	}

	private static Image getImage(final String file_path) {
		if (!UtilResource.containsImage(file_path)) {
			try {
				byte[] image = (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
					@Override
					public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
						return invoker.queryImage(request, file_path);
					}
				});
				UtilResource.putImage(file_path, image);
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return UtilResource.getImage(file_path);
	}

	public static Image getImage(SEVERITY severity, boolean ack_state) {
		switch (severity) {
		case COMMUNICATION_FAIL:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_CF_ACK : IMAGE_ORANGE.FAULT_ALARM_CF);
		case CRITICAL:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_CRITICAL_ACK : IMAGE_ORANGE.FAULT_ALARM_CRITICAL);
		case MAJOR:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_MAJOR_ACK : IMAGE_ORANGE.FAULT_ALARM_MAJOR);
		case MINOR:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_MINOR_ACK : IMAGE_ORANGE.FAULT_ALARM_MINOR);
		case CLEAR:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_CLEAR_ACK : IMAGE_ORANGE.FAULT_ALARM_CLEAR);
		case WARNING:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_INFO_ACK : IMAGE_ORANGE.FAULT_ALARM_INFO);
		case INFO:
			return ThemeFactory.getImage(ack_state ? IMAGE_ORANGE.FAULT_ALARM_INFO_ACK : IMAGE_ORANGE.FAULT_ALARM_INFO);
		default:
			return null;
		}
	}

	public static Image getImage(SEVERITY severity) {
		return getImage(severity, false);
	}

	public static Color getColor(SEVERITY severity) {
		switch (severity) {
		case COMMUNICATION_FAIL:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CF);
		case CRITICAL:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CRITICAL);
		case MAJOR:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MAJOR);
		case MINOR:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MINOR);
		case CLEAR:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CLEAR);
		case WARNING:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_INFO);
		case INFO:
			return ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_INFO);
		default:
			return null;
		}
	}

	public static byte[] getSound(SEVERITY severity) {
		switch (severity) {
		case COMMUNICATION_FAIL:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_CF);
		case CRITICAL:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_CRITICAL);
		case MAJOR:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_MAJOR);
		case MINOR:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_MINOR);
		case CLEAR:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_CLEAR);
		case WARNING:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_WARNING);
		case INFO:
			return ThemeFactory.getSound(SOUND_ORANGE.FAULT_ALARM_INFO);
		default:
			return null;
		}
	}

}
