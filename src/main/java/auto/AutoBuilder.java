package auto;

import java.util.ArrayList;
import java.util.List;

public class AutoBuilder {
    private String model;
    private int productionYear;
    private double power;

    public AutoBuilder() {
    }

    public AutoBuilder(Auto existingAuto) {
        this.model = existingAuto.getModel();
        this.productionYear = existingAuto.getProductionYear();
        this.power = existingAuto.getPower();
    }
    
    private void validateModel(String model) {
        if (model == null ||  model.isEmpty()) {
            throw new IllegalArgumentException("model must not be null");
        }
    }

    private void validateProductionYear(int productionYear) {
        if (productionYear < 1886) {
            throw new IllegalArgumentException("productionYear must be >= 1886, got: " + productionYear);
        }
    }

    private void validatePower(double power) {
        if (power < 0.1) {
            throw new IllegalArgumentException("power must be >= 0.1, got: " + power);
        }
    }

    public AutoBuilder model(String model) {
        validateModel(model);
        this.model = model;
        return this;
    }

    public AutoBuilder productionYear(int productionYear) {
        validateProductionYear(productionYear);
        this.productionYear = productionYear;
        return this;
    }

    public AutoBuilder power(double power) {
        validatePower(power);
        this.power = power;
        return this;
    }

    public Auto build() {
        validateModel(this.model);
        validateProductionYear(this.productionYear);
        validatePower(this.power);
        return new Auto(model, productionYear, power);
    }
    
    public static List<Auto> fromString(String s) {
        if (s == null || s.isBlank()) {
            return new ArrayList<>();
        }

        String[] lines = s.split("\n");
        List<Auto> result = new ArrayList<>();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) {
                continue;
            }

            String[] parts = trimmedLine.split(";");
            if (parts.length != 3) {
                throw new IllegalArgumentException(
                        "Invalid format in line '" + trimmedLine + "': expected 'Model;Year;Power'"
                );
            }

            String model = parts[0].trim();
            int year;
            double power;

            try {
                year = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid year in line '" + trimmedLine + "': " + parts[1].trim(), e);
            }

            try {
                power = Double.parseDouble(parts[2].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid power in line '" + trimmedLine + "': " + parts[2].trim(), e);
            }

            result.add(new AutoBuilder()
                    .model(model)
                    .productionYear(year)
                    .power(power)
                    .build());
        }

        return result;
    }
}
