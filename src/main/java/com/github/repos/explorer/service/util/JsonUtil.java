package com.github.repos.explorer.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.repos.explorer.model.Branch;
import com.github.repos.explorer.DTO.RepositoryDTO;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class JsonUtil {
	
	public static Branch mapToBranch(JsonNode branchNode) {
		String name = branchNode.get("name").textValue();
		String lastCommitSha = branchNode.get("commit").get("sha").textValue();
		return new Branch(name, lastCommitSha);
	}
	
	public static RepositoryDTO mapToRepository(JsonNode repoNode, List<Branch> branches) {
		String repositoryName = repoNode.get("name").textValue();
		String ownerLogin = repoNode.get("owner").get("login").textValue();
		boolean isFork = repoNode.get("fork").booleanValue();
		
		RepositoryDTO repositoryDTO = new RepositoryDTO(repositoryName, ownerLogin, branches, isFork);
		return repositoryDTO;
	}
	
	public static Stream<JsonNode> streamOf(JsonNode node) {
		return node.isArray() ? StreamSupport.stream(node.spliterator(), false) : Stream.of(node);
	}
}
