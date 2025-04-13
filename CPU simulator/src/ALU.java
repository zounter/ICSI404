import com.sun.jdi.BooleanValue;


public class ALU {
    public Word16 instruction = new Word16();
    public Word32 op1 = new Word32();
    public Word32 op2 = new Word32();
    public Word32 result = new Word32();
    public Bit less = new Bit(false);
    public Bit equal = new Bit(false);
    public void doInstruction()
    {
        result = new Word32();
        switch (instruction.toString().replaceAll(",","").substring(0,5))
        {
            case "fffff" -> {}
            case "fffft" -> {Adder.add(op1, op2, result);}
            case "ffftf" -> {Word32.and(op1,op2,result);}
            case "ffftt" -> {Multiplier.multiply(op1,op2,result);}
            case "fftff" -> {Shifter.LeftShift(op1,op2,result);}
            case "fftft" -> {Adder.subtract(op1,op2,result);}
            case "ffttf" -> {Word32.or(op1,op2,result);}
            case "ffttt" -> {Shifter.RightShift(op1,op2,result);}
            case "fttff" -> {
                if (less.getValue().equals(Bit.boolValues.TRUE) || equal.getValue().equals(Bit.boolValues.TRUE))
                op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "fttft" -> {
                if (less.getValue().equals(Bit.boolValues.TRUE))
                    op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "ftttf" -> {
                if (less.getValue().equals(Bit.boolValues.FALSE) || equal.getValue().equals(Bit.boolValues.TRUE))
                    op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "ftttt" -> {
                if (less.getValue().equals(Bit.boolValues.FALSE) && equal.getValue().equals(Bit.boolValues.FALSE))
                    op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "tffff" -> {
                if (equal.getValue().equals(Bit.boolValues.TRUE))
                    op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "tffft" -> {
                if (equal.getValue().equals(Bit.boolValues.FALSE))
                    op1.copy(result);
                else {
                    new Word32().copy(result);
                    result.setBitN(30, new Bit(true));
                }
            }
            case "ftftt" -> {compare();}

        }
    }

    private void compare()
    {
        less.assign(Bit.boolValues.FALSE);
        equal.assign(Bit.boolValues.FALSE);
        Bit temp = new Bit();
        Word32 tempWord = new Word32();
        if (Word32.equals(op1,op2))
        {
            equal.assign(Bit.boolValues.TRUE);
        }
        Adder.subtract(op1,op2,tempWord);
        tempWord.getBitN(0, temp);
        if (temp.getValue() == Bit.boolValues.TRUE)
        {
            less.assign(Bit.boolValues.TRUE);
        }
    }
}
