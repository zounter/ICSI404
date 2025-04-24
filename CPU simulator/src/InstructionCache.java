public class InstructionCache {
    private Word32[] cacheBlock = new Word32[8];
    private Word32 address = new Word32();

    InstructionCache() {
        for (int i = 0; i < cacheBlock.length; i++) {
            cacheBlock[i] = new Word32();
        }
    }


    //read Cache
    public int read(Word32 Destination, Word32 Result, L2 l2, Memory mem, Boolean useL2)
    {
        Word32 result = new Word32();
        Adder.subtract(Destination, address, result);
        int register = TestConverter.toInt(result);
        if (register > 8 || register < 0)
        {
            if (useL2) {
                return fetchL2(Destination, Result, l2, mem);
            } else {
                return fetchMemory(Destination, Result, mem);
            }
        }
        cacheBlock[register].copy(Result);
        return 10;
    }

    //fetch memory from l2
    private int fetchL2(Word32 Destination, Word32 Result, L2 l2, Memory mem)
    {
        Word32 result = new Word32();
        int register;
        int time = l2.read(Destination, Result, mem);
        int counter = 0;
        for (Word32 currentBlock : l2.addresses)
        {
            Adder.subtract(Destination, currentBlock, result);
            register = TestConverter.toInt(result);
            if (register > 0 && register < 8)
            {
                for (int i = counter * 8; i < (counter * 8) + 8; i++)
                {
                    l2.cacheBlock[i].copy(cacheBlock[i/counter]);
                }
            }
            counter++;
        }

            return time;
    }

    private int fetchMemory(Word32 Destination, Word32 Result, Memory mem)
    {
        Word32 result = new Word32();
        Shifter.RightShift(Destination, 3, result);
        Word32 number = new Word32();
        TestConverter.fromInt(1, number);
        mem.address.copy(result);
        for (int i = 0; i < 8; i++)
        {
            mem.read();
            mem.value.copy(cacheBlock[i]);
            Adder.add(result, number, mem.address);
            mem.address.copy(result);
        }
        return 350;
    }

}




