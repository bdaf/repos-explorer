package com.github.repos.explorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Github login not found")
public class GithubLoginNotFoundException extends RuntimeException {
	public GithubLoginNotFoundException(String message) {
		super(message);
	}
}
