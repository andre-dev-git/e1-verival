package com.vev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RankingTest {

    private Ranking createRankingWithScoresDesc(int... scores) {
        Ranking ranking = new Ranking();
        for (int score : scores) {
            assertTrue(ranking.add(new Record("P" + score, score)));
        }
        return ranking;
    }

    @Test
    void addDeveInserirQuandoHaMenosDeDezRegistros() {
        Ranking ranking = new Ranking();

        boolean inserted = ranking.add(new Record("Ana", 50));

        assertTrue(inserted);
        assertEquals(1, ranking.numRecords());
        assertEquals(50, ranking.getScore(0).getScore());
    }

    @Test
    void addDeveManterOrdenacaoAoInserirNoTopoEMeioEFim() {
        Ranking ranking = createRankingWithScoresDesc(90, 70, 50);

        assertTrue(ranking.add(new Record("Topo", 100)));
        assertTrue(ranking.add(new Record("Meio", 60)));
        assertTrue(ranking.add(new Record("Fim", 40)));

        assertEquals(6, ranking.numRecords());
        assertEquals(100, ranking.getScore(0).getScore());
        assertEquals(90, ranking.getScore(1).getScore());
        assertEquals(70, ranking.getScore(2).getScore());
        assertEquals(60, ranking.getScore(3).getScore());
        assertEquals(50, ranking.getScore(4).getScore());
        assertEquals(40, ranking.getScore(5).getScore());
    }

    @Test
    void addDeveInserirQuandoRankingCheioENovoMaiorQuePior() {
        Ranking ranking = createRankingWithScoresDesc(100, 90, 80, 70, 60, 50, 40, 30, 20, 10);

        boolean inserted = ranking.add(new Record("Novo", 15));

        assertTrue(inserted);
        assertEquals(10, ranking.numRecords());
        assertEquals(15, ranking.worstScore().getScore());
        assertNull(ranking.getScore(10));
    }

    @Test
    void addNaoDeveInserirQuandoRankingCheioENovoIgualAoPior() {
        Ranking ranking = createRankingWithScoresDesc(100, 90, 80, 70, 60, 50, 40, 30, 20, 10);

        boolean inserted = ranking.add(new Record("Empate", 10));

        assertFalse(inserted);
        assertEquals(10, ranking.numRecords());
        assertEquals(10, ranking.worstScore().getScore());
    }

    @Test
    void addNaoDeveInserirQuandoRankingCheioENovoMenorQuePior() {
        Ranking ranking = createRankingWithScoresDesc(100, 90, 80, 70, 60, 50, 40, 30, 20, 10);

        boolean inserted = ranking.add(new Record("Baixo", 9));

        assertFalse(inserted);
        assertEquals(10, ranking.numRecords());
        assertEquals(10, ranking.worstScore().getScore());
    }

    @Test
    void addDeveLancarExcecaoQuandoRecebeRegistroNulo() {
        Ranking ranking = new Ranking();

        assertThrows(NullPointerException.class, () -> ranking.add(null));
    }

    @Test
    void numRecordsDeveRetornarZeroQuandoVazio() {
        Ranking ranking = new Ranking();

        assertEquals(0, ranking.numRecords());
    }

    @Test
    void numRecordsDeveRefletirInsercoesValidas() {
        Ranking ranking = createRankingWithScoresDesc(30, 20, 10);

        assertEquals(3, ranking.numRecords());
    }

    @Test
    void numRecordsDevePermanecerComDezAposTentativaRejeitadaComRankingCheio() {
        Ranking ranking = createRankingWithScoresDesc(100, 90, 80, 70, 60, 50, 40, 30, 20, 10);

        ranking.add(new Record("Baixo", 9));

        assertEquals(10, ranking.numRecords());
    }

    @Test
    void getScoreDeveRetornarRegistroParaIndiceValido() {
        Ranking ranking = createRankingWithScoresDesc(30, 20, 10);

        Record best = ranking.getScore(0);
        Record last = ranking.getScore(2);

        assertNotNull(best);
        assertNotNull(last);
        assertEquals(30, best.getScore());
        assertEquals(10, last.getScore());
    }

    @Test
    void getScoreDeveRetornarNullParaIndicesInvalidos() {
        Ranking ranking = createRankingWithScoresDesc(30, 20, 10);

        assertNull(ranking.getScore(-1));
        assertNull(ranking.getScore(3));
        assertNull(ranking.getScore(4));
        assertNull(ranking.getScore(Integer.MAX_VALUE));
    }

    @Test
    void bestScoreDeveRetornarMaiorPontuacaoEAposAtualizacao() {
        Ranking ranking = createRankingWithScoresDesc(90, 70, 50);
        assertEquals(90, ranking.bestScore().getScore());

        ranking.add(new Record("NovoMelhor", 95));

        assertEquals(95, ranking.bestScore().getScore());
    }

    @Test
    void bestScoreDeveRetornarNullQuandoRankingVazio() {
        Ranking ranking = new Ranking();

        assertNull(ranking.bestScore());
    }

    @Test
    void worstScoreDeveRetornarMenorPontuacaoEAposSubstituicao() {
        Ranking ranking = createRankingWithScoresDesc(100, 90, 80, 70, 60, 50, 40, 30, 20, 10);
        assertEquals(10, ranking.worstScore().getScore());

        ranking.add(new Record("MelhorQuePior", 15));

        assertEquals(15, ranking.worstScore().getScore());
    }

    @Test
    void worstScoreDeveLancarExcecaoQuandoRankingVazio() {
        Ranking ranking = new Ranking();

        assertThrows(ArrayIndexOutOfBoundsException.class, ranking::worstScore);
    }
}
