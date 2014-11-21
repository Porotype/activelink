package org.vaadin.activelink.client.activelink;

import org.vaadin.activelink.ActiveLink;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.link.LinkConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;

@Connect(ActiveLink.class)
public class ActiveLinkConnector extends LinkConnector implements ClickHandler {

	ActiveLinkServerRpc rpc = RpcProxy.create(ActiveLinkServerRpc.class, this);

	protected HandlerRegistration handlerReg = null;

	@Override
	public ActiveLinkWidget getWidget() {
		return (ActiveLinkWidget) super.getWidget();
	}

	@Override
	public ActiveLinkState getState() {
		return (ActiveLinkState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		if (getState().on) {
			if (handlerReg == null) {
				handlerReg = getWidget().addClickHandler(this);
			}
		} else if (handlerReg != null) {
			handlerReg.removeHandler();
			handlerReg = null;
		}

	}

	public void onClick(final ClickEvent event) {

		final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
				.buildMouseEventDetails(event.getNativeEvent(), getWidget()
						.getElement());
		rpc.clicked(mouseDetails);

		if (!mouseDetails.isCtrlKey() && !mouseDetails.isAltKey()
				&& !mouseDetails.isShiftKey() && !mouseDetails.isMetaKey()) {
			// RPC gets lost if we don't delay
			event.preventDefault();
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				public void execute() {
					getWidget().onClick(mouseDetails);
				}
			});
		}
	}

}
