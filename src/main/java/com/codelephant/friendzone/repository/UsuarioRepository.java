package com.codelephant.friendzone.repository;

import com.codelephant.friendzone.model.LoginType;
import com.codelephant.friendzone.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.idAuth = :idAuth AND u.loginType = :loginType")
    Optional<Usuario> findByIdAuth(@Param("idAuth") Long idAuth, @Param("loginType") LoginType loginType);
}
