package dev.sml.service;

import dev.sml.model.Price;

import java.util.Collection;

public interface PriceResolver {
    static PriceResolver getDefaultPriceResolver() {
        return new PriceResolverImpl();
    }

    Collection<Price> mergePrices(Collection<Price> currentPrices, Collection<Price> newPrices);
}
