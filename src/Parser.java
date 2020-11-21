import java.util.HashMap;
import java.util.Map;

public class Parser
{
    /*  Private Attributes  */

    private String[] args;
    private String cmd;
    private final Map<String, Integer> commands = new HashMap<>()
    {
        {
            put("cp", 2);
            put("mv", 2);
            put("cd", 1);
            put("cat", 1);
            put("more", 1);
            put("mkdir", 1);
            put("rmdir", 1);
            put("rm", 1);
            put("args", 1);
            put("ls", 0);
            put("help", 0);
            put("pwd", 0);
            put("clear", 0);
            put("date", 0);
        }
    };

    /*  Public Methods  */

    public boolean parse(String input)
    {
        String[] separated_input = input.split(" ");
        int length = separated_input.length;

        cmd = separated_input[0];
        args = new String[length - 1];
        System.arraycopy(separated_input, 1, args, 0, length - 1);

        if (!commands.containsKey(getCmd()))
        {
            System.out.println("No such command !");
            return false;
        }

        else if (commands.get(getCmd()) > getArgs().length)
        {
            System.out.println("Missing arguments !");
            return false;
        }

        else if (commands.get(getCmd()) < getArgs().length)
        {
            System.out.println("Too many arguments !");
            return false;
        }

        return true;
    }

    /*  Getters & Setters  */

    public String[] getArgs()
    {
        return args;
    }

    public void setArgs(String[] args)
    {
        this.args = args;
    }

    public String getCmd()
    {
        return cmd;
    }

    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }

    /* Default Constructor */

    Parser()
    {
        args = null;
        cmd = null;
    }
}
