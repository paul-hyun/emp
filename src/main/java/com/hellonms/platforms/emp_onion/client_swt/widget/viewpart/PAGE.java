package com.hellonms.platforms.emp_onion.client_swt.widget.viewpart;

/**
 * <p>
 * PAGE
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PAGE {

	public final String title;

	public final String pageId;

	public final boolean closable;

	public PAGE(String title, String pageId, boolean closable) {
		this.title = title;
		this.pageId = pageId;
		this.closable = closable;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PAGE) {
			return pageId.equals(((PAGE) obj).pageId);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return pageId.hashCode();
	}

}
