//remove this import when implamenting your code

public class Adder {
    public static void subtract(Word32 a, Word32 b, Word32 result)
    {
        //converting the second Word32 into its negative version using two's complement and adding to a
        Word32 one = new Word32();
        one.setBitN(31, new Bit(true));
        b.not(b);
        add(one,b,b);
        add(a,b,result);
    }

    public static void add(Word32 a, Word32 b, Word32 result)
    {
        //initializiing local bits
        Bit bit1 = new Bit();
        Bit bit2 = new Bit();
        Bit remainder = new Bit();
        Bit product = new Bit();

        //the big boy
        //takes in the two bits and remainder runs a long if else block based upon an algorithm for finding the correct answer
        for (int counter = 31; counter > -1; counter--) {
            a.getBitN(counter, bit1);
            b.getBitN(counter, bit2);
            Bit.and(bit1, bit2, product);
            Bit.and(remainder, product, product);
            //handles case where all 3 are true
            if(product.getValue() == Bit.boolValues.TRUE)
            {
                remainder.assign(Bit.boolValues.TRUE);
                result.setBitN(counter, new Bit(true));
                continue;
            }
            //handles cases where remainder is true but one of the added bits isn't
            Bit.xor(bit1, bit2, product);
            Bit.and(product, remainder, product);
            if(product.getValue() == Bit.boolValues.TRUE)
            {
                remainder.assign(Bit.boolValues.TRUE);
                result.setBitN(counter, new Bit(false));
                continue;
            }
            //handles cases where at most one of the bits is true
            Bit.xor(bit1, bit2, product);
            if(product.getValue() == Bit.boolValues.TRUE)
            {
                remainder.assign(Bit.boolValues.FALSE);
                result.setBitN(counter, new Bit(true));
                continue;
            }
            //handles case where both of the bits are true
            Bit.and(bit1, bit2, product);
            if (product.getValue() == Bit.boolValues.TRUE)
            {
                remainder.assign(Bit.boolValues.TRUE);
                result.setBitN(counter, new Bit(false));
                continue;
            }
            //handles case where the remainder is true
            if(remainder.getValue() == Bit.boolValues.TRUE)
            {
                remainder.assign(Bit.boolValues.FALSE);
                result.setBitN(counter, new Bit(true));
                continue;
            }
            //defaults to all bits being false
            remainder.assign(Bit.boolValues.FALSE);
            result.setBitN(counter, new Bit(false));
        }
    }
}
