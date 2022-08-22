package dev.sml.service;

import dev.sml.model.Price;

import java.util.*;
import java.util.stream.Collectors;

public class PriceResolverImpl implements PriceResolver {

    @Override
    public Collection<Price> mergePrices(Collection<Price> currentPrices, Collection<Price> newPrices) {
        List<Price> result = new ArrayList<>(currentPrices);
        newPrices.forEach(price -> mergePrice(result, price));
        return new HashSet<>(result);
    }

    private void mergePrice(Collection<Price> currentPrices, Price newPrice) {
        List<Price> coveredPrices = currentPrices.stream()
                .filter(price -> isNeedToMerge(price, newPrice))
                .collect(Collectors.toList());
        if (coveredPrices.isEmpty()) {
            currentPrices.add(newPrice);
        } else {
            coveredPrices.forEach(oldPrice -> replacePrice(currentPrices, oldPrice, newPrice));
            unionEqualPrices(currentPrices, newPrice);
        }
    }

    private void replacePrice(Collection<Price> currentPrices, Price oldPrice, Price newPrice) {
        currentPrices.remove(oldPrice);
        if (oldPrice.getBegin().before(newPrice.getBegin())) {
            Price firstPart = new Price(oldPrice);
            firstPart.setEnd(newPrice.getBegin());
            currentPrices.add(firstPart);
        }
        currentPrices.add(newPrice);
        if (oldPrice.getEnd().after(newPrice.getEnd())) {
            Price secondPart = new Price(oldPrice);
            secondPart.setBegin(newPrice.getEnd());
            currentPrices.add(secondPart);
        }
    }

    private void unionEqualPrices(Collection<Price> currentPrices, Price newPrice) {
        List<Price> pricesToExpand = currentPrices.stream()
                .filter(price -> isNeedToMerge(price, newPrice) && isEqualValues(price, newPrice))
                .collect(Collectors.toList());
        Optional<Price> optionalPrice = pricesToExpand.stream()
                .reduce(this::expandIntervals);
        currentPrices.removeAll(pricesToExpand);
        optionalPrice.ifPresent(currentPrices::add);
    }

    private Price expandIntervals(Price accumulator, Price price) {
        Date begin = accumulator.getBegin();
        Date end = accumulator.getEnd();
        if (price.getBegin().before(begin)) {
            begin = price.getBegin();
        }
        if (price.getEnd().after(end)) {
            end = price.getEnd();
        }
        accumulator.setBegin(begin);
        accumulator.setEnd(end);
        return accumulator;
    }

    private boolean isNeedToMerge(Price oldPrice, Price newPrice) {
        return oldPrice.getProductCode().equals(newPrice.getProductCode()) &&
                oldPrice.getNumber() == newPrice.getNumber() &&
                oldPrice.getDepart() == newPrice.getDepart() &&
                oldPrice.getBegin().compareTo(newPrice.getEnd()) <= 0 &&
                oldPrice.getEnd().compareTo(newPrice.getBegin()) >= 0;
    }

    private boolean isEqualValues(Price oldPrice, Price newPrice) {
        return oldPrice.getValue() == newPrice.getValue();
    }


}
