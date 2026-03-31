# Relatorio de defeitos - `Ranking`

Execucao realizada com `mvn test` sobre a implementacao atual de `Ranking`.

## Resultado geral
- Total de testes: 15
- Sucesso: 9
- Falhas: 6
- Erros de execucao: 0

## Defeitos encontrados

### DEF-01 - Limite de ranking diferente da especificacao
- **Requisito**: ranking deve armazenar no maximo 10 jogadores.
- **Comportamento esperado**: ao tentar inserir com ranking cheio (10), quantidade deve permanecer 10.
- **Comportamento observado**: quantidade chegou a 11.
- **Evidencia de testes**:
  - `addDeveInserirQuandoRankingCheioENovoMaiorQuePior` (esperado 10, obtido 11)
  - `numRecordsDevePermanecerComDezAposTentativaRejeitadaComRankingCheio` (esperado 10, obtido 11)
- **Causa na implementacao**: constante interna `MAXSCORES` esta definida como 20.
- **Impacto**: viola diretamente a regra de top 10.

### DEF-02 - Regra de entrada com ranking cheio permite empate com pior score
- **Requisito**: novo jogador entra somente se score for maior que o ultimo colocado.
- **Comportamento esperado**: score igual ao pior deve ser rejeitado.
- **Comportamento observado**: score igual foi aceito.
- **Evidencia de teste**:
  - `addNaoDeveInserirQuandoRankingCheioENovoIgualAoPior` (esperado `false`, obtido `true`)
- **Causa na implementacao**: uso de `>=` na verificacao de entrada quando cheio.
- **Impacto**: altera criterio de classificacao definido.

### DEF-03 - Regra de rejeicao para score menor nao se manifesta por causa da capacidade incorreta
- **Requisito**: score menor que o pior deve ser rejeitado quando ranking ja tem 10.
- **Comportamento esperado**: retorno `false` e sem alteracao do ranking.
- **Comportamento observado**: retorno `true`, pois a estrutura ainda nao esta cheia (aceita ate 20).
- **Evidencia de teste**:
  - `addNaoDeveInserirQuandoRankingCheioENovoMenorQuePior` (esperado `false`, obtido `true`)
- **Causa na implementacao**: efeito colateral do defeito DEF-01.
- **Impacto**: impossibilita garantir o filtro correto para entrada no top 10.

### DEF-04 - Atualizacao do pior score apos substituicao nao ocorre no estado esperado de top 10
- **Requisito**: ao entrar novo jogador melhor que o pior em ranking cheio (10), o pior deve ser removido.
- **Comportamento esperado**: pior score deve ser atualizado para o novo limite inferior.
- **Comportamento observado**: pior score permaneceu inalterado no cenario testado.
- **Evidencia de teste**:
  - `worstScoreDeveRetornarMenorPontuacaoEAposSubstituicao` (esperado 15, obtido 10)
- **Causa na implementacao**: como a capacidade esta em 20, a insercao ocorreu sem fase de substituicao.
- **Impacto**: regra de eliminacao do ultimo colocado nao eh exercida no momento correto.

### DEF-05 - Entrada invalida (registro nulo) eh aceita
- **Requisito**: entrada deve representar um jogador valido (nome e pontuacao).
- **Comportamento esperado**: chamada com registro nulo deve ser rejeitada explicitamente.
- **Comportamento observado**: `add(null)` retorna sucesso sem lancar erro.
- **Evidencia de teste**:
  - `addDeveLancarExcecaoQuandoRecebeRegistroNulo` (esperado excecao, obtido nenhuma excecao)
- **Causa na implementacao**: ausencia de validacao de nulo no metodo `add`.
- **Impacto**: permite dados inconsistentes no ranking e pode gerar falhas futuras em pontos de consulta.

## Conclusao
Os defeitos principais estao concentrados em regras de negocio de capacidade e criterio de entrada com ranking cheio. Corrigindo:
- `MAXSCORES` para 10, e
- comparacao de entrada para estritamente maior (`>`),
as falhas associadas ao comportamento de top 10 tendem a ser resolvidas.
