package com.casino.roulette;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.casino.roulette.entity.Tirage;

@Component
@RepositoryEventHandler(Tirage.class)
public class EventHandler {
	
	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newTirage(Tirage tirage) {
		this.websocket.convertAndSend(
				"/tirage/add", getPath(tirage));
	}

	/**
	 * Take an {@link Tirage} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param tirage
	 */
	private String getPath(Tirage tirage) {
		return this.entityLinks.linkForSingleResource(tirage.getClass(),
				tirage.getId()).toUri().getPath();
	}
}
