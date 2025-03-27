import java.util.Arrays;

public class Assembler {



    public static String[] assemble(String[] input)
    {
        String [] result = new String[input.length];
        String isR2 = "f";
        String temp = "";
        int counter = 0;
        for (String command : input) {
            //parse the operator
            while(command.length() > 0 && command.charAt(0) != ' ')
            {
                temp = temp.concat(Character.toString(command.charAt(0)));
                command = command.substring(1);
            }
            command = command.substring(1);
            result[counter] = result[counter].concat(parseOperator(temp));
            isR2 = isCallFormat(temp);
            temp = "";
            //check is sys call/return format
            if (isR2 == "f")
            {
                //check if r1 is register set r2 bit accordingly concat to result
                if(command.charAt(0) == 'r')
                {
                    isR2 = "t";
                    command = command.substring(1);
                }
                //parse r1 to 5 bits concat to result

                //parse r2 to 5 bits concat to result

            }
            else
            {
                //parse op2 as 11 bit integer
            }




            counter++;
        }
        return result;
    }

    public static String[] finalOutput(String[] input) {
        return null;
    }

    //checks if the operator is a syscall/return format opperation
    private static String isCallFormat(String input)
    {
        String result;
        switch (input)
        {
            case "syscall":
            case "ble":
            case "blt":
            case "bge":
            case "bgt":
            case "beq":
            case "bne":
                result = "t"
                break;
            default:
                result = "f";
        }
        return result;
    }

    //parses the operator into a 5 bit string
    private static String parseOperator(String input)
    {
        String result;
        return result;
    }

    //parses a register/imediate as a 5 bit string
    private static String parseRegister5(String input)
    {
        String result;
        return result;
    }

    private static String parseRegister11(String input)
    {
        String result;
        return result;
    }



}
