# ✅ Como Executar o Projeto JavaFX

## 📋 Status Atual

### O que está funcionando:
- ✅ Código fonte compilado corretamente
- ✅ JARs do JavaFX (javafx.controls.jar, javafx.base.jar, etc.) na pasta `lib`
- ✅ DLLs do JavaFX na pasta `lib`
- ✅ VS Code configurado corretamente

### ⚠️ Problema Identificado:
**As DLLs do JavaFX não são compatíveis com Java 24**

Erro: `Graphics Device initialization failed for: d3d, sw - No toolkit found`

---

## 🔧 SOLUÇÃO: Baixar JavaFX SDK Compatível

### Passo 1: Download do JavaFX SDK
1. Acesse: https://gluonhq.com/products/javafx/
2. Selecione:
   - **Version**: 24.0.2 (mesma versão do seu Java)
   - **Operating System**: Windows
   - **Architecture**: x64
   - **Type**: SDK
3. Clique em **Download**

### Passo 2: Instalar o JavaFX
1. Extraia o arquivo ZIP baixado (ex: `javafx-sdk-24.0.2`)
2. Abra a pasta extraída e vá até `javafx-sdk-24.0.2/lib`
3. **Copie TODOS os arquivos** (JARs + DLLs) dessa pasta
4. **Cole na pasta** `C:\Users\gabri\OneDrive\Ambiente de Trabalho\javaFX\AulasJavaFX\lib`
   - Clique em "Substituir" se perguntado

### Passo 3: Executar no VS Code
1. Abra o arquivo `AulasJavaFX/src/application/Main.java`
2. Pressione **F5** ou clique em **Run > Start Debugging**
3. Selecione a configuração **"Main JavaFX"**
4. O projeto deve executar! 🎉

---

## 🖥️ Executar pela Linha de Comando

Depois de atualizar as DLLs, use este comando:

```powershell
cd "C:\Users\gabri\OneDrive\Ambiente de Trabalho\javaFX\AulasJavaFX"
java --module-path "lib" --add-modules javafx.controls,javafx.fxml -cp bin application.Main
```

---

## 📝 Configurações do VS Code

### ✅ `.vscode/launch.json` (já configurado)
```json
{
    "type": "java",
    "name": "Main JavaFX",
    "request": "launch",
    "mainClass": "application.Main",
    "projectName": "AulasJavaFx",
    "vmArgs": "--module-path \"C:/Users/gabri/OneDrive/Ambiente de Trabalho/javaFX/AulasJavaFX/lib\" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics"
}
```

### ✅ `.vscode/settings.json` (já configurado)
```json
{
    "java.project.sourcePaths": ["AulasJavaFX/src"],
    "java.project.outputPath": "AulasJavaFX/bin",
    "java.project.referencedLibraries": ["AulasJavaFX/lib/**/*.jar"]
}
```

---

## ❓ Alternativa: Usar Java 21 LTS

Se não conseguir baixar o JavaFX 24, você pode:

1. Instalar o **Java 21 LTS** (versão mais estável)
2. Baixar o **JavaFX SDK 21**
3. Configurar o VS Code para usar Java 21

---

## 🆘 Precisa de Ajuda?

Se após seguir esses passos ainda houver erro, verifique:
- [ ] As DLLs estão na pasta `lib` junto com os JARs?
- [ ] O Java instalado é o Java 24.0.2?
- [ ] Recompilou o projeto depois de atualizar as bibliotecas?
