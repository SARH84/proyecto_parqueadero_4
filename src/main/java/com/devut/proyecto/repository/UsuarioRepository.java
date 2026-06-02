package com.devut.proyecto.repository;

import com.devut.proyecto.entities.Usuario;
import com.devut.proyecto.entities.UsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioId> {

    @Query("SELECT u FROM Usuario u WHERE u.id.login = :login")
    Optional<Usuario> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM Usuario u WHERE u.apikey = :apikey")
    Optional<Usuario> findByApikey(@Param("apikey") String apikey);

    @Query("SELECT u FROM Usuario u WHERE u.persona.id = :idPersona")
    Optional<Usuario> findByPersonaId(@Param("idPersona") Integer idPersona);
}