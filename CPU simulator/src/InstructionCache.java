import java.util.Optional;

public class InstructionCache {
    private Word32[] cacheBlock = new Word32[8];
    private Word32 address = new Word32();

    InstructionCache() {
        for (int i = 0; i < cacheBlock.length; i++) {
            cacheBlock[i] = new Word32();
        }
        TestConverter.fromInt(111,address);
    }


    public int read(Word32 Destination, Word32 Result, Optional<L2> l2, Memory mem)
    {
        //reading
        Word32 result = new Word32();
        Word32 trueAddress = new Word32();
        Shifter.LeftShift(address, 3, trueAddress);
        Adder.subtract(Destination, trueAddress, result);
        int register = TestConverter.toInt(result);
        if (register > 7 || register < 0)
        {
            //read failed
            if (l2.isPresent()) {
                return fetchL2(Destination, Result, l2.get(), mem);
            } else {
                return fetchMemory(Destination, Result, mem);
            }
        }
        //read success
        cacheBlock[register].copy(Result);
        return 10;
    }

    private int fetchL2(Word32 Destination, Word32 Result, L2 l2, Memory mem)
    {
        //fetch memory from l2
        Word32 result = new Word32();
        int register;
        //this will read the exact Result, which also pops up the block in the stack
        int time = l2.read(Destination, Result, mem);
        int counter = 0;
        for (Word32 currentBlock : l2.addresses)
        {
            Adder.subtract(Destination, currentBlock, result);
            register = TestConverter.toInt(result);
            if (register > 0 && register < 8)
            {
                for (int i = 0; i < 8; i++)
                {
                        l2.cacheBlock[i + counter].copy(cacheBlock[i]);
                }
            }
            counter+= 8;
        }
        return time;
    }

    public int fetchMemory(Word32 Destination, Word32 Result, Memory mem)
    {
        Word32 result = new Word32();
        Word32 trueAddress = new Word32();
        Shifter.RightShift(Destination, 3, result);
        result.copy(address);
        Word32 number = new Word32();
        Shifter.LeftShift(address, 3,trueAddress);
        TestConverter.fromInt(1, number);
        trueAddress.copy(mem.address);
        for (int i = 0; i < 8; i++)
        {
            mem.read();
            mem.value.copy(cacheBlock[i]);
            Adder.add(trueAddress, number, mem.address);
            mem.address.copy(trueAddress);
        }
        TestConverter.fromInt(8, number);
        Adder.subtract(trueAddress, number, result);
        result.copy(trueAddress);
        Adder.subtract(Destination,trueAddress, result);
        int register = TestConverter.toInt(result);
        cacheBlock[register].copy(Result);
        return 350;
    }
}




