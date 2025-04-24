import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Processor {
    private Memory mem;
    ALU alu = new ALU();
    private Word32[] registers = new Word32[32];
    private Word32 PC = new Word32();
    private Word16 instruction = new Word16();
    private boolean halted = false;
    private int reg1;
    private int reg2;
    private int container;
    Stack<Word32> instructionStack = new Stack<>();
    public List<String> output = new LinkedList<>();
    private Boolean useL2;
    private Boolean useInstructionCache;
    private int clockCycle;



    public Processor(Memory m) {
        mem = m;
        for (int i = 0; i < registers.length; i++)
        {
            registers[i] = new Word32();
        }
    }

    public void run() {
        //find the end of the program
        Scanner input = new Scanner(System.in);
        useInstructionCache = false;
        useL2 = false;

        Word32 one = new Word32();
        one.setBitN(31, new Bit(true));
        Word32 end = new Word32();
        end.setBitN(21, new Bit(true));
        end.setBitN(22, new Bit(true));
        end.setBitN(23, new Bit(true));
        end.setBitN(24, new Bit(true));
        end.setBitN(25, new Bit(true));
        end.setBitN(27, new Bit(true));
        one.setBitN(30, new Bit(false));
        one.setBitN(31, new Bit(true));
        while (!Word32.equals(PC, end) && !halted) {
            fetch();
            decode();
            execute();
            store();
            if (container != 3 && container != 1) Adder.add(PC, one, PC);
            else Adder.subtract(PC, one, PC);
        }
        System.out.println("Cycles taken for computing: " + clockCycle);
    }


    private void fetch()
    {
        Bit checkEven = new Bit();
        Word32 CurrentDram = new Word32();
        new Word32().copy(alu.op1);
        new Word32().copy(alu.op2);
        PC.copy(CurrentDram);
        PC.getBitN(31, checkEven);
        //divide PC by two
        Shifter.RightShift(CurrentDram, 1, CurrentDram);
        //check if even/odd
        if (checkEven.getValue().equals(Bit.boolValues.TRUE))
        {
            //take right instuction if odd
            CurrentDram.copy(mem.address);
            mem.read();
            mem.value.getBottomHalf(instruction);
        }
        else
        {
            //take left if even
            CurrentDram.copy(mem.address);
            mem.read();
            mem.value.getTopHalf(instruction);
        }
    }

    private void decode() {
        Bit temp = new Bit();
        for (int i = 0; i < 5; i++)
        {
            instruction.getBitN(i, temp);
            alu.instruction.setBitN(i, temp);
        }
        //its a call return
        if(isCallReturn(alu.instruction))
        {
            instruction.getBitN(5, temp);
            if (temp.getValue().equals(Bit.boolValues.TRUE)) {
                for (int i = 0; i < 27; i++) {
                    alu.op1.setBitN(i, temp);
                }
            }
            for (int i = 5; i < 16; i++) {
                instruction.getBitN(i, temp);
                alu.op1.setBitN(i + 16, temp);
            }
        }
        //its a 2R or Immediate
        else
        {
            instruction.getBitN(5, temp);
            //its not a register
            if (temp.getValue() == Bit.boolValues.TRUE) {
                instruction.getBitN(6, temp);
                if (temp.getValue().equals(Bit.boolValues.TRUE)) {
                    for (int i = 0; i < 27; i++) {
                        alu.op1.setBitN(i, temp);
                    }
                }
                for (int i = 6; i < 11; i++) {
                    instruction.getBitN(i, temp);
                    alu.op1.setBitN(i + 21, temp);
                }
                for (int i = 11; i < 16; i++) {
                    instruction.getBitN(i, temp);
                    alu.op2.setBitN(i + 16, temp);
                }
                reg2 = getReg(alu.op2);
                registers[reg2].copy(alu.op2);
            }
            //its a register
            else {
                for (int i = 6; i < 11; i++) {
                    instruction.getBitN(i, temp);
                    alu.op1.setBitN(i + 21, temp);
                }
                reg1 = getReg(alu.op1);
                registers[reg1].copy(alu.op1);
                for (int i = 11; i < 16; i++) {
                    instruction.getBitN(i, temp);
                    alu.op2.setBitN(i + 16, temp);
                }
                reg2 = getReg(alu.op2);
                registers[reg2].copy(alu.op2);
            }
        }
    }



    private void execute() {
        //executing command
        Word32 two = new Word32();
        Word32 result = new Word32();
        two.setBitN(30, new Bit(true));
        switch (instruction.toString().replaceAll(",","").substring(0,5))
        {
                //halt
            case "fffff":
                halted = true;
                container = 7;
                break;
                //basic ops
            case "ffftt":
                clockCycle += 8;
            case "fffft":
            case "ffftf":
            case "fftff":
            case "fftft":
            case "ffttf":
            case "ffttt":
                alu.op1.copy(two);
                alu.op2.copy(alu.op1);
                two.copy(alu.op2);
                alu.doInstruction();
                container = 0;
                break;
                //syscalls
            case "ftfff":
                //check op do print accordingly
                if (alu.op1.equals(new Word32()))
                {
                    printReg();
                }
                else
                {
                    printMem();
                }
                container = 7;
                break;
            case "ftfft":
                //push
                Multiplier.multiply(alu.op1,two, result);
                result.copy(alu.op1);
                container = 1;
                break;
            case "ftftf":
                //pop
                Multiplier.multiply(alu.op1,two, result);
                container = 2;
                break;
            case "ftftt":
                alu.op1.copy(two);
                alu.op2.copy(alu.op1);
                two.copy(alu.op2);
                alu.doInstruction();
                container = 7;
                break;
                //branches
                //return either double their ammount or https://youtu.be/OKTXq2VQ09s?t=32
            case "fttff":
            case "fttft":
            case "ftttf":
            case "ftttt":
            case "tffff":
            case "tffft":
                Shifter.LeftShift(alu.op1, 1, alu.result);
                alu.result.copy(alu.op1);
                alu.doInstruction();
                container = 3;
                break;
            case "tfftf":
                container = 4;
                break;
            case "tfftt":
                container = 5;
                break;
            case "tftff":
                container = 6;
                break;
        }
        clockCycle += 2;

    }

    private void store() {
        //determine destination based upon instruction and how it should be manipulated
        Word32 one= new Word32();
        Bit temp = new Bit();
        Word32 result = new Word32();
        one.setBitN(30, new Bit(true));
        switch (container)
        {
                //result to R2
            case 0:
                alu.result.copy(registers[reg2]);
                break;
                //push
            case 1:
                new Word32().copy(one);
                one.setBitN(31, new Bit(true));
                instructionStack.push(new Word32());
                PC.copy(instructionStack.getFirst());
                Adder.add(alu.op1, PC, result);
                result.copy(PC);
                break;
                //pop
            case 2:
                instructionStack.pop().copy(PC);
                break;
                //branch
            case 3:
                Adder.add(alu.result, PC, alu.op1);
                alu.op1.copy(PC);
                break;
            case 4:
                // Memory to Register
                instruction.getBitN(5, temp);
                if(temp.getValue().equals(Bit.boolValues.FALSE))
                {
                    alu.op1.copy(mem.address);
                    if (useInstructionCache && useL2)
                    {

                    }
                    else if (useInstructionCache)
                    {

                    }
                    else
                    {
                        mem.read();
                        clockCycle += 298;
                    }
                    mem.value.copy(registers[reg2]);
                }
                else
                {
                    Adder.add(alu.op1, alu.op2, result);
                    result.copy(mem.address);
                    if (useInstructionCache && useL2)
                    {

                    }
                    else if (useInstructionCache)
                    {

                    }
                    else
                    {
                        mem.read();
                        clockCycle += 298;
                    }
                    mem.value.copy(registers[reg2]);
                }
                break;
                //Value to Memory
            case 5:
                alu.op2.copy(mem.address);
                alu.op1.copy(mem.value);

                if (useL2)
                {

                }
                else
                {
                    mem.write();
                    clockCycle += 298;
                }
                break;
            case 6:
                //data to Register
                alu.op1.copy(registers[reg2]);
                break;
            default:
                alu.doInstruction();
                break;
        }
    }

    private void printReg() {
        for (int i = 0; i < 32; i++) {
            var line = "r"+ i + ":" + registers[i].toString();
            output.add(line);
            System.out.println(line);
        }
    }

    private void printMem() {
        for (int i = 0; i < 1000; i++) {
            Word32 addr = new Word32();
            Word32 value = new Word32();
            // Convert i to Word32 here...
            TestConverter.fromInt(i, addr);
            addr.copy(mem.address);
            mem.read();
            mem.value.copy(value);
            var line = i + ":" + value;
            output.add(line);
            System.out.println(line);
        }
    }

    private Boolean isCallReturn (Word16 instruction)
    {
        switch (instruction.toString().replace(",","").substring(0,5))
        {
            case "ftfff":
            case "ftfft":
            case "ftftf":
            case "fttff":
            case "fttft":
            case "ftttf":
            case "ftttt":
            case "tffft":
                return true;
            default:
                return false;
        }
    }

    private int getReg(Word32 in)
    {
        int reg = 0;
        Bit temp = new Bit();
        for (int i = 27; i <32; i++)
        {
            in.getBitN(i, temp);
            if(temp.getValue().equals(Bit.boolValues.TRUE)) reg += (int) Math.pow(2, 31 - i);
        }
        return reg;
    }

}