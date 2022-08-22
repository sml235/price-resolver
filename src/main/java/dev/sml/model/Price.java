package dev.sml.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class Price {
    private long id; // идентификатор в БД
    private String productCode; // код товара
    private int number; // номер цены
    private int depart; // номер отдела
    private Date begin; // начало действия
    private Date end; // конец действия
    private long value; // значение цены в копейках

    public Price(Price price) {
        this.productCode = price.getProductCode();
        this.number = price.getNumber();
        this.depart = price.getDepart();
        this.begin = price.getBegin();
        this.end = price.getEnd();
        this.value = price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return number == price.number &&
                depart == price.depart &&
                value == price.value &&
                productCode.equals(price.productCode) &&
                begin.equals(price.begin) &&
                end.equals(price.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, number, depart, begin, end, value);
    }

}

