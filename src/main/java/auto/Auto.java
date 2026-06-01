package auto;

import java.util.Objects;
import java.util.StringJoiner;

public class Auto {
    private final String model;
    private final int productionYear;
    private final double power;

    public String getModel() {
        return model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public Double getPower() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Auto auto = (Auto) o;
        return getModel().equals(auto.getModel()) && getProductionYear().equals(auto.getProductionYear()) && getPower().equals(auto.getPower());
    }

    @Override
    public int hashCode() {
        int result = getModel().hashCode();
        result = 31 * result + getProductionYear().hashCode();
        result = 31 * result + getPower().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Auto.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("productionYear=" + productionYear)
                .add("power=" + power)
                .toString();
    }

    public Auto(String model, int productionYear, double power) {
        this.model = model;
        this.productionYear = productionYear;
        this.power = power;
    }
}
