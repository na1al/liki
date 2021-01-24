package ua.catalog.liki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.catalog.liki.entity.Tag;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {

    public Optional<Tag> findOneByAlias(String alias);

    public Set<Tag> findAllByIdIn(int[] id);

}
