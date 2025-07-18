package com.github.repos.explorer.DTO;

import java.util.List;

public record Repository(String repositoryName, String ownerLogin, List<Branch> branches, boolean fork) {
}
