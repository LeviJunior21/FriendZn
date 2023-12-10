package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    @Query("SELECT p FROM Publicacao p INNER JOIN FETCH p.interessados u WHERE u.id = :usuarioId")
    List<Publicacao> findPublicacoesByUsuarioInteressado(@Param("usuarioId") Long usuarioId);
}
