package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    @Query("SELECT c FROM Conversa c WHERE c.receptor = :receptor ORDER BY c.receptor ASC")
    List<Conversa> findByReceptor(@Param("receptor") Long receptor);
}
