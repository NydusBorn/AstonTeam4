package auto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AutoBuilderTest — AutoBuilder validation")
class AutoBuilderTest {
    @Test
    @DisplayName("builds Auto with default constructor")
    void buildAutoWithDefaultConstructor() {
        Auto auto = new auto.AutoBuilder()
                .model("Toyota")
                .productionYear(2020)
                .power(150.5)
                .build();

        assertEquals("Toyota", auto.getModel());
        assertEquals(2020, auto.getProductionYear());
        assertEquals(150.5, auto.getPower());
    }

    @Test
    @DisplayName("throws on null model")
    void throwsOnNullModel() {
        auto.AutoBuilder builder = new auto.AutoBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.model(null));
    }

    @Test
    @DisplayName("throws on empty model")
    void throwsOnEmptyModel() {
        auto.AutoBuilder builder = new auto.AutoBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.model(""));
    }

    @Test
    @DisplayName("throws on model before year before power")
    void throwsOnYearBefore1886() {
        auto.AutoBuilder builder = new auto.AutoBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.productionYear(1800));
    }

    @Test
    @DisplayName("throws on power too low")
    void throwsOnPowerTooLow() {
        auto.AutoBuilder builder = new auto.AutoBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.power(0.05));
    }

    @Test
    @DisplayName("builds valid minimum Auto")
    void buildsValidMinimumAuto() {
        Auto auto = new auto.AutoBuilder()
                .model("Benz")
                .productionYear(1886)
                .power(0.1)
                .build();

        assertEquals("Benz", auto.getModel());
        assertEquals(1886, auto.getProductionYear());
        assertEquals(0.1, auto.getPower());
    }

    @Test
    @DisplayName("builds from existing Auto")
    void buildFromExistingAuto() {
        Auto original = new Auto("Toyota", 2020, 150.5);
        Auto copied = new auto.AutoBuilder(original)
                .model("Honda")
                .build();

        assertEquals("Honda", copied.getModel());
        assertEquals(2020, copied.getProductionYear());
        assertEquals(150.5, copied.getPower());
    }

    @Test
    @DisplayName("fromString parses valid input")
    void fromStringParsesValidInput() {
        String input = "Toyota;2020;150.5\nHonda;2018;180.0";
        List<Auto> autos = auto.AutoBuilder.fromString(input);

        assertEquals(2, autos.size());
        assertEquals("Toyota", autos.get(0).getModel());
        assertEquals(2020, autos.get(0).getProductionYear());
        assertEquals(150.5, autos.get(0).getPower());
        assertEquals("Honda", autos.get(1).getModel());
        assertEquals(2018, autos.get(1).getProductionYear());
        assertEquals(180.0, autos.get(1).getPower());
    }

    @Test
    @DisplayName("fromString returns empty list for blank input")
    void fromStringReturnsEmptyForBlankInput() {
        assertTrue(auto.AutoBuilder.fromString("").isEmpty());
        assertTrue(auto.AutoBuilder.fromString("   ").isEmpty());
        assertTrue(auto.AutoBuilder.fromString(null).isEmpty());
    }

    @Test
    @DisplayName("fromString throws on invalid format")
    void fromStringThrowsOnInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> auto.AutoBuilder.fromString("Toyota;2020"));
        assertThrows(IllegalArgumentException.class, () -> auto.AutoBuilder.fromString("Toyota;;150.5"));
    }

    @Test
    @DisplayName("fromString throws on invalid year")
    void fromStringThrowsOnInvalidYear() {
        assertThrows(IllegalArgumentException.class, () -> auto.AutoBuilder.fromString("Toyota;abc;150.5"));
    }

    @Test
    @DisplayName("fromString throws on invalid power")
    void fromStringThrowsOnInvalidPower() {
        assertThrows(IllegalArgumentException.class, () -> auto.AutoBuilder.fromString("Toyota;2020;xyz"));
    }

    @Test
    @DisplayName("fromString handles whitespace")
    void fromStringHandlesWhitespace() {
        String input = "  Toyota ; 2020 ; 150.5 \n  Honda ; 2018 ; 180.0  ";
        List<Auto> autos = auto.AutoBuilder.fromString(input);

        assertEquals(2, autos.size());
        assertEquals("Toyota", autos.get(0).getModel());
        assertEquals(2020, autos.get(0).getProductionYear());
        assertEquals(150.5, autos.get(0).getPower());
    }
}