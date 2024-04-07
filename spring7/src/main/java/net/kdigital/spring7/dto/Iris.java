package net.kdigital.spring7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Iris {
    private Double sepalLength;
    private Double sepalWidth;
    private Double petalLength;
    private Double petalWidth;
}
