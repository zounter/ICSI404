If you run into a compile error caused by an improper import
PLEASE
EITHER REMOVE ALL IMPORTS FROM EVERY CLASS FILE submited

OR IMPLAMENT THE CODE IN THIS STRUCTURE

src
    Code
        Assembler
            Bit
            Word16
            Word32
        Cache
        Math
            Adder
            Multiplier
            Shifter
            TestConverter
        Memory_and_ALU
        Processor
    Tests
        Assembler
            AssemblerTest
        Bits_and_Words
            BitTest
            Word16Test
            Word32Test
        Math
            AdderTest
            MultiplierTest
            ShifterTest
        Memory_and_ALU
            ALUTest
            MemoryTest

You will also need to ensure that the Tests properly import everything needed to run