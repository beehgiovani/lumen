# Status e próximos passos — Lúmen

> Atualizado em 26/09/2026 após remoção de duplicatas e build Android local.

## Classificação

- **Estado:** protótipo técnico pausado
- **Confiança:** alta
- **Natureza:** experimento Android multimódulo com uma frente web incompleta

## Evidências observadas

- O repositório Android possui módulos de domínio, dados, visão, UI comum e desenho.
- Duplicatas de fontes geradas em `domain/bin` foram removidas da árvore rastreada.
- `assembleDebug` passou; um teste do módulo de domínio ainda falha por comportamento preexistente e está registrado como pendência.
- Não há APK/AAB versionado nem evidência de uma experiência completa validada.

## Diagnóstico franco

A arquitetura demonstra capacidade técnica, porém ainda não há uma experiência mínima validada de ponta a ponta.

## Upgrades previstos

### P0 — preservar e tornar retomável

- [x] Consolidar o README e remover fontes duplicadas do versionamento.
- Decidir se a versão canônica será Android ou web; não evoluir as duas ao mesmo tempo.
- Corrigir o versionamento de `lumen-web` como pasta normal, submódulo válido ou repositório separado.

### P1 — estabilizar

- Entregar um fluxo vertical: câmera/entrada, interpretação, traço e salvamento.
- Criar testes para domínio e estabilização de coordenadas.
- Gerar um APK debug reproduzível e registrar requisitos de dispositivo.

### P2 — evoluir

- Medir latência e consumo; adicionar fallback sem câmera.
- Somente depois, retomar áudio reativo ou recursos multimodais.

## Critério para considerar retomado

O projeto será considerado retomado quando uma plataforma escolhida executar um fluxo completo demonstrável, com build, teste e documentação coerentes com o código público.

## Prompt de retomada para o Codex

> Retome o projeto **Lúmen** nesta pasta. Leia este arquivo e o README, inspecione o Git e preserve todo trabalho local. Comece somente pelo P0, valide com evidências e não implemente P1/P2 antes de apresentar o diagnóstico atualizado.

