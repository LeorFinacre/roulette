define(function(require) {
	'use strict';

	var SockJS = require('sockjs-client'); // <1>
	require('stompjs'); // <2>
	
	return {
		register: register
	};
	
	function register(tirages) {
		const socket = SockJS('/tirage'); // <3>
		const stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			tirages.forEach(function (tirage) { // <4>
				stompClient.subscribe(tirage.route, tirage.callback);
			});
		});
	}
});

