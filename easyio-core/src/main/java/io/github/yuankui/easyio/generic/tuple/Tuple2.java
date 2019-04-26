package io.github.yuankui.easyio.generic.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuple2<E1, E2> {
    private E1 e1;
    private E2 e2;
}
