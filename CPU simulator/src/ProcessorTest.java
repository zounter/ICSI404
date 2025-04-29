import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProcessorTest {
    @Test
    public void testAdd() {
        String[] program = {
                "add 10 r0",
                "syscall 0"
        };
        var p = runProgram(program);
        Assertions.assertEquals("r0:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,f,", p.output.getFirst());
    }

    @Test
    public void testFibonacci() {
        String[] program = {
                "copy 10 r9", // address  -- 0 0
                "leftshift 1 r9", // r9 = 20 1
                "copy 15 r0", // limit for fib -- 1 2
                "store 0 r9",  // first fib 3
                "add 1 r9", // move to 2nd -- 2 4
                "store 1 r9",  // second fib 5
                "add 1 r9", // move to next spot in ram -- 3 6
                "copy r9 r2", // temporary 7
                "copy r9 r3", // temporary2 -- 4 8
                "load -1 r2", // load f(n-1) into r2 9
                "load -2 r3", // load f(n-2) into r3-- 5 10
                "add r2 r3", // create next fib 11
                "store r3 r9", // store fib -- 6 12
                "subtract 1 r0", // decrement counter 13
                "compare 0 r0", // compare counter to 0 -- 7 14
                "bne -4", // loop back to 3 15
                "syscall 1", // print out memory -- 8 16
                "halt" // done 17
        };
        var p = runProgram(program);
        Assertions.assertEquals("35:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,t,t,f,f,f,t,f,", p.output.get(35));
        Assertions.assertEquals("36:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t,f,t,t,f,t,t,", p.output.get(36));
    }

    @Test
    public void testLinkedList () {
        String[] program = {
                "copy 10 r5", // address 0
                "leftshift 1 r5",
                "copy r5 r3", //address 1
                "store 0 r3",
                "copy r3 r2", //address 2
                "copy 1 r1",
                "add 2 r3", //address 3
                "store r1 r3",
                "add 1 r2", //address 4
                "store r3 r2",
                "copy r3 r2", //address 5
                "add 1 r1",
                "compare r5 r1", //address 6
                "blt -3",
                "copy r5 r3", //address 7
                "copy 0 r4",
                "load r3 r1", //address 8
                "add 1 r3",
                "load r3 r2", //address 9
                "add r1 r4",
                "copy r2 r3", //address 10
                "copy r2 r3",
                "compare 0 r3", //address 11
                "bne -3",
                "syscall 0", //address 12
                "halt",
        };
        System.out.println("no cache");
        var p = runProgram(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
        System.out.println("Instruction cache");
        p = runProgramInstruction(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
        System.out.println("L2 cache");
        p = runProgramL2(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
    }

    @Test
    public void testArray ()
    {
        String [] program = {
                "copy 10 r5", //address 0
                "leftshift 1 r5",
                "copy r5 r2", //address 1
                "copy 0 r6",
                "copy r2 r6", //address 2
                "add r1 r6",
                "store r1 r6", //address 3
                "add 1 r1",
                "compare r5 r1", //address 4
                "blt -2",
                "copy 0 r1", //address 5
                "copy 0 r4",
                "copy r2 r6", //address 6
                "add r1 r6",
                "load r6 r3", //address 7
                "add r3 r4",
                "add 1 r1", //address 8
                "add 0 r1",
                "compare r5 r1", //address 9
                "blt -3",
                "syscall 0", //address 10
                "halt",

        };
        System.out.println("no cache");
        var p = runProgram(program);
        System.out.println("Instruction cache");
        p = runProgramInstruction(program);
        System.out.println("L2 cache");
        p = runProgramL2(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
    }

    @Test
    public void testArrayBackwards ()
    {
        String [] program = {
                "copy 10 r5", //address 0
                "leftshift 1 r5",
                "copy r5 r2", //address 1
                "copy 0 r6",
                "copy r2 r6", //address 2
                "add r1 r6",
                "store r1 r6", //address 3
                "add 1 r1",
                "compare r5 r1", //address 4
                "blt -2",
                "copy r2 r1", //address 5
                "add r5 r1",
                "add 0 r1", //address 6
                "copy 0 r4",
                "copy r2 r6", //address 7
                "add r1 r6",
                "load r6 r3", //address 8
                "add r3 r4",
                "subtract 1 r1", //address 9
                "add 0 r1",
                "compare 0 r1", //address 10
                "bge -3",
                "syscall 0", //address 11
                "halt",
        };
        System.out.println("no cache");
        var p = runProgram(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
        System.out.println("Instruction cache");
        p = runProgramInstruction(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
        System.out.println("L2 cache");
        p = runProgramL2(program);
        Assertions.assertEquals("r4:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,t,t,t,t,f,", p.output.get(4));
    }

    @Test
    public void testPower() {
        String[] program = {
                "copy 6 r0", // address 0
                "copy 2 r1",
                "copy 0 r2", // address 1
                "call 4", // call 1+4 = 5
                "syscall 0",// address 2
                "copy 7 r0",
                "copy 3 r1",// address 3
                "call 2", // call 3 + 2
                "syscall 0",// address 4
                "halt",
                "copy r0 r2",// address 5
                "copy r0 r2", // waste a spot
                "multiply r0 r2", // address 6
                "subtract 1 r1",
                "compare 1 r1",// address 7
                "bne -1", // -- loop back to address 6
                "return", // address 8
        };
        var p = runProgram(program);
        Assertions.assertEquals("r2:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,t,f,f,", p.output.get(2));
        Assertions.assertEquals("r2:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,f,t,f,t,t,t,", p.output.get(34));
    }

    private static Processor runProgram(String[] program) {
        var assembled = Assembler.assemble(program);
        var merged = Assembler.finalOutput(assembled);
        var m = new Memory();
        m.load(merged);
        var p = new Processor(m);
        p.run();
        return p;
    }
    private static Processor runProgramInstruction(String[] program) {
        var assembled = Assembler.assemble(program);
        var merged = Assembler.finalOutput(assembled);
        var m = new Memory();
        m.load(merged);
        var p = new Processor(m);
        p.useInstructionCache = true;
        p.run();
        return p;
    }private static Processor runProgramL2(String[] program) {
        var assembled = Assembler.assemble(program);
        var merged = Assembler.finalOutput(assembled);
        var m = new Memory();
        m.load(merged);
        var p = new Processor(m);
        p.useL2 = true;
        p.run();
        return p;
    }
}
