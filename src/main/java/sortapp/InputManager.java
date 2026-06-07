package sortapp;

import auto.Auto;
import auto.AutoBuilder;
import collection.CustomArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InputManager {

    private static final Random RANDOM = new Random();
    private static final String[] MODEL_SUFFIXES = {"Sedan", "Coupe", "SUV", "Hatchback", "Truck", "Van", "Wagon", "Convertible"};

    public List<Auto> generateRandom(int count) {
        return IntStream.range(0, count)
                .mapToObj(_ -> new Auto(
                        generateRandomModel(),
                        RANDOM.nextInt(2026 - 1886 + 1) + 1886,
                        RANDOM.nextDouble(50.0, 500.0)
                ))
                .collect(Collectors.toCollection(CustomArrayList::new));
    }

    public List<Auto> loadFromFile(String path, int count) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .flatMap(this::parseLineSafely)
                    .limit(count)
                    .collect(Collectors.toCollection(CustomArrayList::new));
        } catch (IOException e) {
            IO.println("Error reading file '" + path + "': " + e.getMessage());
            return new CustomArrayList<>();
        }
    }

    public List<Auto> manualInput(int maxCount) {
        Stream<String> lines = maxCount == 0
                ? createUnlimitedInputStream()
                : createLimitedInputStream(maxCount);

        List<Auto> autos = lines
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .flatMap(this::parseLineSafely)
                .collect(Collectors.toCollection(CustomArrayList::new));

        if (maxCount > 0 && autos.size() < maxCount) {
            IO.println("Stopped early. Entered " + autos.size() + " entries.");
        }

        return autos;
    }

    private Stream<String> createUnlimitedInputStream() {
        return Stream.generate(IO::readLine)
                .takeWhile(line -> !line.trim().isEmpty());
    }

    private Stream<String> createLimitedInputStream(int maxCount) {
        return IntStream.range(0, maxCount)
                .mapToObj(i -> {
                    IO.println("Enter entry " + (i + 1) + " of " + maxCount +
                            " (model;year;power) or empty line to finish:");
                    return IO.readLine();
                })
                .takeWhile(line -> !line.trim().isEmpty());
    }

    private Stream<Auto> parseLineSafely(String line) {
        try {
            List<Auto> parsed = AutoBuilder.fromString(line);
            return parsed.stream();
        } catch (Exception e) {
            IO.println("Warning: Error parsing line '" + line + "': " + e.getMessage());
            return Stream.empty();
        }
    }

    private String generateRandomModel() {
        String suffix = MODEL_SUFFIXES[RANDOM.nextInt(MODEL_SUFFIXES.length)];
        int number = RANDOM.nextInt(9000) + 1000;
        return suffix + "-" + number;
    }
}