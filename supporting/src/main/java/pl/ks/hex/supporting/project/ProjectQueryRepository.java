package pl.ks.hex.supporting.project;

import java.util.Optional;

public interface ProjectQueryRepository {
    Optional<Project> findById(Long id);
}
