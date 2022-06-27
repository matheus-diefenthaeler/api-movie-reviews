package br.com.letscode.matheus.criticasdefilme.repositories;

import br.com.letscode.matheus.criticasdefilme.entities.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
}
