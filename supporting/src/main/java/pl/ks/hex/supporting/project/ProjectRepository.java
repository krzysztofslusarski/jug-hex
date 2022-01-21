package pl.ks.hex.supporting.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "projects")
interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQueryRepository {
}
