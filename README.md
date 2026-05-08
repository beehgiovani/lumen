# LÚMEN | Spatial Drawing Engine

![Lúmen Logo](./src/assets/logo.png)

**Lúmen** é uma engine de desenho espacial de alta performance que utiliza Inteligência Artificial e Visão Computacional para transformar movimentos das mãos em arte digital vibrante e dinâmica.

---

## 🚀 Tecnologias de Ponta

*   **Core**: [React 19](https://react.dev/) + [Vite](https://vitejs.dev/) (Ultra Fast Build & HMR)
*   **Visão Computacional**: [MediaPipe Hands](https://developers.google.com/mediapipe/solutions/vision/hand_landmarker) (Detecção 21 pontos em tempo real)
*   **Renderização**: HTML5 Canvas API com Pipeline de Otimização Layered
*   **Áudio**: Web Audio API (Sintetizadores dinâmicos baseados em velocidade e ferramenta)
*   **Design**: CSS Moderno (Glassmorphism, Backdrop Filters, Soft Shadows)
*   **Arquitetura**: Clean Architecture com separação rigorosa de domínios (Core Engine, UI, Business Logic)

---

## ✨ Funcionalidades Premium

*   **21 Ferramentas de Desenho**: De pincéis neon a geradores de galáxias e formas 3D projetadas.
*   **Som Reativo**: Cada pincel possui uma assinatura sonora única que reage à velocidade do movimento.
*   **Controles Gestuais**:
    *   **Apontar**: Desenhar.
    *   **Punho Fechado**: Borracha inteligente.
    *   **Polegar para Baixo**: Limpar tela (Gesture-hold).
    *   **Pinch/Hover**: Interação com interface sem toque.
*   **Efeitos Avançados**: Kaleidoscope, Mirror Mode, Bloom Intensity, Echo Persistence.
*   **AI Autocomplete**: Detecção e correção automática de formas geométricas (Círculos e Quadrados).
*   **Exportação**: Salve sua arte em alta resolução (PNG).

---

## 📂 Estrutura do Projeto

```txt
/lumen-web
├── src/
│   ├── assets/          # Logos e recursos estáticos
│   ├── components/      # Componentes UI (Sidebar, Controls, Onboarding)
│   ├── utils/
│   │   ├── drawingEngine.js # Motor de renderização principal
│   │   ├── soundEngine.js   # Motor de síntese de áudio
│   │   └── constants.js     # Configurações globais e cores
│   └── App.jsx          # Orquestrador principal e lógica de IA
├── public/              # Arquivos públicos e WASM do MediaPipe
└── package.json         # Dependências e scripts
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
