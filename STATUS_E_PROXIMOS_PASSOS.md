# Status e próximos passos — Lúmen

> Auditoria de 22/09/2026. Esta é uma fotografia baseada em arquivos, Git, artefatos e endpoints observáveis. Nenhum build completo foi executado nesta classificação.

## Classificação

- **Estado:** protótipo técnico pausado
- **Confiança:** alta
- **Natureza:** experimento Android multimódulo com uma frente web incompleta

## Evidências observadas

- O repositório Android possui módulos de domínio, dados, visão, UI comum e desenho.
- Não foram encontrados APK/AAB atuais; o último commit é de maio de 2026.
- `lumen-web` existe localmente, mas a relação dele com o repositório principal precisa ser regularizada; há alteração local no README.

## Diagnóstico franco

A arquitetura demonstra capacidade técnica, porém ainda não há uma experiência mínima validada de ponta a ponta.

## Upgrades previstos

### P0 — preservar e tornar retomável

- Preservar a alteração local do README em uma branch.
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

