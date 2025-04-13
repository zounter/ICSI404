//remove this import when adding this class to your code

public class Multiplier {
    public static void multiply(Word32 a, Word32 b, Word32 result) {
        Word32 shifted = new Word32();

        Bit checkAdd = new Bit();
        for (int counter = 31; counter > -1; counter--)
        {
            //checking if current bit in b is true
            b.getBitN(counter, checkAdd);
            if(checkAdd.getValue() == Bit.boolValues.TRUE)
            {
                //shifting Word a by the current place and adding the sum to the result
                Shifter.LeftShift(a, 31 - counter , shifted);
                Adder.add(result, shifted, result);
            }
            shifted = new Word32();
        }
    }
}
