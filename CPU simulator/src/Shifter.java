//remove this import when adding this class to your code

public class Shifter {
    public static void LeftShift(Word32 source, int amount, Word32 result)
    {
        Bit currentBit = new Bit();
        for (int counter = 0; counter < 32 - amount; counter++)
        {
            source.getBitN(counter + amount, currentBit);
            result.setBitN(counter, currentBit);
        }
    }

    public static void RightShift(Word32 source, int amount, Word32 result) {
        Bit currentBit = new Bit();
        for (int counter = amount; counter < 32; counter++)
        {
            source.getBitN(counter - amount, currentBit);
            result.setBitN(counter, currentBit);
        }
    }
}
