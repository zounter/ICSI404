public class Multiplier {
    public static void multiply(Word32 a, Word32 b, Word32 result) {
        Word32 shifted = new Word32();
        Bit checkAdd = new Bit();
        for (int counter = 31; counter > -1; counter--)
        {
            b.getBitN(counter, checkAdd);
            if(checkAdd.getValue() == Bit.boolValues.TRUE)
            {
                Shifter.LeftShift(a, 31 - counter , shifted);
                Adder.add(result, shifted, result);
            }
            shifted = new Word32();
        }
    }
}
