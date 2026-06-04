package sortapp;

import auto.Auto;
import auto.AutoBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InputManager {

    private static final Random RANDOM = new Random();
    private static final String[] MODEL_SUFFIXES = {"Sedan", "Coupe", "SUV", "Hatchback", "Truck", "Van", "Wagon", "Convertible"};

    public List<Auto> generateRandom(int count) {
        List<Auto> autos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String model = generateRandomModel();
            int year = RANDOM.nextInt(2026 - 1886 + 1) + 1886;
            double power = RANDOM.nextDouble(50.0, 500.0);
            autos.add(new Auto(model, year, power));
        }
        return autos;
    }

    private String generateRandomModel() {
        String suffix = MODEL_SUFFIXES[RANDOM.nextInt(MODEL_SUFFIXES.length)];
        int number = RANDOM.nextInt(9000) + 1000;
        return suffix + "-" + number;
    }

    public List<Auto> loadFromFile(String path, int count) {
        List<Auto> autos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int loaded = 0;
            while ((line = reader.readLine()) != null && loaded < count) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    List<Auto> parsed = AutoBuilder.fromString(line);
                    if (!parsed.isEmpty()) {
                        for (Auto auto : parsed) {
                            autos.add(auto);
                            loaded++;
                            if (loaded >= count) break;
                        }
                    } else {
                        IO.println("Warning: Could not parse line: " + line);
                    }
                } catch (Exception e) {
                    IO.println("Warning: Error parsing line '" + line + "': " + e.getMessage());
                }
            }
        } catch (IOException e) {
            IO.println("Error reading file '" + path + "': " + e.getMessage());
        }
        return autos;
    }

    public List<Auto> manualInput(int maxCount) {
        List<Auto> autos = new ArrayList<>();

        if (maxCount == 0) {
            while (true) {
                IO.println("Enter entry (model;year;power) or empty line to finish:");
                String line = IO.readLine();
                if (line.isEmpty()) {
                    break;
                }
                try {
                    List<Auto> parsed = AutoBuilder.fromString(line);
                    if (!parsed.isEmpty()) {
                        autos.addAll(parsed);
                    } else {
                        IO.println("Warning: Could not parse entry: " + line);
                    }
                } catch (Exception e) {
                    IO.println("Warning: Error parsing entry '" + line + "': " + e.getMessage());
                }
            }
        } else {
            for (int i = 0; i < maxCount; i++) {
                IO.println("Enter entry " + (i + 1) + " of " + maxCount + " (model;year;power) or empty line to finish:");
                String line = IO.readLine();
                if (line.isEmpty()) {
                    IO.println("Stopped early.");
                    break;
                }
                try {
                    List<Auto> parsed = AutoBuilder.fromString(line);
                    if (!parsed.isEmpty()) {
                        autos.addAll(parsed);
                    } else {
                        IO.println("Warning: Could not parse entry: " + line);
                        i--; // Don't count this attempt
                    }
                } catch (Exception e) {
                    IO.println("Warning: Error parsing entry '" + line + "': " + e.getMessage());
                    i--; // Don't count this attempt
                }
            }
        }

        return autos;
    }
}
