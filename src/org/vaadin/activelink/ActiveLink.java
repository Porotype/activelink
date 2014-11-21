package org.vaadin.activelink;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;

import org.vaadin.activelink.client.activelink.ActiveLinkServerRpc;
import org.vaadin.activelink.client.activelink.ActiveLinkState;

import com.vaadin.server.Resource;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.Component;

@SuppressWarnings("serial")
public class ActiveLink extends com.vaadin.ui.Link {

	static {
		try {
			LINK_FOLLOWED_METHOD = LinkActivatedListener.class
					.getDeclaredMethod("linkActivated",
							new Class[] { LinkActivatedEvent.class });
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			throw new java.lang.RuntimeException(
					"Internal error finding methods in ActiveLink");
		}
	}

	private ActiveLinkServerRpc rpc = new ActiveLinkServerRpc() {
		public void clicked(MouseEventDetails mouseDetails) {
			fireEvent(new LinkActivatedEvent(ActiveLink.this, mouseDetails));
		}
	};

	private static final Method LINK_FOLLOWED_METHOD;

	private HashSet<LinkActivatedListener> listeners = new HashSet<LinkActivatedListener>();

	public ActiveLink() {
		registerRpc(rpc);
	}

	public ActiveLink(String caption, Resource resource, String targetName,
			int width, int height, BorderStyle border) {
		super(caption, resource, targetName, width, height, border);
		registerRpc(rpc);
	}

	public ActiveLink(String caption, Resource resource) {
		super(caption, resource);
		registerRpc(rpc);
	}

	@Override
	public ActiveLinkState getState() {
		return (ActiveLinkState) super.getState();
	}

	/**
	 * Adds the link activated listener.
	 * 
	 * @param listener
	 *            the Listener to be added.
	 */
	public void addListener(LinkActivatedListener listener) {
		listeners.add(listener);
		addListener(LinkActivatedEvent.class, listener, LINK_FOLLOWED_METHOD);
		getState().on = !listeners.isEmpty();
	}

	/**
	 * Removes the link activated listener.
	 * 
	 * @param listener
	 *            the Listener to be removed.
	 */
	public void removeListener(LinkActivatedListener listener) {
		listeners.remove(listener);
		removeListener(LinkActivatedEvent.class, listener, LINK_FOLLOWED_METHOD);
		getState().on = !listeners.isEmpty();
	}

	public class LinkActivatedEvent extends Component.Event {

		private MouseEventDetails details;

		/**
		 * New instance of text change event.
		 * 
		 * @param source
		 *            the Source of the event.
		 */
		public LinkActivatedEvent(Component source, MouseEventDetails details) {
			super(source);
			this.details = details;
		}

		/**
		 * Gets the ActiveLink where the event occurred.
		 * 
		 * @return the Source of the event.
		 */
		public ActiveLink getActiveLink() {
			return (ActiveLink) getSource();
		}

		public MouseEventDetails getMouseEventDetails() {
			return details;
		}
	}

	/**
	 * ActiveLink click listener
	 */
	public interface LinkActivatedListener {

		/**
		 * ActiveLink has been activated.
		 * 
		 * @param event
		 *            ActiveLink click event.
		 */
		public void linkActivated(LinkActivatedEvent event);

	}

}
