public class Bit {
    public enum boolValues { FALSE, TRUE }
    private boolValues BitValue;

    public Bit()
    {
        //if no value is given it is automatically set to false
        this.BitValue = boolValues.FALSE;
    }

    public Bit(boolean value) {
        //setting bit's boolValue
        if(value) this.BitValue = boolValues.TRUE;
        else this.BitValue = boolValues.FALSE;
    }

    public boolValues getValue()
    {
        return this.BitValue;
    }

    public void assign(boolValues value)
    {
        if(value == boolValues.TRUE)
        {
            this.BitValue = boolValues.TRUE;
        }
        else
        {
            this.BitValue = boolValues.FALSE;
        }
    }

    public void and(Bit b2, Bit result)
    {
            and(this, b2, result);
    }

    public static void and(Bit b1, Bit b2, Bit result)
    {
        //will only set result to true if both enums fail their is false checks
        if (b1.getValue() == boolValues.FALSE) result.assign(boolValues.FALSE);
        else if (b2.getValue() == boolValues.FALSE) result.assign(boolValues.FALSE);
        else result.assign(boolValues.TRUE);
    }

    public void or(Bit b2, Bit result)
    {
      or(this, b2, result);
    }

    public static void or(Bit b1, Bit b2, Bit result)
    {
        //will only set result to false if both enums fail their is true checks
        if (b1.getValue() == boolValues.TRUE) result.assign(boolValues.TRUE);
        else if (b2.getValue() == boolValues.TRUE) result.assign(boolValues.TRUE);
        else result.assign(boolValues.FALSE);
    }

    public void xor(Bit b2, Bit result)
    {
        xor(this, b2, result);
    }

    public static void xor(Bit b1, Bit b2, Bit result)
    {
        //will only set result to false if b1 and b2 are different values
        if(b1.getValue() != b2.getValue()) result.assign(boolValues.TRUE);
        else result.assign(boolValues.FALSE);
    }

    public static void not(Bit b2, Bit result)
    {
        // uses if/else statement to swap result Bit's boolValue
        if(b2.BitValue == boolValues.TRUE)  result.assign(boolValues.FALSE);
        else result.assign(boolValues.TRUE);
    }

    public void not(Bit result)
    {
      not(this, result);
    }

    public String toString() {
        // prints either a t or an f depending on the Bit's boolValue
        if(this.BitValue == boolValues.TRUE) return "t";
        return "f";
    }
}