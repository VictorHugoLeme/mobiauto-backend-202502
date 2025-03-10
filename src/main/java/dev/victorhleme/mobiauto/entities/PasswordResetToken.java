package dev.victorhleme.mobiauto.entities;


import dev.victorhleme.mobiauto.enums.PasswordResetTokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    private PasswordResetTokenType type;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    public PasswordResetToken(Usuario usuario, String token, PasswordResetTokenType type, LocalDateTime expiryDate) {
        this.usuario = usuario;
        this.token = token;
        this.type = type;
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken refreshToken(String token, LocalDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
        return this;
    }
}
