package kz.stegano.med.repository;

import kz.stegano.med.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> getAllByUserId(Long userId);
}
