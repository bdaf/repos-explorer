package com.github.repos.explorer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.repos.explorer.DTO.RepositoryDTO;
import com.github.repos.explorer.exception.GithubBadCredentialsException;
import com.github.repos.explorer.exception.GithubLoginNotFoundException;
import com.github.repos.explorer.model.Repository;
import com.github.repos.explorer.service.util.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.repos.explorer.service.util.JsonUtil.mapToRepository;
import static com.github.repos.explorer.service.util.JsonUtil.streamOf;

@Service
public class GithubService {
	private static final String GITHUB_API_URL = "https://api.github.com/";
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final HttpClient httpClient = HttpClient.newHttpClient();
	
	public List<Repository> findAllNotForkReposOf(String githubUsername) throws IOException, InterruptedException {
		return findAllNotForkReposOf(githubUsername, "");
	}
	
	public List<Repository> findAllNotForkReposOf(String githubUsername, String token) throws IOException, InterruptedException {
		token = Objects.requireNonNullElse(token, "");
		return new ArrayList<>(findAllDTOReposOf(githubUsername, token).stream()
				.filter(r -> !r.fork())
				.map(Repository::from)
				.toList());
	}
	
	private List<RepositoryDTO> findAllDTOReposOf(String githubUsername, String token) throws IOException, InterruptedException {
		HttpResponse<String> response = fetchGetJson(GITHUB_API_URL + "users/" + githubUsername + "/repos", token);
		
		if (response.statusCode() == 404)
			throw new GithubLoginNotFoundException();
		
		var jsonNode = mapper.readTree(response.body());
		
		return streamOf(jsonNode).map(repository -> {
			try {
				return createRepositoryObject(repository, githubUsername, token);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).toList();
	}
	
	private RepositoryDTO createRepositoryObject(JsonNode repoNode, String githubUsername, String token)
			throws IOException, InterruptedException {
		
		String repositoryName = repoNode.get("name").asText();
		
		String uriForBranches = GITHUB_API_URL + "repos/" + githubUsername + "/" + repositoryName + "/" + "branches";
		
		HttpResponse<String> response = fetchGetJson(uriForBranches, token);
		
		var arrayNode =  mapper.readTree(response.body());
		
		var branches = streamOf(arrayNode).map(JsonUtil::mapToBranch).toList();
		
		return mapToRepository(repoNode, branches);
	}
	
	private HttpResponse<String> fetchGetJson(String uriString, String token) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uriString))
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", token)
				.GET()
				.build();
		
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		if (response.statusCode() == 401)
			throw new GithubBadCredentialsException();
		
		return response;
	}
}
