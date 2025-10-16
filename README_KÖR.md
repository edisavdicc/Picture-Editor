# Hur man kör Image Processor

## ✅ Rätt sätt att köra programmet

### Metod 1: Maven (Rekommenderat)
```bash
./mvnw clean javafx:run
```

### Metod 2: IntelliJ IDEA
1. Öppna projektet i IntelliJ
2. Högerklicka på `ImageProcessorApplication.java`
3. Välj "Run 'ImageProcessorApplication.main()'"

### Metod 3: Eclipse
1. Öppna projektet i Eclipse
2. Högerklicka på `ImageProcessorApplication.java`
3. Välj "Run As" → "Java Application"

## ❌ Vanliga fel

### ClassNotFoundException: lab4edisochdanils.InvertApplication
**Problem:** Du försöker köra en gammal/felaktig klass
**Lösning:** 
- Radera gamla run configurations
- Använd `./mvnw clean javafx:run`
- Main-klassen heter: `lab4edisochdanils.ImageProcessorApplication`

### ModuleNotFoundException
**Problem:** Projektet är inte kompilerat
**Lösning:** Kör `./mvnw clean compile` först

## 📝 Krav
- Java 21 eller senare
- Maven (eller använd inkluderad mvnw)





