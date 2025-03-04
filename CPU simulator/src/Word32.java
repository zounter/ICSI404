public class Word32 {
    public Word32()
    {
        //fills array with false bits if no input is given
        for(int counter = 0; counter < 32; counter++)
        {
            this.BitArray32[counter] = new Bit();
        }
    }

    private Bit[] BitArray32 = new Bit[32];

    public Word32(Bit[] in)
    {
        this.BitArray32 = in;
    }

    public void getTopHalf(Word16 result)
    {
        // sets result = bits 0-15 of this word. use bit.assign
        for (int counter = 0; counter < 16; counter++)
        {
            result.setBitN(counter, BitArray32[counter]);
        }
    }

    public void getBottomHalf(Word16 result)
    {
        // sets result = bits 16-31 of this word. use bit.assign
        for (int counter = 16; counter < 32; counter++)
        {
            result.setBitN(counter - 16, BitArray32[counter]);
        }
    }

    public void copy(Word32 result)
    {
        // sets result's bit to be the same as this. use bit.assign
        int counter = 0;
        for (Bit currentBit : this.BitArray32)
        {
            result.BitArray32[counter].assign(currentBit.getValue());
            counter++;
        }
    }

    public boolean equals(Word32 other)
    {
        return this.equals(this, other);
    }

    public static boolean equals(Word32 a, Word32 b)
    {
        int counter = 0;
        while (counter < a.BitArray32.length)
        {
            if(a.BitArray32[counter].getValue() != b.BitArray32[counter].getValue()) return false;
            counter++;
        }
        return true;
    }

    public void getBitN(int n, Bit result)
    {
        result.assign(this.BitArray32[n].getValue());
    }

    public void setBitN(int n, Bit source)
    {
        //  use bit.assign
        this.BitArray32[n].assign(source.getValue());
    }

    public void and(Word32 other, Word32 result)
    {
        this.and(this, other, result);
    }

    public static void and(Word32 a, Word32 b, Word32 result)
    {
        for (int counter = 0; counter < a.BitArray32.length; counter++)
        {
            Bit.and(a.BitArray32[counter], b.BitArray32[counter], result.BitArray32[counter]);
        }
    }

    public void or(Word32 other, Word32 result)
    {
        this.or(this, other, result);
    }

    public static void or(Word32 a, Word32 b, Word32 result)
    {
        for (int counter = 0; counter < a.BitArray32.length; counter++)
        {
            Bit.or(a.BitArray32[counter], b.BitArray32[counter], result.BitArray32[counter]);
        }
    }

    public void xor(Word32 other, Word32 result)
    {
        this.xor(this, other, result);
    }

    public static void xor(Word32 a, Word32 b, Word32 result)
    {
        for (int counter = 0; counter < a.BitArray32.length; counter++)
        {
            Bit.xor(a.BitArray32[counter], b.BitArray32[counter], result.BitArray32[counter]);
        }
    }

    public void not( Word32 result)
    {
        this.not(this, result);
    }

    public static void not(Word32 a, Word32 result)
    {
        for (int counter = 0; counter < a.BitArray32.length; counter++)
        {
            Bit.not(a.BitArray32[counter], result.BitArray32[counter]);
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Bit currentBit : this.BitArray32)
        {
            sb.append(currentBit.toString());
            sb.append(",");
        }
        return sb.toString();
    }

    public void fromString(String word)
    {
        //takes a stream of data and converts it to a word32
        //will only convert the first 32 chars into word32 all other chars are not added
        word.replace(",","");
        int counter = 0;
        for (char sbit : word.toCharArray())
        {
            if (sbit == 't' && counter < 32)
            {
                this.BitArray32[counter].assign(Bit.boolValues.TRUE);
            }
            else if (counter < 32)
            {
                this.BitArray32[counter].assign(Bit.boolValues.FALSE);
            }
            counter++;
        }
    }

}