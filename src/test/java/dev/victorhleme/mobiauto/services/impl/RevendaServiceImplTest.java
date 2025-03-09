package dev.victorhleme.mobiauto.services.impl;

import dev.victorhleme.mobiauto.dtos.revenda.RevendaCreationDto;
import dev.victorhleme.mobiauto.dtos.revenda.RevendaDto;
import dev.victorhleme.mobiauto.repositories.RevendaRepository;
import dev.victorhleme.mobiauto.utils.IntegrationTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;


class RevendaServiceImplTest extends IntegrationTest {

    private final RevendaServiceImpl revendaService;
    private final RevendaRepository revendaRepository;

    @Autowired
    RevendaServiceImplTest(
        RevendaServiceImpl revendaService,
        RevendaRepository revendaRepository
    ) {
        this.revendaService = revendaService;
        this.revendaRepository = revendaRepository;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("saveRevenda")
    @DisplayName("Should correctly save Revenda or throw error with:")
    void shouldCorrectlySaveRevenda(
        String description,
        String cnpj,
        String nomeSocial,
        Class<Exception> expectedException
    ) {
        RevendaCreationDto dto = new RevendaCreationDto().withCnpj(cnpj).withNomeSocial(nomeSocial);

        if (expectedException == null) {
            revendaService.save(dto);
            Assertions.assertTrue(revendaRepository.findAll().stream().anyMatch(revenda -> revenda.getCnpj().equals(cnpj)));
        } else {
            Assertions.assertThrows(expectedException, () -> revendaService.save(dto));
        }

    }

    public static Stream<Arguments> saveRevenda() {
        return Stream.of(
            Arguments.of(
                "valid CNPJ and Nome Social",
                "00000000000000",
                "Algum Nome",
                null
            ),
            Arguments.of(
                "invalid CNPJ",
                "0000000000000",
                "Algum Nome",
                ConstraintViolationException.class
            ),
            Arguments.of(
                "blank Nome Social",
                "00000000000000",
                "",
                ConstraintViolationException.class
            )
        );
    }

}