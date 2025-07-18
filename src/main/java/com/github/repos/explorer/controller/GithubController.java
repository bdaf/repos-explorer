package com.github.repos.explorer.controller;

import com.github.repos.explorer.model.Repository;
import com.github.repos.explorer.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/repos")
public class GithubController {
	
	public GithubController(GithubService githubService) {
		this.githubService = githubService;
	}
	
	private final GithubService githubService;
	
	@GetMapping("/{loginName}")
	public ResponseEntity<?> getNotForkReposOf(@PathVariable String loginName)
			throws IOException, InterruptedException {
		List<Repository> repos = githubService.findAllNotForkReposOf(loginName.toLowerCase());
		return ResponseEntity.ok(repos);
	}
	
}
