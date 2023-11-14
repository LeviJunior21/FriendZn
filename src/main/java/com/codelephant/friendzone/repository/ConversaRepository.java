package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    @Query("SELECT c FROM Conversa c WHERE c.idUsuario1 = :idUsuario1 ORDER BY c.idUsuario1 ASC")
    List<Conversa> findByUserio1Id(Long idUsuario1);
}
