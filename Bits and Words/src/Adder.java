public class Adder {
    public static void subtract(Word32 a, Word32 b, Word32 result)
    {
        //converting the second Word32 into its negative version and adding to a
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
        int bitsOn = 0;

        for (int counter = 31; counter > -1; counter--) {
            //finding amount of bits that are true
            a.getBitN(counter, bit1);
            b.getBitN(counter, bit2);
            if (bit1.getValue() == Bit.boolValues.TRUE) bitsOn++;
            if (bit2.getValue() == Bit.boolValues.TRUE) bitsOn++;
            if (remainder.getValue() == Bit.boolValues.TRUE) bitsOn++;
            //setting remainder and product according to the # of bits that are true
            if(bitsOn == 3)
            {
                remainder.assign(Bit.boolValues.TRUE);
                result.setBitN(counter, new Bit(true));
            }
            else if(bitsOn == 2)
            {
                remainder.assign(Bit.boolValues.TRUE);
                result.setBitN(counter, new Bit(false));
            }
            else if (bitsOn == 1)
            {
                remainder.assign(Bit.boolValues.FALSE);
                result.setBitN(counter, new Bit(true));
            }
            else
            {
                remainder.assign(Bit.boolValues.FALSE);
                result.setBitN(counter, new Bit(false));
            }
            //reseting bits
            bitsOn = 0;
        }
    }
}
