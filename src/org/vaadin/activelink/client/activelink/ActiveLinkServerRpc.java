package org.vaadin.activelink.client.activelink;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface ActiveLinkServerRpc extends ServerRpc {

	public void clicked(MouseEventDetails mouseDetails);

}
