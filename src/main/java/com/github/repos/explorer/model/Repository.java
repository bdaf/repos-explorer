package com.github.repos.explorer.model;

import com.github.repos.explorer.DTO.RepositoryDTO;

import java.util.List;


public record Repository(String repositoryName, String ownerLogin, List<Branch> branches) {
	
	public static Repository from(RepositoryDTO repositoryDTO) {
		return new Repository(repositoryDTO.repositoryName(), repositoryDTO.ownerLogin(), repositoryDTO.branches());
	}
}
