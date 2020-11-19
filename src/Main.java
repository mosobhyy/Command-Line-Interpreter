import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Parser p = new Parser();
        Terminal t = new Terminal();
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("~$ ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit"))
                break;

            if (input.contains(" | "))
            {
                String[] separated_input = input.split(" \\| ");
                if (p.parse(separated_input[0]))
                    executeCmd(p, t);
                if (p.parse(separated_input[1]))
                    executeCmd(p, t);
            }
            else if(input.contains(" >> "))
            {
                String[] separated_input = input.split(" >> ");
                if (p.parse(separated_input[0]))
                    executeCmdToFile(p, t, separated_input[1], true);
            }
            else if(input.contains(" > "))
            {
                String[] separated_input = input.split(" > ");
                if (p.parse(separated_input[0]))
                    executeCmdToFile(p, t, separated_input[1], false);
            }
            else
                if (p.parse(input))
                    executeCmd(p, t);
        }
    }

    private static void executeCmd(Parser p, Terminal t) throws IOException
    {
        if (p.getCmd().contentEquals("cp"))
            t.cp(p.getArgs()[0], p.getArgs()[1]);
        else if (p.getCmd().contentEquals("mv"))
            t.mv(p.getArgs()[0], p.getArgs()[1]);
        else if (p.getCmd().contentEquals("cd"))
            t.cd(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("cat"))
            t.cat(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("more"))
            t.more(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("mkdir"))
            t.mkdir(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("rmdir"))
            t.rmdir(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("rm"))
            t.rm(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("args"))
            t.args(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("ls"))
            t.ls();
        else if (p.getCmd().contentEquals("help"))
            t.help();
        else if (p.getCmd().contentEquals("pwd"))
            t.pwd();
        else if (p.getCmd().contentEquals("clear"))
            t.clear();
        else if (p.getCmd().contentEquals("date"))
            t.date();
    }
    private static void executeCmdToFile(Parser p, Terminal t, String path, boolean append) throws IOException
    {
        if (p.getCmd().contentEquals("cp"))
            t.cp(p.getArgs()[0], p.getArgs()[1]);
        else if (p.getCmd().contentEquals("mv"))
            t.mv(p.getArgs()[0], p.getArgs()[1]);
        else if (p.getCmd().contentEquals("cd"))
            t.cd(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("cat"))
            t.catOrMoreToFile(p.getArgs()[0], path, append);
        else if (p.getCmd().contentEquals("more"))
            t.catOrMoreToFile(p.getArgs()[0], path, append);
        else if (p.getCmd().contentEquals("mkdir"))
            t.mkdir(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("rmdir"))
            t.rmdir(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("rm"))
            t.rm(p.getArgs()[0]);
        else if (p.getCmd().contentEquals("args"))
            t.argsToFile(p.getArgs()[0], path, append);
        else if (p.getCmd().contentEquals("ls"))
            t.lsToFile(path, append);
        else if (p.getCmd().contentEquals("help"))
            t.helptoFile(path, append);
        else if (p.getCmd().contentEquals("pwd"))
            t.pwdToFile(path, append);
        else if (p.getCmd().contentEquals("clear"))
            t.clear();
        else if (p.getCmd().contentEquals("date"))
            t.dateToFile(path, append);
    }
}
