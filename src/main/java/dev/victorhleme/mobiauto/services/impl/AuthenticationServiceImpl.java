package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.auth.*;
import dev.victorhleme.mobiauto.entities.PasswordResetToken;
import dev.victorhleme.mobiauto.entities.Usuario;
import dev.victorhleme.mobiauto.exceptions.BadRequestException;
import dev.victorhleme.mobiauto.exceptions.NotFoundException;
import dev.victorhleme.mobiauto.exceptions.UserFirstAccessException;
import dev.victorhleme.mobiauto.repositories.PasswordResetTokenRepository;
import dev.victorhleme.mobiauto.repositories.UsuarioRepository;
import dev.victorhleme.mobiauto.security.Authentication;
import dev.victorhleme.mobiauto.security.UserDetailsImpl;
import dev.victorhleme.mobiauto.services.AuthenticationService;
import dev.victorhleme.mobiauto.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static dev.victorhleme.mobiauto.enums.PasswordResetTokenType.PASSWORD_RECOVERY;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuthenticationServiceImpl implements AuthenticationService {

    @Lazy
    private final AuthenticationManager authenticationManager;
    private final Authentication authentication;
    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AuthTokenService authTokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        Usuario usuario = usuarioRepository.findByEmail(loginDto.getLogin())
            .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        if (usuario.isFirstAccess())
            throw new UserFirstAccessException(loginDto.getLogin());

        var token = new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
        var authentication = authenticationManager.authenticate(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new LoginResponseDto(authTokenService.generateToken(userDetails), usuario.getEmail(), usuario.getNome(), userDetails.authorities());
    }

    @Override
    public void recoverPassword(EmailDto emailDto) {
        log.info("Recovery password: {}", emailDto.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(emailDto.getEmail())
            .orElseThrow(() -> new NotFoundException(Usuario.class, emailDto.getEmail()));

        emailService.sendEmail(usuario, PASSWORD_RECOVERY);
    }

    @Override
    public LoginResponseDto resetPassword(PasswordResetDto passwordResetDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(passwordResetDto.getToken())
            .orElseThrow(() -> new BadRequestException("Invalid token"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expired");

        Usuario usuario = passwordResetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        if (usuario.isFirstAccess())
            usuario.setFirstAccess(false);
        usuarioRepository.save(usuario);

        passwordResetTokenRepository.delete(passwordResetToken);

        var token = new UsernamePasswordAuthenticationToken(
            usuario.getEmail(), passwordResetDto.getNewPassword());
        var authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = authTokenService.generateToken(userDetails);

        return new LoginResponseDto(
            jwtToken, usuario.getEmail(), usuario.getNome(), userDetails.authorities());
    }

    @Override
    public LoginResponseDto changePassword(PasswordChangeDto passwordChangeDto) {
        log.info("Change password for user: {}", passwordChangeDto.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(passwordChangeDto.getEmail())
            .orElseThrow(() -> new NotFoundException(Usuario.class, passwordChangeDto.getEmail()));

        usuario.setSenha(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        usuario.setFirstAccess(false);
        usuarioRepository.save(usuario);

        var token = new UsernamePasswordAuthenticationToken(
            usuario.getEmail(), passwordChangeDto.getNewPassword());
        var authentication = authenticationManager.authenticate(token);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = authTokenService.generateToken(userDetails);

        return new LoginResponseDto(
            jwtToken, usuario.getEmail(), usuario.getNome(), userDetails.authorities());
    }

}
