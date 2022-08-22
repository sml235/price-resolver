package dev.sml.service;

import dev.sml.model.Price;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PriceResolverTest {

    private static Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, date);
        return Date.from(calendar.toInstant());
    }

    @Test(expected = NullPointerException.class)
    public void testNullParameters() {
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        priceResolver.mergePrices(null, null);
    }

    @Test
    public void testEmptyParameters() {
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Collection<Price> result = priceResolver.mergePrices(Collections.emptyList(), Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testEmptyNewPriceParameters() {
        Set<Price> prises = Set.of(
                Price.builder()
                        .productCode("122856")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(11_000)
                        .build());
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Collection<Price> result = priceResolver.mergePrices(prises, Collections.emptyList());
        assertEquals(prises, result);
    }

    @Test
    public void testEmptyOldPriceParameters() {
        Set<Price> prises = Set.of(
                Price.builder()
                        .productCode("122856")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(11_000)
                        .build());
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Collection<Price> result = priceResolver.mergePrices(Collections.emptyList(), prises);
        assertEquals(prises, result);
    }

    @Test
    public void testMergePricesExample() {
        Set<Price> curPrises = Set.of(
                Price.builder()
                        .productCode("122856")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(11_000)
                        .build(),
                Price.builder()
                        .productCode("122856")
                        .number(2)
                        .depart(1)
                        .begin(getDate(2013, 0, 10))
                        .end(getDate(2013, 0, 20))
                        .value(99_000)
                        .build(),
                Price.builder()
                        .productCode("6654")
                        .number(1)
                        .depart(2)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(5_000)
                        .build()
        );
        Set<Price> newPrises = Set.of(
                Price.builder()
                        .productCode("122856")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 20))
                        .end(getDate(2013, 1, 20))
                        .value(11_000)
                        .build(),
                Price.builder()
                        .productCode("122856")
                        .number(2)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(92_000)
                        .build(),
                Price.builder()
                        .productCode("6654")
                        .number(1)
                        .depart(2)
                        .begin(getDate(2013, 0, 12))
                        .end(getDate(2013, 0, 13))
                        .value(4_000)
                        .build()
        );
        Set<Price> expected = Set.of(
                Price.builder()
                        .productCode("122856")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 1, 20))
                        .value(11_000)
                        .build(),
                Price.builder()
                        .productCode("122856")
                        .number(2)
                        .depart(1)
                        .begin(getDate(2013, 0, 10))
                        .end(getDate(2013, 0, 15))
                        .value(99_000)
                        .build(),
                Price.builder()
                        .productCode("122856")
                        .number(2)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(92_000)
                        .build(),
                Price.builder()
                        .productCode("6654")
                        .number(1)
                        .depart(2)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 12))
                        .value(5_000)
                        .build(),
                Price.builder()
                        .productCode("6654")
                        .number(1)
                        .depart(2)
                        .begin(getDate(2013, 0, 12))
                        .end(getDate(2013, 0, 13))
                        .value(4_000)
                        .build(),
                Price.builder()
                        .productCode("6654")
                        .number(1)
                        .depart(2)
                        .begin(getDate(2013, 0, 13))
                        .end(getDate(2013, 0, 31))
                        .value(5_000)
                        .build()
        );
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Set<Price> result = Set.copyOf(priceResolver.mergePrices(curPrises, newPrises));
        assertEquals(expected, result);
    }

    @Test
    public void testMergePricesExample_1() {
        Set<Price> curPrises = Set.of(
                Price.builder()
                        .productCode("1")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(50)
                        .build()
        );
        Set<Price> newPrises = Set.of(
                Price.builder()
                        .productCode("1")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 10))
                        .end(getDate(2013, 0, 21))
                        .value(60)
                        .build()
        );
        Set<Price> expected = Set.of(
                Price.builder()
                        .productCode("1")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 10))
                        .value(50)
                        .build(),
                Price.builder()
                        .productCode("1")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 10))
                        .end(getDate(2013, 0, 21))
                        .value(60)
                        .build(),
                Price.builder()
                        .productCode("1")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 21))
                        .end(getDate(2013, 0, 31))
                        .value(50)
                        .build()
        );
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Set<Price> result = Set.copyOf(priceResolver.mergePrices(curPrises, newPrises));
        assertEquals(expected, result);
    }

    @Test
    public void testMergePricesExample_2() {
        Set<Price> curPrises = Set.of(
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 21))
                        .value(100)
                        .build(),
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 21))
                        .end(getDate(2013, 0, 31))
                        .value(120)
                        .build()
        );
        Set<Price> newPrises = Set.of(
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(110)
                        .build()
        );
        Set<Price> expected = Set.of(
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 15))
                        .value(100)
                        .build(),
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(110)
                        .build(),
                Price.builder()
                        .productCode("2")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 25))
                        .end(getDate(2013, 0, 31))
                        .value(120)
                        .build()
        );
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Set<Price> result = Set.copyOf(priceResolver.mergePrices(curPrises, newPrises));
        assertEquals(expected, result);
    }

    @Test
    public void testMergePricesExample_3() {
        Set<Price> curPrises = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 11))
                        .value(80)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 11))
                        .end(getDate(2013, 0, 21))
                        .value(87)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 21))
                        .end(getDate(2013, 0, 31))
                        .value(90)
                        .build()
        );
        Set<Price> newPrises = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 5))
                        .end(getDate(2013, 0, 15))
                        .value(80)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(85)
                        .build()
        );
        Set<Price> expected = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 15))
                        .value(80)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 15))
                        .end(getDate(2013, 0, 25))
                        .value(85)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 25))
                        .end(getDate(2013, 0, 31))
                        .value(90)
                        .build()
        );
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Set<Price> result = Set.copyOf(priceResolver.mergePrices(curPrises, newPrises));
        assertEquals(expected, result);
    }

    @Test
    public void testMergePricesExample_4() {
        Set<Price> curPrises = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 11))
                        .value(80)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 11))
                        .end(getDate(2013, 0, 21))
                        .value(87)
                        .build(),
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 21))
                        .end(getDate(2013, 0, 31))
                        .value(80)
                        .build()
        );
        Set<Price> newPrises = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 11))
                        .end(getDate(2013, 0, 21))
                        .value(80)
                        .build()
        );
        Set<Price> expected = Set.of(
                Price.builder()
                        .productCode("3")
                        .number(1)
                        .depart(1)
                        .begin(getDate(2013, 0, 1))
                        .end(getDate(2013, 0, 31))
                        .value(80)
                        .build()
        );
        PriceResolver priceResolver = PriceResolver.getDefaultPriceResolver();
        Set<Price> result = Set.copyOf(priceResolver.mergePrices(curPrises, newPrises));
        assertEquals(expected, result);
    }
}