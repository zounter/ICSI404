//remove this import when adding this class to your code

public class TestConverter {
    public static void fromInt(int value, Word32 result)
    {
        Word32 oneWord = new Word32();
        //checking if value is negative
        if(value > 0) {
            for (int counter = 0; counter < 32; counter++)
            {
                //checking if value is greater than or equal to binary digit's base 10 value
                if (value >= Math.pow(2, 31 - counter))
                {
                    //subtract digit's base 10 value from value
                    result.setBitN(counter, new Bit(true));
                    value -= Math.pow(2, 31 - counter);
                }
                else
                {
                    result.setBitN(counter, new Bit(false));
                }
            }
        }
        else
        {
            //setting value to positive
            value *= -1;
            //applying positive case in reverse order
            for (int counter = 0; counter < 32; counter++) {
                if (value >= Math.pow(2, 31 - counter)) {
                    result.setBitN(counter, new Bit(false));
                    value -= Math.pow(2, 31 - counter);
                } else {
                    result.setBitN(counter, new Bit(true));
                }
            }
            //adding binary 1
            fromInt(1, oneWord);
            Adder.add(result, oneWord, result);
        }
    }

    public static int toInt(Word32 value)
    {
        Bit currentBit = new Bit();
        int result = 0;
        value.getBitN(0, currentBit);
        //checking if Word32 is negative
        if(currentBit.getValue() == Bit.boolValues.FALSE) {
            //adding base 10 value of currentBit to result whenever currentBit is true
            for (int counter = 0; counter < 32; counter++) {
                value.getBitN(counter, currentBit);
                if (currentBit.getValue() == Bit.boolValues.TRUE) {
                    result += Math.pow(2, 31 - counter);
                }
            }
        }
        else
        {
            //adding base 10 value of currentBit to result whenever currentBit is false
            for (int counter = 0; counter < 32; counter++) {
                value.getBitN(counter, currentBit);
                if (currentBit.getValue() == Bit.boolValues.FALSE) {
                    result += Math.pow(2, 31 - counter);
                }
            }
            //setting value according to two's complement
            result *= -1;
            result -= 1;
        }
        return result;
    }
}