package com.github.repos.explorer.DTO;

import com.github.repos.explorer.model.Branch;

import java.util.List;

public record RepositoryDTO(String repositoryName, String ownerLogin, List<Branch> branches, boolean fork) {
}
