package sortapp;

import auto.Auto;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputManager Tests")
class InputManagerTest {

    private InputManager inputManager;

    @BeforeEach
    void setUp() {
        inputManager = new InputManager();
    }

    @Test
    @DisplayName("generateRandom produces valid Autos with correct ranges")
    void generateRandomProducesValidAutos() {
        int count = 10;
        List<Auto> autos = inputManager.generateRandom(count);

        assertEquals(count, autos.size());

        for (Auto auto : autos) {
            assertNotNull(auto.getModel());
            assertFalse(auto.getModel().isEmpty());
            assertTrue(auto.getProductionYear() >= 1886);
            assertTrue(auto.getProductionYear() <= 2026);
            assertTrue(auto.getPower() >= 50.0);
            assertTrue(auto.getPower() <= 500.0);
        }
    }

    @Test
    @DisplayName("generateRandom with zero count returns empty list")
    void generateRandomZeroCount() {
        List<Auto> autos = inputManager.generateRandom(0);
        assertTrue(autos.isEmpty());
    }

    @Test
    @DisplayName("generateRandom produces different values on each call")
    void generateRandomProducesDifferentValues() {
        List<Auto> autos1 = inputManager.generateRandom(5);
        List<Auto> autos2 = inputManager.generateRandom(5);

        assertNotEquals(autos1, autos2);
    }

    @Test
    @DisplayName("loadFromFile stops at count limit")
    void loadFromFileStopsAtCount() throws IOException {
        Path tempFile = Files.createTempFile("test-autos", ".txt");
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                sb.append("Model-").append(i).append(";").append(2000 + i).append(";100.0").append(System.lineSeparator());
            }
            Files.writeString(tempFile, sb.toString());

            // Load only 3 entries
            List<Auto> autos = inputManager.loadFromFile(tempFile.toString(), 3);

            assertEquals(3, autos.size());
            assertEquals("Model-0", autos.get(0).getModel());
            assertEquals(2000, autos.get(0).getProductionYear());
            assertEquals("Model-2", autos.get(2).getModel());
            assertEquals(2002, autos.get(2).getProductionYear());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    @DisplayName("loadFromFile handles non-existent file gracefully")
    void loadFromFileNonExistent() {
        List<Auto> autos = inputManager.loadFromFile("/non/existent/file.txt", 10);
        assertTrue(autos.isEmpty());
    }

    @Test
    @DisplayName("loadFromFile skips blank lines and counts only valid entries")
    void loadFromFileSkipsBlankLines() throws IOException {
        Path tempFile = Files.createTempFile("test-autos-blanks", ".txt");
        try {
            // Write entries with blank lines interspersed
            StringBuilder sb = new StringBuilder();
            sb.append("Model-A;1999;100.0\n");
            sb.append("\n");
            sb.append("Model-B;2000;120.0\n");
            sb.append("\n");
            sb.append("Model-C;2001;140.0\n");
            Files.writeString(tempFile, sb.toString());

            List<Auto> autos = inputManager.loadFromFile(tempFile.toString(), 2);

            assertEquals(2, autos.size());
            assertEquals("Model-A", autos.get(0).getModel());
            assertEquals("Model-B", autos.get(1).getModel());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    @DisplayName("loadFromFile handles invalid parse format")
    void loadFromFileInvalidFormat() throws IOException {
        Path tempFile = Files.createTempFile("test-autos-invalid", ".txt");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Valid-1;2000;100.0\n");
            sb.append("Invalid-Format\n");
            sb.append("Valid-2;2001;110.0\n");
            Files.writeString(tempFile, sb.toString());

            List<Auto> autos = inputManager.loadFromFile(tempFile.toString(), 2);

            assertEquals(2, autos.size());
            assertEquals("Valid-1", autos.get(0).getModel());
            assertEquals("Valid-2", autos.get(1).getModel());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    @DisplayName("loadFromFile with count larger than file returns all entries")
    void loadFromFileCountLargerThanFile() throws IOException {
        Path tempFile = Files.createTempFile("test-autos-partial", ".txt");
        try {
            StringBuilder sb = new StringBuilder();
            // Write 3 entries
            for (int i = 0; i < 3; i++) {
                sb.append("Car-").append(i).append(";").append(2010 + i).append(";150.0").append(System.lineSeparator());
            }
            Files.writeString(tempFile, sb.toString());

            // Ask for 10 entries, but only 3 exist
            List<Auto> autos = inputManager.loadFromFile(tempFile.toString(), 10);

            assertEquals(3, autos.size());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
