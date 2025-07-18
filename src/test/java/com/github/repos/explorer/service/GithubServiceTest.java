package com.github.repos.explorer.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class GithubServiceTest {
	
	@Test
	void findAllNotForkResultReposOf() throws IOException, InterruptedException {
		GithubService githubService = new GithubService();

		// Number of not fork public repositories on "bdaf" account: 17
		assertEquals(17, githubService.findAllNotForkResultReposOf("bdaf").size());
		
		
	}
}