package immutable;

import java.util.Objects;

public class ImmutableExample {
    private final int primitive;
    private final String string;
    private final int[] primitiveArray;
    private final NastyClass nastyClass;
    private final NastyClass[] nastyClassArray;

    public ImmutableExample(final int primitive,
                            final String string,
                            final int[] primitiveArray,
                            final NastyClass nastyClass,
                            final NastyClass[] nastyClassArray) {
        this.primitive = primitive;
        this.string = string;
        if (Objects.isNull(primitiveArray)) {
            this.primitiveArray = null;
        } else {
            this.primitiveArray = primitiveArray.clone();
        }
        this.nastyClass = new NastyClass(nastyClass);
        if (Objects.isNull(nastyClassArray)) {
            this.nastyClassArray = null;
        } else {
            this.nastyClassArray = new NastyClass[nastyClassArray.length];
            for (int counter = 0; counter < this.nastyClassArray.length; counter++) {
                this.nastyClassArray[counter] = new NastyClass(nastyClassArray[counter]);
            }
        }
    }

    public int getPrimitive() {
        return primitive;
    }

    public String getString() {
        return string;
    }

    public int[] getPrimitiveArray() {
        int[] array = null;
        if (Objects.nonNull(primitiveArray)) {
            array = primitiveArray.clone();
        }
        return array;
    }

    public NastyClass getNastyClass() {
        return new NastyClass(nastyClass);
    }

    public NastyClass[] getNastyClassArray() {
        NastyClass[] array = null;
        if (Objects.nonNull(nastyClassArray)) {
            array = new NastyClass[nastyClassArray.length];
            for (int counter = 0; counter < this.nastyClassArray.length; counter++) {
                array[counter] = new NastyClass(this.nastyClassArray[counter]);
            }
        }
        return nastyClassArray;
    }
}
