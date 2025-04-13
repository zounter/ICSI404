
public class Shifter {
    public static void LeftShift(Word32 source, int amount, Word32 result)
    {
        Bit currentBit = new Bit();
        Word32 temp = new Word32();
        for (int counter = 0; counter < 32 - amount; counter++)
        {
            source.getBitN(counter + amount, currentBit);
            temp.setBitN(counter, currentBit);
        }
        temp.copy(result);
    }

    public static void LeftShift(Word32 source, Word32 amount, Word32 result)
    {
        Bit currentBit = new Bit();
        Word32 clear = new Word32();
        Word32 temp = new Word32();
        source.copy(result);
        for (int i = 0; i < 32; i++)
        {
            amount.getBitN(i,currentBit);
            if(currentBit.getValue() == Bit.boolValues.TRUE)
            {
                result.copy(temp);
                clear.copy(result);
                Shifter.LeftShift(temp,(int) Math.pow(2,31 - i),result);
            }
        }
    }

    public static void RightShift(Word32 source, int amount, Word32 result) {
        Bit currentBit = new Bit();
        Word32 temp = new Word32();
        for (int counter = amount; counter < 32; counter++)
        {
            source.getBitN(counter - amount, currentBit);
            temp.setBitN(counter, currentBit);
        }
        temp.copy(result);
    }

    public static void RightShift(Word32 source, Word32 amount, Word32 result)
    {
        Bit currentBit = new Bit();
        Word32 clear = new Word32();
        Word32 temp = new Word32();
        source.copy(result);
        for (int i = 31; i > -1; i--)
        {
            amount.getBitN(i,currentBit);
            if(currentBit.getValue() == Bit.boolValues.TRUE)
            {
                result.copy(temp);
                clear.copy(result);
                Shifter.RightShift(temp,(int) Math.pow(2, 31- i),result);
            }
        }
    }
}
