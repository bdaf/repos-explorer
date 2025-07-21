package com.github.repos.explorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = GithubLoginNotFoundException.REASON)
public class GithubLoginNotFoundException extends RuntimeException {
	static final String REASON = "Github login not found";
	
	public GithubLoginNotFoundException() {
		super(REASON);
	}
}
