# Lúmen

Protótipo Android em Kotlin e Jetpack Compose para explorar desenho por gestos, visão computacional no dispositivo e organização modular.

> **Estado atual:** experimento técnico pausado. A base Android está versionada e contém os componentes de câmera, rastreamento de mãos, desenho e exportação, mas ainda não há uma experiência de ponta a ponta homologada nem uma release pública reproduzível documentada.

O projeto não é apresentado como produto em produção, ferramenta de acessibilidade validada ou modelo próprio de inteligência artificial.

## Escopo

- captura de câmera com CameraX;
- rastreamento de mãos com MediaPipe Tasks Vision;
- transformação de landmarks em pontos do domínio;
- suavização do traço e estado de desenho;
- interface Compose para onboarding, câmera, overlay e canvas;
- exportação local de desenho;
- separação entre domínio, dados, visão e interface.

## Situação por frente

| Frente | Situação observável |
| --- | --- |
| Base Android multimódulo | Versionada |
| Modelo MediaPipe | Incluído como asset local |
| Testes de domínio | O teste existente não compila no estado atual |
| APK debug | Compilado com sucesso em 26/09/2026 |
| Build de release | Precisa ser revalidado e documentado |
| Experiência web | Não está disponível neste checkout |
| Homologação em dispositivos | Não documentada |

## Arquitetura

```text
app                 composição da aplicação e permissões Android
  │
  └── feature-drawing
        ├── domain        modelos, contratos e casos de uso
        ├── vision        CameraX, MediaPipe e mapeamento de landmarks
        ├── data          implementação do repositório de desenhos
        └── ui-common     tema e componentes visuais compartilhados
```

### Módulos

- `app`: Activity, permissão de câmera e montagem inicial das dependências;
- `domain`: modelos de desenho, suavização, casos de uso e contratos;
- `vision`: câmera, `HandLandmarker` e processamento de gestos;
- `data`: persistência/acesso a desenhos;
- `feature-drawing`: ViewModel, canvas, overlay, onboarding e exportação;
- `ui-common`: tema Compose compartilhado.

A injeção ainda é manual em `MainActivity`; isso é adequado ao protótipo, mas limita testes e evolução do grafo de dependências.

## Stack

- Kotlin e Gradle Kotlin DSL;
- Jetpack Compose e Material 3;
- CameraX;
- MediaPipe Tasks Vision;
- Coroutines;
- JUnit é a escolha prevista para os testes de domínio, mas a dependência ainda não está declarada no módulo.

## Requisitos

- Android Studio compatível com Android Gradle Plugin 9.0;
- JDK 21;
- Android SDK 36;
- Android 7.0 (API 24) ou superior;
- emulador `x86_64` ou aparelho `arm64-v8a` com câmera para o fluxo completo.

O modelo `app/src/main/assets/hand_landmarker.task` é executado localmente. O projeto Android atual não depende de uma API externa nem exige credenciais.

## Build e testes

No Windows:

```powershell
.\gradlew.bat :domain:test
.\gradlew.bat :app:lintDebug
.\gradlew.bat :app:assembleDebug
```

Em Linux ou macOS:

```bash
./gradlew :domain:test
./gradlew :app:lintDebug
./gradlew :app:assembleDebug
```

O APK debug, quando o build passa, é gerado em `app/build/outputs/apk/debug/`. Essa pasta é local e não deve ser versionada.

Na auditoria de 26/09/2026, `:app:assembleDebug` passou. Já `:domain:test` falhou na compilação: o módulo não declara a dependência JUnit e `PointSmootherTest` ainda usa o parâmetro nomeado antigo `factor`. Portanto, o comando de teste acima documenta a verificação necessária, não um teste atualmente verde. O lint não foi executado nesta auditoria.

## Configuração segura

- mantenha `local.properties`, keystores, arquivos `.env` e diretórios de build fora do Git;
- não substitua o asset do modelo por um arquivo sem registrar origem, versão e licença;
- não grave imagens da câmera, landmarks ou desenhos do usuário sem consentimento e finalidade clara;
- revise permissões e política de privacidade antes de distribuir qualquer build.

## Estado da referência web

O caminho `lumen-web` está registrado no Git como uma referência a outro commit, mas este repositório não possui `.gitmodules` nem inclui o conteúdo correspondente. Portanto, a experiência web descrita em documentos antigos — React, MediaPipe, Canvas e Web Audio — não é tratada aqui como evidência reproduzível.

Antes de retomar a frente web, é necessário escolher uma única estratégia: pasta versionada normalmente, submódulo válido com origem declarada ou repositório separado. Até essa decisão, o Android é a única base documentada por este README.

## Limites conhecidos

- O tratamento de negação da permissão de câmera ainda é incompleto.
- Há apenas um teste unitário no módulo de domínio, e ele não compila até a dependência JUnit e a chamada de `PointSmoother` serem atualizadas.
- O build debug atual emite avisos de recursos Gradle obsoletos, incompatíveis com o futuro Gradle 10, e de configuração legada de empacotamento nativo; a atualização da toolchain precisa tratar esses avisos.
- Não existem medições registradas de latência, consumo, precisão ou compatibilidade por dispositivo.
- A injeção manual e o acoplamento do fluxo inicial ainda precisam de testes.
- Build bem-sucedido não comprova qualidade do gesto, acessibilidade ou experiência em uso real.

## Roadmap

Consulte [Status e próximos passos](STATUS_E_PROXIMOS_PASSOS.md). A retomada proposta é:

1. escolher Android como plataforma canônica inicial e corrigir a referência `lumen-web`;
2. corrigir a infraestrutura do teste de domínio e manter o APK debug reproduzível em ambiente limpo;
3. fechar um fluxo vertical de câmera, interpretação, traço e salvamento;
4. ampliar testes de domínio e medir desempenho antes de adicionar recursos multimodais.
