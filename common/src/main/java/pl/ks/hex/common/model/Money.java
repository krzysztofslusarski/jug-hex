package pl.ks.hex.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
public class Money implements Comparable<Money>, Serializable {
    private final BigDecimal valuePln;

    public Money(BigDecimal valuePln) {
        this.valuePln = valuePln;
    }

    public Money(String  valuePln) {
        this.valuePln = new BigDecimal(valuePln);
    }

    public static Money of(BigDecimal valuePln) {
        return new Money(valuePln);
    }

    @Override
    public int compareTo(Money other) {
        return valuePln.compareTo(other.valuePln);
    }

    public Money add(Money money) {
        return Money.of(money.valuePln.add(valuePln));
    }

    public Money multiply(BigDecimal multiplier) {
        return Money.of(valuePln.multiply(multiplier));
    }

    public Money multiply(Integer multiplier) {
        return Money.of(valuePln.multiply(BigDecimal.valueOf(multiplier)));
    }
}
