package com.github.repos.explorer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.repos.explorer.DTO.Repository;
import com.github.repos.explorer.service.util.JsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.github.repos.explorer.service.util.JsonUtil.*;

@Service
public class GithubService {
	private static final String GITHUB_API_URL = "https://api.github.com/";
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final HttpClient httpClient = HttpClient.newHttpClient();

	public List<Repository> findAllReposOf(String githubUsername) throws IOException, InterruptedException {
		JsonNode jsonNode = fetchGetJson(GITHUB_API_URL + "users/" + githubUsername + "/repos");
		
		return streamOf(jsonNode).map(repository -> {
			try {
				return createRepositoryObject(repository, githubUsername);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).toList();
	}
	
	private Repository createRepositoryObject(JsonNode repoNode, String githubUsername)
			throws IOException, InterruptedException {
		
		String repositoryName = repoNode.get("name").asText();
		
		String uriForBranches = GITHUB_API_URL + "repos/" + githubUsername + "/" + repositoryName + "/" + "branches";
		JsonNode arrayNode = fetchGetJson(uriForBranches);
		var branches = streamOf(arrayNode).map(JsonUtil::mapToBranch).toList();
		
		return mapToRepository(repoNode, branches);
	}
	
	private JsonNode fetchGetJson(String uriString) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uriString))
				.header("Accept", "application/json")
				.GET()
				.build();
		
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		return mapper.readTree(response.body());
	}
	
	public List<Repository> findAllNotForkReposOf(String githubUsername) throws IOException, InterruptedException {
		return findAllReposOf(githubUsername).stream().filter(r -> !r.fork()).toList();
	}
}
