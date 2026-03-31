# Casos de teste por particionamento - classe `Ranking`

Este documento define os casos de teste para cada metodo publico da classe `Ranking`, com base em particionamento de equivalencia.

## Metodo `add(Record)`

### Particoes
- P1: ranking com menos de 10 registros.
- P2: ranking com 10 registros e novo score maior que o pior.
- P3: ranking com 10 registros e novo score igual ao pior.
- P4: ranking com 10 registros e novo score menor que o pior.
- P5: insercao deve manter ordenacao decrescente.
- P6: valor invalido de entrada (registro nulo).

### Casos
- **ADD-01 (P1)**: inserir em ranking vazio. Esperado: `true`, quantidade passa para 1.
- **ADD-02 (P1)**: inserir varios ate totalizar 10. Esperado: todas insercoes `true`.
- **ADD-03 (P2)**: com 10 registros, inserir score maior que o pior. Esperado: `true`, continua com 10, pior anterior sai.
- **ADD-04 (P3)**: com 10 registros, inserir score igual ao pior. Esperado: `false`, ranking inalterado.
- **ADD-05 (P4)**: com 10 registros, inserir score menor que o pior. Esperado: `false`, ranking inalterado.
- **ADD-06 (P5)**: inserir score que vira melhor colocado. Esperado: novo registro no indice 0.
- **ADD-07 (P5)**: inserir score intermediario. Esperado: registro entra na posicao correta, sem quebrar ordem.
- **ADD-08 (P5)**: inserir score que fica no fim (quando ainda ha menos de 10). Esperado: registro na ultima posicao.
- **ADD-09 (P6)**: chamar `add(null)`. Esperado: lancar excecao por entrada invalida.

## Metodo `numRecords()`

### Particoes
- P1: ranking vazio.
- P2: ranking com insercoes validas.
- P3: ranking cheio com tentativa rejeitada.
- P4: estado invalido (objeto nulo para chamada do metodo).

### Casos
- **NUM-01 (P1)**: ranking recem-criado. Esperado: `0`.
- **NUM-02 (P2)**: apos 3 insercoes validas. Esperado: `3`.
- **NUM-03 (P3)**: apos ranking cheio e insercao rejeitada. Esperado: permanece `10`.
- **NUM-04 (P4)**: chamar `numRecords()` com referencia de ranking nula. Esperado: erro de uso (chamada invalida).

## Metodo `getScore(int)`

### Particoes
- P1: indice valido.
- P2: indice negativo.
- P3: indice igual ao tamanho.
- P4: indice acima do ultimo.
- P5: valor invalido extremo (indice muito alto).

### Casos
- **GET-01 (P1)**: `getScore(0)` com ranking nao vazio. Esperado: melhor registro.
- **GET-02 (P1)**: `getScore(ultimo)` valido. Esperado: pior registro atual.
- **GET-03 (P2)**: `getScore(-1)`. Esperado: `null`.
- **GET-04 (P3)**: `getScore(numRecords())`. Esperado: `null`.
- **GET-05 (P4)**: `getScore(numRecords()+1)`. Esperado: `null`.
- **GET-06 (P5)**: `getScore(Integer.MAX_VALUE)`. Esperado: `null`.

## Metodo `bestScore()`

### Particoes
- P1: ranking nao vazio.
- P2: melhor score muda apos nova insercao.
- P3: estado invalido (ranking vazio).

### Casos
- **BEST-01 (P1)**: apos popular ranking. Esperado: retorna maior score.
- **BEST-02 (P2)**: inserir score maior que o atual melhor. Esperado: novo melhor retornado.
- **BEST-03 (P3)**: chamar `bestScore()` com ranking vazio. Esperado: retorno nulo (sem melhor score disponivel).

## Metodo `worstScore()`

### Particoes
- P1: ranking nao vazio.
- P2: pior score muda apos substituicao em ranking cheio.
- P3: estado invalido (ranking vazio).

### Casos
- **WORST-01 (P1)**: apos popular ranking. Esperado: retorna menor score.
- **WORST-02 (P2)**: com 10 registros, inserir score melhor que o pior e eliminar ultimo. Esperado: pior score atualizado corretamente.
- **WORST-03 (P3)**: chamar `worstScore()` com ranking vazio. Esperado: lancar excecao por estado invalido.
