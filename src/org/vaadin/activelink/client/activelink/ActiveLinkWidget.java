package org.vaadin.activelink.client.activelink;

import com.google.gwt.user.client.Window;
import com.vaadin.client.ui.VLink;
import com.vaadin.shared.MouseEventDetails;

public class ActiveLinkWidget extends VLink {

	public void onClick(MouseEventDetails event) {

		if (enabled) {
			if (target == null) {
				target = "_self";
			}
			String features;
			switch (borderStyle) {
			case NONE:
				features = "menubar=no,location=no,status=no";
				break;
			case MINIMAL:
				features = "menubar=yes,location=no,status=no";
				break;
			default:
				features = "";
				break;
			}

			if (targetWidth > 0) {
				features += (features.length() > 0 ? "," : "") + "width="
						+ targetWidth;
			}
			if (targetHeight > 0) {
				features += (features.length() > 0 ? "," : "") + "height="
						+ targetHeight;
			}

			if (!event.isCtrlKey() && !event.isAltKey() && !event.isShiftKey()
					&& !event.isMetaKey()) {
				Window.open(src, target, features);
			}

		}
	}

}