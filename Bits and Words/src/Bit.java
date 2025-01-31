public class Bit {
    public enum boolValues { FALSE, TRUE }
    boolValues BitValue;

    public Bit(boolean value) {
        if(value)
        {
            this.BitValue = boolValues.TRUE;
        }
        else
        {
            this.BitValue = boolValues.FALSE;
        }
    }

    public boolValues getValue()
    {
        return this.BitValue;
    }

    public void assign(boolValues value)
    {
        this.BitValue = value;
    }

    public void and(Bit b2, Bit result)
    {
        if (this.getValue() == boolValues.FALSE)
        {
            result.assign(boolValues.FALSE);
        }
        else if (b2.getValue() == boolValues.FALSE)
        {
            result.assign(boolValues.FALSE);
        }
        else
        {
            result.assign(boolValues.TRUE);
        }
    }

    public static void and(Bit b1, Bit b2, Bit result)
    {

        if (b1.getValue() == boolValues.FALSE)
        {
            result.assign(boolValues.FALSE);
        }
        else if (b2.getValue() == boolValues.FALSE)
        {
            result.assign(boolValues.FALSE);
        }
        else
        {
            result.assign(boolValues.TRUE);
        }
    }

    public void or(Bit b2, Bit result)
    {
        if (this.getValue() == boolValues.TRUE)
        {
            result.assign(boolValues.TRUE);
        }
        else if (b2.getValue() == boolValues.TRUE)
        {
            result.assign(boolValues.TRUE);
        }
        else
        {
            result.assign(boolValues.FALSE);
        }
    }

    public static void or(Bit b1, Bit b2, Bit result)
    {
        if (b1.getValue() == boolValues.TRUE)
        {
            result.assign(boolValues.TRUE);
        }
        else if (b2.getValue() == boolValues.TRUE)
        {
            result.assign(boolValues.TRUE);
        }
        else
        {
            result.assign(boolValues.FALSE);
        }
    }

    public void xor(Bit b2, Bit result)
    {
        or(b2, result);
        if(result.getValue() == boolValues.FALSE)
        {
            return;
        }
        and(b2, result);
        result.not(result);
    }

    public static void xor(Bit b1, Bit b2, Bit result)
    {
        or(b1, b2, result);
        if(result.getValue() == boolValues.FALSE)
        {
         return;
        }
        and(b1, b2, result);
        result.not(result);
    }

    public static void not(Bit b2, Bit result)
    {
        if(b2.BitValue == boolValues.TRUE)
        {
            result.assign(boolValues.FALSE);
        }
        else
        {
            result.assign(boolValues.TRUE);
        }
    }

    public void not(Bit result)
    {
        if(this.BitValue == boolValues.TRUE)
        {
            result.assign(boolValues.FALSE);
        }
        else
        {
            result.assign(boolValues.TRUE);
        }
    }

    public String toString() {
        if(this.BitValue == boolValues.TRUE)
        {
            return "t";
        }
        return "f";
    }






}
