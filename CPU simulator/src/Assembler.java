import java.util.Arrays;

public class Assembler {



    public static String[] assemble(String[] input)
    {
        String [] result = new String[input.length];
        String isR2 = "f";
        String temp = "";
        boolean negative = false;
        int counter = 0;
        for (String command : input) {
            //parse the operator
            result[counter] = "";
            temp = "";
            negative = false;
            while(!command.isEmpty())
            {
                if(command.charAt(0) == ' ')
                {
                    break;
                }
                temp = temp.concat(Character.toString(command.charAt(0)));
                command = command.substring(1);
            }
            result[counter] = result[counter].concat(parseOperator(temp));
            if(command.isEmpty())
            {
                result[counter] = result[counter].concat("fffffffffff");
            }
            else {
                command = command.substring(1);
                isR2 = isCallFormat(temp);
                temp = "";
                //check is sys call/return format
                if (isR2 == "f") {
                    //check if r1 is register set r2 bit accordingly concat to result
                    if (command.charAt(0) != 'r') {
                        isR2 = "t";
                    }
                    else
                    {
                        command = command.substring(1);
                    }
                    result[counter] = result[counter].concat(isR2);
                    //parse r1 to 5 bits concat to result
                    if(command.charAt(0) == '-')
                    {
                        negative = true;
                        command = command.substring(1);
                    }
                    while (command.charAt(0) != ' ')
                    {
                        temp = temp.concat(Character.toString(command.charAt(0)));
                        command = command.substring(1);
                    }
                    command = command.substring(1);
                    result[counter] = result[counter].concat(parseRegister(temp, 5, negative));
                    negative = false;
                    temp = "";
                    //parse r2 to 5 bits concat to result

                    while (!command.isEmpty())
                    {
                        if(command.charAt(0) != 'r')
                        {
                            temp = temp.concat(Character.toString(command.charAt(0)));
                        }
                        command = command.substring(1);
                    }
                    result[counter] = result[counter].concat(parseRegister(temp, 5, false));
                } else {
                    if(command.charAt(0) == '-')
                    {
                        negative = true;
                        command = command.substring(1);
                    }
                    while (!command.isEmpty())
                    {
                        if(command.charAt(0) != 'r')
                        {
                            temp = temp.concat(Character.toString(command.charAt(0)));
                        }
                        command = command.substring(1);
                    }
                    result[counter] = result[counter].concat(parseRegister(temp, 11, negative));
                }
            }
            counter++;
        }
        return result;
    }

    public static String[] finalOutput(String[] input) {
        String [] output = new String[(input.length/2) + (input.length % 2)];
        int counter;
        for (counter = 0; counter < input.length - 1; counter += 2)
        {
            output[counter/2] = input[counter] + input[counter + 1];
        }
        if (counter < input.length)
        {
            output[output.length - 1] = input[input.length - 1];
            output[output.length - 1] = output[output.length - 1].concat("ffffffffffffffff");
        }
        return output;
    }

    //checks if the operator is a syscall/return format opperation
    private static String isCallFormat(String input)
    {
        return switch (input) {
            case "call", "syscall", "ble", "blt", "bge", "bgt", "beq", "bne" -> "t";
            default -> "f";
        };
    }

    //parses the operator into a 5 bit string
    private static String parseOperator(String input)
    {
        return switch (input) {
            case "halt" -> "fffff";
            case "add" -> "fffft";
            case "and" -> "ffftf";
            case "multiply" -> "ffftt";
            case "leftshift" -> "fftff";
            case "subtract" -> "fftft";
            case "or" -> "ffttf";
            case "rightshift" -> "ffttt";
            case "syscall" -> "ftfff";
            case "call" -> "ftfft";
            case "return" -> "ftftf";
            case "compare" -> "ftftt";
            case "ble" -> "fttff";
            case "blt" -> "fttft";
            case "bge" -> "ftttf";
            case "bgt" -> "ftttt";
            case "beq" -> "tffff";
            case "bne" -> "tffft";
            case "load" -> "tfftf";
            case "store" -> "tfftt";
            case "copy" -> "tftff";
            default -> "";
        };
    }

    //parses a register/imediate as a 5 bit string
    private static String parseRegister(String input, int size, boolean negative)
    {
        int number =  Integer.parseInt(input), pos = size - 1;
        String t = "t";
        String f = "f";
        String result = "";
        if (negative)
        {
            number--;
            t = "f";
            f = "t";
        }

        while (number > 0) {
            if (number >= Math.pow((double) 2, (double) pos))
            {
                result = result.concat(t);
                number = (int) (number - Math.pow((double) 2, (double) pos));
            } else
            {
                result = result.concat(f);
            }
            pos--;
        }
        while (result.length() < size)
        {
            result = result.concat(f);
        }

        return result;
    }



}
