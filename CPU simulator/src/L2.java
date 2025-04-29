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
            TestConverter.fromInt(111, addresses[i]);
        }
    }

    //try to read or fail and go to memory
    //returns time that it took to read data
    public int read(Word32 Destination, Word32 Result, Memory mem)
    {
        int time = 0;
        Word32 result = new Word32();
        Word32 trueAddress = new Word32();
        int counter = 0;
        for (Word32 currentAddress : addresses)
        {
            Shifter.LeftShift(currentAddress, 3,trueAddress);
            Adder.subtract(Destination, trueAddress, result);
            int register = TestConverter.toInt(result);
            //hit
            if (register < 8 && register > -1)
            {
                cacheBlock[register + counter].copy(Result);
                surface(counter/8);
                return 50;
            }
            counter+= 8;
        }
        //failed, fetch data and try again
        time = fetch(Destination, mem);
        read(Destination, Result, mem);
        return time;
    }

    //try to write or fail and go to memory
    //returns time it took to write data
    public int write(Word32 Destination, Word32 Value, Memory mem)
    {
        int time = 0;
        Word32 result = new Word32();
        Word32 trueAddress = new Word32();
        int counter = 0;
        for (Word32 currentAddress : addresses)
        {
            Shifter.LeftShift(currentAddress, 3, trueAddress);
            Adder.subtract(Destination, trueAddress, result);
            int register = TestConverter.toInt(result);
            //hit
            if (register < 8 && register > -1)
            {
                Value.copy(cacheBlock[register + counter]);
                surface(counter/8);
                return 50;
            }
            counter+= 8;
        }
        //failed, fetch data and try again
        time = fetch(Destination, mem);
        write(Destination, Value, mem);
        return time;
    }


    //stores the least used block back into memory before deletion
    private void store(Memory mem)
    {
        Word32 trueAddress = new Word32();
        Word32 iterator = new Word32();
        Shifter.LeftShift(addresses[3], 3, trueAddress);
        TestConverter.fromInt(1, iterator);
        for (int i = 24; i < 32; i++)
        {
            trueAddress.copy(mem.address);
            cacheBlock[i].copy(mem.value);
            mem.write();
            Adder.add(trueAddress, iterator, trueAddress);
        }
        requeue();
    }

    public void storeAll(Memory mem)
    {
        //clears
        for (int i = 0; i < 4; i++) store(mem);
    }

    //get a block from memory
    private int fetch(Word32 Destination, Memory mem)
    {
        //store least used block in memory
        store(mem);
        //fill new block with data
        Word32 trueAddress = new Word32();
        Word32 iterator = new Word32();
        TestConverter.fromInt(1, iterator);
        Shifter.RightShift(Destination, 3, addresses[0]);
        Shifter.LeftShift(addresses[0], 3, trueAddress);
        for (int i = 0; i < 8; i++)
        {
            trueAddress.copy(mem.address);
            mem.read();
            mem.value.copy(cacheBlock[i]);
            Adder.add(trueAddress, iterator, trueAddress);
        }
        return 350;
    }

    //L2 functions almost like a stack, when a data block is moved it surfaces when it's used less it sinks, the most sunken block is deleted
    private void requeue()
    {
        //every block sinks by one adds new data block to the cache
        Word32 number = new Word32();
        addresses[2].copy(addresses[3]);
        addresses[1].copy(addresses[2]);
        addresses[0].copy(addresses[1]);
        TestConverter.fromInt(111, number);
        number.copy(addresses[0]);

        for (int i = 31; i > 7; i--)
        {
            cacheBlock[i - 8].copy(cacheBlock[i]);
        }
    }

    //this actually was maiking it faster storing times were not counted properly.
    private void surface(int address)
    {
        //swap two blocks allowing one to surface and the other to sink
        if (address != 0) {
            Word32 temp = new Word32();
            addresses[address].copy(temp);
            addresses[address - 1].copy(addresses[address]);
            temp.copy(addresses[address - 1]);
            for (int i = (8 * address) + 7; i >  (8 * address) - 1; i--)
            {
                cacheBlock[i].copy(temp);
                cacheBlock[i - 8].copy(cacheBlock[i]);
                temp.copy(cacheBlock[i - 8]);
            }
        }
    }

}
