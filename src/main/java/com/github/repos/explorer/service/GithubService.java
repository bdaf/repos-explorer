package com.github.repos.explorer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.repos.explorer.DTO.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class GithubService {
	private static final String GITHUB_REPOS_API_URL_PREFIX = "https://api.github.com/users/";
	private static final String GITHUB_REPOS_API_URL_SUFFIX = "/repos";
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final HttpClient httpClient = HttpClient.newHttpClient();

	public List<Repository> findAllReposOf(String githubUsername) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(GITHUB_REPOS_API_URL_PREFIX + githubUsername + GITHUB_REPOS_API_URL_SUFFIX))
				.header("Accept", "application/json")
				.GET()
				.build();
		
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		return mapper.readValue(response.body(), new TypeReference<>() {});
	}
	
	public List<Repository> findAllNotForkReposOf(String githubUsername) throws IOException, InterruptedException {
		return findAllReposOf(githubUsername).stream().filter(r -> !r.fork()).toList();
	}
}
