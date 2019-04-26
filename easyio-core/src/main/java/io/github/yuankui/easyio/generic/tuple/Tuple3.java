package io.github.yuankui.easyio.generic.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuple3<E1, E2, E3> {
    private E1 e1;
    private E2 e2;
    private E3 e3;
}
