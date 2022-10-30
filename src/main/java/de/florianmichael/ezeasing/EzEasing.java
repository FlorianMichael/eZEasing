package de.florianmichael.ezeasing;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

// https://easings.net/
public enum EzEasing {

    LINEAR("Linear", x -> x),

    IN_SINE("In sine", x -> (float) (1 - Math.cos(x * Math.PI) / 2F)),
    OUT_SINE("Out sine", x -> (float) (1 - Math.sin(x * Math.PI) / 2F)),
    IN_OUT_SINE("In out sine", x -> (float) (-(Math.cos(Math.PI * x) - 1) / 2F)),

    IN_QUAD("In quad", x -> x * x),
    OUT_QUAD("Out quad", x -> 1 - (1 - x) * (1 - x)),
    IN_OUT_QUAD("In out quad", x -> x < 0.5 ? 2 * x * x : 1 - (float) Math.pow(-2 * x + 2, 2) / 2F),

    IN_CUBIC("In cubic", x -> (float) Math.pow(x, 3)),
    OUT_CUBIC("Out cubic", x -> (float) (1 - Math.pow(1 - x, 3))),
    IN_OUT_CUBIC("In out cubic", x -> x < 0.5 ? 4 * x * x * x : 1 - (float) Math.pow(-2 * x + 2, 3) / 2F),

    IN_QUART("In quart", x -> (float) Math.pow(x, 4)),
    OUT_QUART("Out quart", x -> (float) (1 - Math.pow(1 - x, 4))),
    IN_OUT_QUART("In out quart", x -> x < 0.5 ? 8 * x * x * x * x : 1 - (float) Math.pow(-2 * x + 2, 4) / 2),

    IN_QUINT("In quint", x -> (float) Math.pow(x, 5)),
    OUT_QUINT("Out quint", x -> (float) (1 - Math.pow(1 - x, 5))),
    IN_OUT_QUINT("In out quint", x -> x < 0.5 ? 16 * x * x * x * x * x : 1 - (float) Math.pow(-2 * x + 2, 5) / 2F),

    IN_EXPO("In expo", x -> x == 0 ? 0 : (float) Math.pow(2, 10 * x - 10)),
    OUT_EXPO("Out expo", x -> x == 1 ? 1 : (float) (1 - Math.pow(2, -10 * x))),
    IN_OUT_EXPO("In out expo", x -> x == 0 ? 0 : (float) (x == 1 ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2)),

    IN_CIRC("In circ", x -> (float) (1 - Math.sqrt(1 - Math.pow(x, 2)))),
    OUT_CIRC("Out circ", x -> (float) Math.sqrt(1 - Math.pow(x - 1, 2))),
    IN_OUT_CIRC("In out circ", x -> (float) (x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2)),

    IN_BACK("In back", x -> {
        final float c1 = 1.70158F;
        final float c3 = c1 + 1;

        return c3 * x * x * x - c1 * x * x;
    }),
    OUT_BACK("Out back", x -> {
        final float c1 = 1.70158F;
        final float c3 = c1 + 1;

        return 1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2);
    }),
    IN_OUT_BACK("In out back", x -> {
        final float c1 = 1.70158F;
        final float c2 = c1 * 1.525F;

        return x < 0.5 ? ((float) Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : ((float) Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
    }),

    IN_ELASTIC("In elastic", x -> {
        final float c4 = (2f * (float) Math.PI) / 3F;

        return x == 0 ? 0 : (float) (x == 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
    }),
    OUT_ELASTIC("Out elastic", x -> {
        final float c4 = (2f * (float) Math.PI) / 3F;

        return x == 0 ? 0 : (float) (x == 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1);
    }),
    INT_OUT_ELASTIC("In out elastic", x -> {
        final double c5 = (2 * Math.PI) / 4.5;
        final double sin = Math.sin((20 * x - 11.125) * c5);

        return x == 0 ? 0 : (float) (x == 1 ? 1 : x < 0.5 ? -(Math.pow(2, 20 * x - 10) * sin) / 2 : (Math.pow(2, -20 * x + 10) * sin) / 2 + 1);
    }),

    OUT_BOUNCE("Out bounce", x -> {
        final float n1 = 7.5625F;
        final float d1 = 2.75F;

        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5F / d1) * x + 0.75F;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25F / d1) * x + 0.9375F;
        } else {
            return n1 * (x -= 2.625F / d1) * x + 0.984375F;
        }
    }),
    IN_BOUNCE("In bounce", x -> 1 - EzEasing.OUT_BOUNCE.ease(1 - x)),
    IN_OUT_BOUNCE("In out bounce", x -> x < 0.5 ? (1 - EzEasing.OUT_BOUNCE.ease(1 - 2 * x)) / 2 : (1 + EzEasing.OUT_BOUNCE.ease(2 * x - 1)) / 2);

    public final String name;
    public final Function<Float, Float> function;

    EzEasing(final String name, final Function<Float, Float> function) {
        this.name = name;
        this.function = function;
    }

    public static List<String> functionNames() {
        return Arrays.stream(values()).map(v -> v.name).collect(Collectors.toList());
    }

    public static float ease(final String functionName, final float x) {
        return byName(functionName).ease(x);
    }

    public static EzEasing byName(final String functionName) {
        for (EzEasing value : values()) {
            if (value.name.equals(functionName)) {
                return value;
            }
        }
        return null;
    }

    public String upperName() {
        return this.name.toUpperCase(Locale.ROOT);
    }

    public float ease(final float x) {
        return this.function.apply(x);
    }
}
