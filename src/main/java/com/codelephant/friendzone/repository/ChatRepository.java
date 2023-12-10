package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.receptor = :id")
    List<Chat> findByIDDestinatario(@Param("id") Long id);
}
