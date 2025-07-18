package com.github.repos.explorer.service;

import com.github.repos.explorer.model.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;


class GithubServiceTest {
	
	@Test
	void findAllNotForkReposOf() throws IOException, InterruptedException {
		GithubService githubService = new GithubService();

		// Number of not fork public repositories on "bdaf" account: 17
		var repos = githubService.findAllNotForkReposOf("bdaf");
		assertEquals(17, repos.size());
		
		
		// Sorting repos alphabetically
		Collections.sort(repos, Comparator.comparing(Repository::repositoryName));
		
		// Contains information in alphabetically first repo on that account:
		Repository first = repos.getFirst();
		
		assertEquals(first.repositoryName(), "FileSetter");
		assertEquals(first.ownerLogin(), "bdaf");
		assertEquals(first.branches().size(), 1);
		assertEquals(first.branches().getFirst().name(), "main");
		assertEquals(first.branches().getFirst().lastCommitSha(), "fbf29aa7c1030de6452d808f35e45a88ec93dade");
		
	}
}