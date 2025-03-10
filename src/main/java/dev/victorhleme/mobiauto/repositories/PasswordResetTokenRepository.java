package dev.victorhleme.mobiauto.repositories;

import dev.victorhleme.mobiauto.entities.PasswordResetToken;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.enums.PasswordResetTokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    @Query("SELECT t FROM PasswordResetToken t JOIN FETCH t.usuario WHERE t.token = :token")
    Optional<PasswordResetToken> findByToken(@Param("token") String token);

    Optional<PasswordResetToken> findByUsuarioAndType(Usuario usuario, PasswordResetTokenType type);
}
