# Lúmen

Protótipo Android em Kotlin e Jetpack Compose para explorar desenho, visão computacional e organização modular.

## Estado do repositório

- A base Android e seus módulos estão versionados.
- O item `lumen-web` aparece no histórico como referência Git, mas o conteúdo web não está incluído neste repositório público.
- O projeto é experimental; não é apresentado como produto em produção nem como modelo próprio de inteligência artificial.

## Stack confirmada no repositório

- Kotlin e Jetpack Compose.
- Estrutura multimódulo Gradle.
- Módulos `domain`, `data`, `vision`, `ui-common` e `feature-drawing`.
- Android SDK e testes por Gradle.

## Estrutura

- `app`: aplicação Android.
- `domain`: modelos e regras.
- `data`: persistência e acesso a dados.
- `vision`: componentes relacionados à visão.
- `feature-drawing`: fluxo de desenho.
- `ui-common`: elementos de interface compartilhados.

## Build

No Windows:

```powershell
.\gradlew.bat :app:assembleDebug
```

Em Linux ou macOS:

```bash
./gradlew :app:assembleDebug
```

## Limites

O README anterior descrevia também uma experiência web com React, MediaPipe, Canvas e Web Audio. Como esse código não está disponível neste repositório público, ele não é tratado aqui como evidência versionada.
