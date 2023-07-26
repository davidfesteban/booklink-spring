package dev.misei.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionResult<T> {
    private boolean result;
    private String message;
    private T returnable;

    public static <K> ActionResult<K> TRUE(K returnable) {
        return new ActionResult<K>(true, null, returnable);
    }

    public static <K> ActionResult<K> TRUE() {
        return new ActionResult<>(true, "Successful operation!", null);
    }

    public static <K> ActionResult<K> FALSE(String message) {
        return new ActionResult<>(false, message, null);
    }
}
