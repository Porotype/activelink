package org.vaadin.activelink;

import javax.servlet.annotation.WebServlet;

import org.vaadin.activelink.ActiveLink.LinkActivatedEvent;
import org.vaadin.activelink.ActiveLink.LinkActivatedListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("activelink")
public class DemoUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.activelink.ActivelinkWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		ActiveLink link = new ActiveLink("Click Me", new ExternalResource(
				"http://blog.porotype.com"));
		link.addListener(new LinkActivatedListener() {

			@Override
			public void linkActivated(LinkActivatedEvent event) {
				MouseEventDetails d = event.getMouseEventDetails();
				String caption = "Link " + d.getButtonName() + "-clicked";
				String msg = "Ctrl: " + d.isCtrlKey() + " Shift: "
						+ d.isShiftKey() + " Alt: " + d.isAltKey() + " Meta: "
						+ d.isMetaKey();
				Notification.show(caption, msg, Type.WARNING_MESSAGE);
				System.err.println(caption + " " + msg);
			}
		});
		layout.addComponent(link);
	}

}