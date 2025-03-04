import com.sun.jdi.BooleanValue;


public class ALU {
    public Word16 instruction = new Word16();
    public Word32 op1 = new Word32();
    public Word32 op2 = new Word32();
    public Word32 result = new Word32();
    public Bit less = new Bit(false);
    public Bit equal = new Bit(false);
    private final Bit temp = new Bit();

    public void doInstruction()
    {
        result = new Word32();
        less.assign(Bit.boolValues.FALSE);
        equal.assign(Bit.boolValues.FALSE);
        instruction.getBitN(0, temp);
        if(temp.getValue() == Bit.boolValues.TRUE)
        {
            return;
        }
        instruction.getBitN(1, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            opCompare();
            return;
        }
        instruction.getBitN(2, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            ThirdBitTrue();
            return;
        }
        instruction.getBitN(3, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            FourthBitTrue();
            return;
        }
        instruction.getBitN(4, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            Adder.add(op1,op2,result);
        }
    }

    public void ThirdBitTrue()
    {
        instruction.getBitN(3, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            ThirdFourthTrue();
            return;
        }
        instruction.getBitN(4, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            Adder.subtract(op1,op2,result);
        }
        if (temp.getValue() == Bit.boolValues.FALSE)
        {
            Shifter.LeftShift(op1,TestConverter.toInt(op2),result);
        }
    }

    public void ThirdFourthTrue()
    {
        instruction.getBitN(4, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            Shifter.RightShift(op1, TestConverter.toInt(op2), result);
        }
        if (temp.getValue() == Bit.boolValues.FALSE)
        {
            Word32.or(op1,op2,result);
        }
    }

    public void FourthBitTrue()
    {
        instruction.getBitN(4, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            Multiplier.multiply(op1,op2,result);
        }
        if (temp.getValue() == Bit.boolValues.FALSE)
        {
            Word32.and(op1,op2,result);
        }
    }

    public void opCompare()
    {
        //checking if the rest of the word matches compare
        instruction.getBitN(2, temp);
        if(temp.getValue() == Bit.boolValues.TRUE) return;
        instruction.getBitN(3, temp);
        if(temp.getValue() == Bit.boolValues.FALSE) return;
        instruction.getBitN(4, temp);
        if(temp.getValue() == Bit.boolValues.FALSE) return;
        Word32 subResult = new Word32();
        if(Word32.equals(op1, op2))
        {
            equal.assign(Bit.boolValues.TRUE);
        }
        Adder.subtract(op1, op2, subResult);
        subResult.getBitN(0, temp);
        if(temp.getValue() == Bit.boolValues.TRUE)
        {
            less.assign(Bit.boolValues.TRUE);
        }
    }
}
