package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {}
