package com.github.repos.explorer.service;

import com.github.repos.explorer.DTO.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GithubServiceTest {
	
	@Test
	void findAllNotForkReposOf() throws IOException, InterruptedException {
		GithubService githubService = new GithubService();
		
		// Number of public repositories on "bdaf" account: 18
		assertEquals(18, githubService.findAllReposOf("bdaf").size());
		
		// Number of not fork public repositories on "bdaf" account: 17
		assertEquals(17, githubService.findAllNotForkReposOf("bdaf").size());
	}
}