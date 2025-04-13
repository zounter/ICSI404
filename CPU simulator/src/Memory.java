
public class Memory {
    public Word32 address= new Word32();
    public Word32 value = new Word32();

    private final Word32[] dram= new Word32[1000];

    public int addressAsInt() {
        return TestConverter.toInt(this.address);
    }

    public Memory() {
        for (int i = 0; i < this.dram.length; i++)
        {
            this.dram[i] = new Word32();
        }
    }

    public void read()
    {
        this.dram[addressAsInt()].copy(this.value);
    }

    public void write()
    {
        this.value.copy(this.dram[this.addressAsInt()]);
    }

    public void load(String[] data)
    {
        for (int i = 0; i < this.dram.length && i < data.length; i++)
        {
            this.dram[i].fromString(data[i]);
        }
    }
}
