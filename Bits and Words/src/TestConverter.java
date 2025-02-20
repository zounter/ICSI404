

public class TestConverter {
    public static void fromInt(int value, Word32 result)
    {
        Word32 oneWord = new Word32();
        if(value > 0) {
            for (int counter = 0; counter < 32; counter++) {
                if (value >= Math.pow(2, 31 - counter)) {
                    result.setBitN(counter, new Bit(true));
                    value -= Math.pow(2, 31 - counter);
                } else {
                    result.setBitN(counter, new Bit(false));
                }
            }
        }
        else
        {
            value *= -1;
            for (int counter = 0; counter < 32; counter++) {
                if (value >= Math.pow(2, 31 - counter)) {
                    result.setBitN(counter, new Bit(false));
                    value -= Math.pow(2, 31 - counter);
                } else {
                    result.setBitN(counter, new Bit(true));
                }
            }
            fromInt(1, oneWord);
            Adder.add(result, oneWord, result);
        }
    }

    public static int toInt(Word32 value)
    {
        Bit curBit = new Bit();
        int result = 0;
        value.getBitN(0, curBit);
        if(curBit.getValue() == Bit.boolValues.FALSE) {
            for (int counter = 0; counter < 32; counter++) {
                value.getBitN(counter, curBit);
                if (curBit.getValue() == Bit.boolValues.TRUE) {
                    result += Math.pow(2, 31 - counter);
                }
            }
        }
        else
        {
            for (int counter = 0; counter < 32; counter++) {
                value.getBitN(counter, curBit);
                if (curBit.getValue() == Bit.boolValues.FALSE) {
                    result += Math.pow(2, 31 - counter);
                }
            }
            result *= -1;
            result -= 1;
        }
        return result;
    }
}