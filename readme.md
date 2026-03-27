# 🖼️ Picture Editor

**Picture Editor** is a desktop image processing application built in **Java** using **JavaFX**. It lets users load images, apply a range of processing operations, visualise a real-time histogram, and save the result — all through a clean and intuitive graphical interface.

---

## 🎬 Demo

[![Picture Editor Demo](https://img.youtube.com/vi/aC9ij3eDnZ4/maxresdefault.jpg)](https://youtu.be/aC9ij3eDnZ4)

> Click the thumbnail to watch the demo on YouTube.

---

## 📸 Screenshots

<!-- Replace the paths below with your actual screenshot files -->
<!-- Recommended: create a `screenshots/` folder in the repo root and add your images there -->

<p align="center">
  <img src="Skärmbild 2026-03-27 033450.png" width="45%" alt="Main View"/>
</p>

---

## ✨ Features

### 🎨 Image Processing Operations
- **Grayscale** — converts the image to black and white
- **Blur** — applies a smoothing filter to reduce detail
- **Sharpen** — enhances edges and fine detail
- **Invert Colors** — inverts the RGB values of every pixel
- **Window/Level** — adjusts contrast and brightness via interactive sliders
- **Restore Original** — resets the image to its original unmodified state

### 📊 Histogram
- Live RGB histogram that updates after every applied operation
- Displayed alongside the image for quick visual feedback

### 📂 File I/O
- **Load** any image from disk via a file chooser dialog
- **Save** the processed image back to disk in your chosen location

### 🏗️ Architecture
- **MVC pattern** — clean separation between model, view, and controller
- **Facade pattern** (`ImageProcessorModel`) — single entry point that coordinates all processing operations
- Pixel-level processing via an `int[][]` matrix, decoupled from the JavaFX image layer

---

## 🗂️ Project Structure

```
Picture-Editor/
├── src/main/java/lab4edisochdanils/
│   ├── ImageProcessorApplication.java   # Entry point (JavaFX Application)
│   ├── model/
│   │   ├── ImageProcessorModel.java     # Facade — coordinates all operations
│   │   ├── ImageOperation.java          # Enum of available operations
│   │   ├── Blur.java
│   │   ├── GrayScale.java
│   │   ├── Histogram.java
│   │   ├── InvertColors.java
│   │   ├── Sharpening.java
│   │   └── WindowLevel.java
│   ├── view/
│   │   ├── ImageProcessorView.java      # Main UI layout (menu, image, histogram)
│   │   ├── ImageProcessorController.java
│   │   ├── HistogramView.java           # Canvas-based histogram rendering
│   │   ├── FileIO.java                  # Load/save dialogs
│   │   └── AlertHelper.java
│   └── utils/
│       ├── ImagePixelsConverter.java    # JavaFX Image ↔ int[][] pixel matrix
│       ├── PixelConverter.java          # ARGB component extraction
│       └── ImageProcessingException.java
├── src/main/resources/
│   └── lab4edisochdanils/images/        # Sample images bundled with the app
│       ├── skull_ct.png
│       ├── devil.png
│       ├── rastered_eye.jpg
│       └── space.jpg
└── pom.xml
```

---

## 🛠️ Technology Stack

| Component | Technology |
|---|---|
| Language | Java 21 |
| UI Framework | JavaFX 21 |
| Build Tool | Maven (with included `mvnw` wrapper) |
| Testing | JUnit Jupiter 5.10 |
| Architecture | MVC + Facade |

---

## 🚀 Installation & Running

### Prerequisites

- **Java 21** or later
- Maven is **not** required — the Maven wrapper (`mvnw`) is included

### Clone & Run

```bash
git clone https://github.com/YOUR_USERNAME/Picture-Editor.git
cd Picture-Editor
```

**macOS / Linux**
```bash
./mvnw clean javafx:run
```

**Windows**
```bash
mvnw.cmd clean javafx:run
```

### Alternative: IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Right-click `ImageProcessorApplication.java`
3. Select **Run 'ImageProcessorApplication.main()'**

### Alternative: Eclipse

1. Open the project in Eclipse
2. Right-click `ImageProcessorApplication.java`
3. Select **Run As → Java Application**

---

## 🕹️ How to Use

1. **Launch** the application — a sample CT scan image loads automatically
2. **Load your own image** via `File → Load Image...`
3. **Apply operations** from the `Process` menu:
   - Grayscale, Blur, Sharpen, Invert Colors
   - Window/Level — reveals interactive sliders for contrast adjustment
   - Restore Original — undoes all changes
4. **Watch the histogram** update in real time on the left panel
5. **Save** your result via `File → Save Image...`

---

## 🔧 Common Issues

| Error | Cause | Fix |
|---|---|---|
| `ClassNotFoundException` | Running an old run config | Delete old configs and use `./mvnw clean javafx:run` |
| `ModuleNotFoundException` | Project not compiled | Run `./mvnw clean compile` first |
| Blank image on startup | Resources not on classpath | Build via Maven, not plain `javac` |

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 📬 Contact

**Edis Avdic** – edis_123@live.se  
GitHub: [github.com/YOUR_USERNAME](https://github.com/edisavdicc)
