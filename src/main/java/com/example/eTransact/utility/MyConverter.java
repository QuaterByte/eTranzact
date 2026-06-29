package com.example.eTransact.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.StringTokenizer;

@Converter
public class MyConverter implements AttributeConverter<Money, String> {
    @Override
    public String convertToDatabaseColumn(Money money) {
        return money.toString();
    }

    @Override
    public Money convertToEntityAttribute(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string);
        String code = tokenizer.nextToken();
        return Money.of(new BigDecimal(tokenizer.nextToken()), code);
    }
}
