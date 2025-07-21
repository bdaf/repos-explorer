package com.github.repos.explorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = GithubBadCredentialsException.REASON)
public class GithubBadCredentialsException extends RuntimeException {
	final static String REASON = "Bad credentials / token";
	
	public GithubBadCredentialsException() {
		super(REASON);
	}
}
