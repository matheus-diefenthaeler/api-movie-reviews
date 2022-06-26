package br.com.letscode.matheus.criticasdefilme.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Profile {

    LEITOR("Leitor", 0L),
    BASICO("Basico", 20L),
    AVANCADO("Avancado", 100L),
    MODERADOR("Moderador", 1000L);

    private final String description;
    private Long score;

}
