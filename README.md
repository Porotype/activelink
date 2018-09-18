[![Published on Vaadin  Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/activelink)
[![Stars on Vaadin Directory](https://img.shields.io/vaadin-directory/star/activelink.svg)](https://vaadin.com/directory/component/activelink)

ActiveLink for Vaadin
==========

An extended link component for the Vaadin Framework ( https://vaadin.com )

ActiveLink is a Link component that sends an event to the server when clicked, before navigating to the linked URL. 
This allows an application to allow a user to open stuff in new windows/tabs while still doing the ajaxy-thing when when the user does not. "Best of both worlds." 

It also sends some additional information about which modifiers where held (e.g shift) and which mouse button was clicked. 
(Plain-old left-click is reliable, but some other situations are more difficult depending on the browser; verify in the relevant browsers if it covers your needs.) 

If no listeners are attached, it will behave like a regular Link.

Thanks to Sami Viitanen for fixing 7.3 compatibility.
