package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        BigDecimal valor = BigDecimal.ZERO;
        for (Field field : fields) {
            if (field.getType().equals(BigDecimal.class) && field.isAnnotationPresent(Somar.class)) {
                BigDecimal value = getValue(field, obj);
                valor = valor.add(value);
            }
        }
        return valor;
    }

    @Override
    public BigDecimal subtrair(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        BigDecimal acc = BigDecimal.ZERO;
        for (Field field : fields) {
            if (field.getType().equals(BigDecimal.class) && field.isAnnotationPresent(Subtrair.class)) {
                BigDecimal value = getValue(field, obj);
                acc = acc.add(value);
            }
        }
        return acc;
    }

    @Override
    public BigDecimal totalizar(Object obj) {
        return somar(obj).subtract(subtrair(obj));
    }

    private BigDecimal getValue(Field field, Object obj) {
        field.setAccessible(true);
        try {
            return new BigDecimal(String.valueOf(field.get(obj))) ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}