package com.github.repos.explorer.controller;

import com.github.repos.explorer.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/repos")
public class GithubController {
	
	public GithubController(GithubService githubService) {
		this.githubService = githubService;
	}
	
	private final GithubService githubService;
	
	@GetMapping("/{loginName}")
	public ResponseEntity<?> getNotForkReposOf(
			@PathVariable String loginName,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token)
			throws IOException, InterruptedException {
		
		var repos = githubService.findAllNotForkReposOf(loginName.toLowerCase(), token);
		return ResponseEntity.ok(repos);
	}
}
