public class L2 {
    Word32 [] cacheBlock = new Word32[32];
    Word32 [] addresses = new Word32[4];

    public L2()
    {
        for (int i = 0; i < cacheBlock.length; i++)
        {
            cacheBlock[i] = new Word32();
        }
        for (int i = 0; i < addresses.length; i++)
        {
            addresses[i] = new Word32();
        }
    }

    //try to read or fail and go to memory
    //returns time that it took to read data
    public int read(Word32 Destination, Word32 Result, Memory mem)
    {
        int register;
        Word32 result = new Word32();
        for (Word32 currentAddress : addresses)
        {
            Adder.subtract(Destination, currentAddress, result);
            register = TestConverter.toInt(result);
            if (register < 8 && register > 0)
            {
                cacheBlock[register].copy(Result);
                return 20;
            }
        }
        return fetch(Destination, Result, mem, 2);
    }

    //try to write or fail and go to memory
    //returns time it took to write data
    public int write(Memory mem)
    {
        return 2;
    }

    //store a block back into memory before deletion
    public void store(Memory mem, int Block)
    {

    }

    //get a block from memory
    private int fetch(Word32 Destination, Word32 Result, Memory mem, int Block)
    {
        Word32 number = new Word32();
        Word32 address = new Word32();
        Shifter.RightShift(Destination, 3, address);
        address.copy(addresses[Block]);
        TestConverter.fromInt(1, number);
        for (int i = 8 * Block; i < 8 + (8 * Block); i++)
        {
            mem.read();
            mem.value.copy(cacheBlock[i]);
            Adder.add(addresses[Block], number, address);
            address.copy(addresses[Block]);
        }
        Shifter.LeftShift(address, 3, number);
        number.copy(address);
        Adder.subtract(Destination, address, number);
        number.copy(address);
        int register = TestConverter.toInt(address);
        Result.copy(cacheBlock[register]);
        return 350;
    }

}
