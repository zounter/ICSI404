public class Bit {
    public enum boolValues { FALSE, TRUE }

    public Bit(boolean value) {

    }

    public boolValues getValue()
    {
        return boolValues.FALSE;
    }

    public void assign(boolValues value)
    {

    }

    public void and(Bit b2, Bit result)
    {

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

    public void xor(Bit b2, Bit result) {
    }

    public static void xor(Bit b1, Bit b2, Bit result)
    {
        and(b1, b2, result);
        if(result.getValue() == boolValues.TRUE)
        {
            not(result, result);
        }
        else
        {

        }
    }

    public static void not(Bit b2, Bit result) {
    }

    public void not(Bit result) {
    }

    public String toString() {
        return "t";
    }






}
