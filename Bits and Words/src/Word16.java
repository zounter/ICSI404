import java.util.Arrays;

public class Word16 {
    public Word16()
    {
        //fills blank BitArray with false Bits if no input is given
        for(int counter = 0; counter < 16; counter++)
        {
            this.BitArray[counter] = new Bit();
        }
    }

    private Bit[] BitArray = new Bit[16];

    public Word16(Bit[] in)
    {
        this.BitArray = in;
    }

    public void copy(Word16 result)
    {
        // sets the values in "result" to be the same as the values in this instance; use "bit.assign"
        for(int counter = 0; counter < result.BitArray.length; counter++)
        {
            result.BitArray[counter].assign(this.BitArray[counter].getValue());
        }
    }

    public void setBitN(int n, Bit source)
    {
        // sets the nth bit of this word to "source"
        this.BitArray[n].assign(source.getValue());
    }

    public void getBitN(int n, Bit result)
    {
        // sets result to be the same value as the nth bit of this word
        result.assign(this.BitArray[n].getValue());
    }

    public boolean equals(Word16 other)
    {
        return this.equals(this, other);
    }

    public static boolean equals(Word16 a, Word16 b)
    {
        // checks if each bit within the bit array is the same value for the same index
        int counter = 0;
        while (counter < a.BitArray.length)
        {
            if(a.BitArray[counter].getValue() != b.BitArray[counter].getValue()) return false;
            counter++;
        }
        return true;
    }

    public void and(Word16 other, Word16 result)
    {
        this.and(this, other, result);
    }

    public static void and(Word16 a, Word16 b, Word16 result)
    {
        // uses and comparison for each index of a and b to form a new Array
        for(int counter = 0; counter < 16; counter++)
        {
            Bit.and(a.BitArray[counter],b.BitArray[counter],result.BitArray[counter]);
        }
    }

    public void or(Word16 other, Word16 result)
    {
        or(this, other, result);
    }

    public static void or(Word16 a, Word16 b, Word16 result)
    {
        //uses or comparison for each index of a and b to form a new Array
        for(int counter = 0; counter < 16; counter++)
        {
            Bit.or(a.BitArray[counter],b.BitArray[counter],result.BitArray[counter]);
        }
    }

    public void xor(Word16 other, Word16 result)
    {
        this.xor(this, other, result);
    }

    public static void xor(Word16 a, Word16 b, Word16 result)
    {
        //uses xor comparison for each index of a and b to form a new Array
        for(int counter = 0; counter < 16; counter++)
        {
            Bit.xor(a.BitArray[counter],b.BitArray[counter],result.BitArray[counter]);
        }
    }

    public void not( Word16 result)
    {
        this.not(this, result);
    }

    public static void not(Word16 a, Word16 result)
    {
        // nots each index of word a creating a new Array
        for(int counter = 0; counter < 16; counter++)
        {
            Bit.not(a.BitArray[counter], result.BitArray[counter]);
        }
    }

    public String toString()
    {
        //outputs word as a string in the format desired
        StringBuilder sb = new StringBuilder();
        for(Bit currentBit : this.BitArray)
        {
            sb.append(currentBit.toString());
            sb.append(",");
        }
        return sb.toString();
    }


}
