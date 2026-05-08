# LÚMEN | Spatial Drawing Engine (Multi-Platform)

![Lúmen Logo](./lumen-web/src/assets/logo.png)

**Lúmen** é um ecossistema de desenho espacial de alta performance que utiliza Inteligência Artificial e Visão Computacional para transformar movimentos das mãos em arte digital vibrante. Disponível para **Web** e **Android Nativo**.

---

## 🚀 Tecnologias de Ponta

### 🌐 Web Engine
*   **Core**: [React 19](https://react.dev/) + [Vite](https://vitejs.dev/)
*   **Visão**: [MediaPipe Hands](https://developers.google.com/mediapipe/solutions/vision/hand_landmarker)
*   **Render**: HTML5 Canvas Layered Pipeline
*   **Áudio**: Web Audio API (Sintetizadores Reativos)

### 📱 Android Native
*   **Linguagem**: [Kotlin 2.2.21](https://kotlinlang.org/)
*   **UI**: [Jetpack Compose](https://developer.android.com/compose)
*   **IA**: TensorFlow Lite / MediaPipe Android SDK
*   **Arquitetura**: Clean Architecture (Módulos: `feature-drawing`, `domain`, `data`)

---

## 📂 Estrutura do Ecossistema

```txt
/Lúmen
├── lumen-web/           # Engine Web (React)
│   ├── src/
│   │   ├── utils/       # Motor de renderização e som
│   │   └── App.jsx      # Orquestrador de IA
├── app/                 # Aplicativo Android Principal
├── feature-drawing/     # Módulo de desenho nativo (Kotlin)
├── domain/              # Regras de negócio compartilhadas
└── README.md            # Documentação unificada
```

---

## 🛠️ Instalação e Execução

1.  Clone o repositório.
2.  Instale as dependências:
    ```bash
    npm install
    ```
3.  Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```

---

## 🧠 Protocolos de Engenharia (V6.1)

Este projeto segue rigorosamente o **Protocolo Global de Engenharia de Bruno Giovani**, priorizando:
*   **Responsividade Absoluta**: Tarefas pesadas executadas de forma assíncrona.
*   **Zero Leak Policy**: Gestão segura de segredos e higienização de repositório.
*   **Offline-First Strategy**: Persistência local resiliente.
*   **UI/UX Standard Premium**: Estética moderna com micro-interações fluidas.

---

## 👨‍💻 Autor

**Bruno Giovani (2026)**
Engenharia de Software de Alta Performance | GovTechs | GIS | IA

---
*Lúmen - Onde o gesto se torna luz.*
