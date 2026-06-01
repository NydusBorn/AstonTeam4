package auto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Auto")
class AutoTest {

    @Nested
    @DisplayName("Construction")
    class ConstructionTests {

        @Test
        @DisplayName("creates Auto with valid fields")
        void createsAutoWithValidFields() {
            Auto auto = new Auto("Toyota", 2020, 150.5);
            assertEquals("Toyota", auto.getModel());
            assertEquals(2020, auto.getProductionYear());
            assertEquals(150.5, auto.getPower());
        }

        @Test
        @DisplayName("creates Auto with minimum year")
        void createsAutoWithMinimumYear() {
            Auto auto = new Auto("Benz", 1886, 0.1);
            assertEquals(1886, auto.getProductionYear());
            assertEquals(0.1, auto.getPower());
        }

        @Test
        @DisplayName("creates Auto with large values")
        void createsAutoWithLargeValues() {
            Auto auto = new Auto("Bugatti", 2025, 1600.0);
            assertEquals(2025, auto.getProductionYear());
            assertEquals(1600.0, auto.getPower());
        }
    }

    @Nested
    @DisplayName("equals and hashCode")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("equals returns true for identical objects")
        void equalsReturnsTrueForIdenticalObjects() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Toyota", 2020, 150.5);
            assertEquals(a1, a2);
        }

        @Test
        @DisplayName("equals returns false for different model")
        void equalsReturnsFalseForDifferentModel() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Honda", 2020, 150.5);
            assertNotEquals(a1, a2);
        }

        @Test
        @DisplayName("equals returns false for different productionYear")
        void equalsReturnsFalseForDifferentProductionYear() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Toyota", 2019, 150.5);
            assertNotEquals(a1, a2);
        }

        @Test
        @DisplayName("equals returns false for different power")
        void equalsReturnsFalseForDifferentPower() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Toyota", 2020, 200.0);
            assertNotEquals(a1, a2);
        }

        @Test
        @DisplayName("equals returns false for null")
        void equalsReturnsFalseForNull() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            assertNotEquals(a1, null);
        }

        @Test
        @DisplayName("equals returns false for different class")
        void equalsReturnsFalseForDifferentClass() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            assertNotEquals(a1, "not an auto");
        }

        @Test
        @DisplayName("hashCode is consistent for equal objects")
        void hashCodeIsConsistentForEqualObjects() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Toyota", 2020, 150.5);
            assertEquals(a1.hashCode(), a2.hashCode());
        }

        @Test
        @DisplayName("equal objects have same hashCode")
        void equalObjectsHaveSameHashCode() {
            Auto a1 = new Auto("Toyota", 2020, 150.5);
            Auto a2 = new Auto("Toyota", 2020, 150.5);
            assertEquals(a1, a2);
            assertEquals(a1.hashCode(), a2.hashCode());
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringTests {

        @Test
        @DisplayName("toString contains model, year, and power")
        void toStringContainsAllFields() {
            Auto auto = new Auto("Toyota", 2020, 150.5);
            String str = auto.toString();
            assertTrue(str.contains("Toyota"));
            assertTrue(str.contains("2020"));
            assertTrue(str.contains("150.5"));
            assertTrue(str.startsWith("Auto["));
            assertTrue(str.endsWith("]"));
        }

        @Test
        @DisplayName("toString format is correct")
        void toStringFormatIsCorrect() {
            Auto auto = new Auto("BMW", 2021, 300.0);
            String str = auto.toString();
            assertEquals("Auto[model='BMW', productionYear=2021, power=300.0]", str);
        }
    }
}
