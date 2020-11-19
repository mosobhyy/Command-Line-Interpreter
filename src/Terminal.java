import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Terminal
{

    /*  Private Attributes  */

    private String dir;

    /*  Public Methods  */

    //  Change Directory
    public void cd(String path)
    {
        if (path.equals(".."))
        {
            File file = new File(getDir());
            setDir(file.getAbsoluteFile().getParent() + '/');
        }
        else
        {
            path = checkPath(path);
            File destination = new File(path);
            if (!destination.exists())
                System.out.println("No such file or directory !");
            else if (destination.isFile())
                System.out.println("Not a directory !");
            else
                setDir(path);
        }
    }

    // Get Directory's Content
    public void ls() throws IOException
    {
        File f = new File(getDir());
        DirectoryStream<Path> contents = Files.newDirectoryStream(f.toPath());
        for (Path name : contents)
            System.out.println(name.getFileName().toString());
    }

    // Write Directory's Content to file
    public void lsToFile(String path, boolean append) throws IOException
    {
        try
        {
            path = checkPath(path);
            File filePath = new File(path);
            if (filePath.isDirectory())
                System.out.println("Is a directory");
            else
            {
                File f = new File(getDir());
                DirectoryStream<Path> contents = Files.newDirectoryStream(f.toPath());
                FileWriter myWriter = new FileWriter(filePath, append);
                for (Path name : contents)
                    myWriter.write(name.getFileName().toString() + '\n');
                myWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("bash: " + path + ": No such file or directory");
        }
    }

    //  Copy File
    public void cp(String sourcePath, String destinationPath) throws IOException
    {
        sourcePath = checkPath(sourcePath);
        destinationPath = checkPath(destinationPath);
        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        if (checkFile(source))
        {
            if (!destination.isFile())
            {
                destinationPath = destinationPath + '/' + source.getName();
                destination = new File(destinationPath);
            }
            destination.createNewFile();
            Scanner myReader = new Scanner(source);
            FileWriter myWriter = new FileWriter(destination);
            while (myReader.hasNextLine())
                myWriter.write(myReader.nextLine() + "\n");
            myReader.close();
            myWriter.close();
        }
    }

    // Get File's Content
    public void cat(String path) throws FileNotFoundException
    {
        path = checkPath(path);
        File filePath = new File(path);
        if (checkFile(filePath))
        {
            Scanner myReader = new Scanner(filePath);
            while (myReader.hasNextLine())
                System.out.println(myReader.nextLine());
            myReader.close();
        }
    }

    // Write File's Content to file
    public void catOrMoreToFile(String sourcePath, String destinationPath, boolean append) throws IOException
    {
        sourcePath = checkPath(sourcePath);
        destinationPath = checkPath(destinationPath);
        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        if (checkFile(source))
        {
            if (!destination.isFile())
            {
                destinationPath = destinationPath + '/' + source.getName();
                destination = new File(destinationPath);
            }
            destination.createNewFile();
            Scanner myReader = new Scanner(source);
            FileWriter myWriter = new FileWriter(destination, append);
            while (myReader.hasNextLine())
                myWriter.write(myReader.nextLine() + "\n");
            myReader.close();
            myWriter.close();
        }
    }

    // Get File's paragraph (Press "ENTER" for line OR "SPACE + ENTER" For 10 lines)
    public void more(String path) throws FileNotFoundException
    {
        path = checkPath(path);
        File filePath = new File(path);
        if (checkFile(filePath))
        {
            Scanner myReader = new Scanner(filePath);
            Scanner scanner = new Scanner(System.in);
            String temp;
            int counter = 0;
            while (myReader.hasNext())
            {
                System.out.print(myReader.nextLine());
                if (counter > 10)
                {
                    temp = scanner.nextLine();
                    if (temp.equals(" "))
                        counter = 0;
                }
                else
                    System.out.println();
                counter++;
            }
        }
    }

    // Create a new directory
    public void mkdir(String fileName)
    {
        fileName = checkPath(fileName);
        File filePath = new File(fileName);
        if (!filePath.mkdir())
            System.out.println("mkdir: cannot create directory ‘" + filePath.getName() + "’:File exists");
    }

    // delete a directory
    public void rmdir(String fileName)
    {
        fileName = checkPath(fileName);
        File filePath = new File(fileName);
        if (!filePath.exists())
            System.out.println("rmdir: failed to remove ‘" + filePath.getName() + "’: No such file or directory");
        else if (filePath.isFile())
            System.out.println("rmdir: failed to remove ‘" + filePath.getName() + "’: Not a directory");
        else
            filePath.delete();
    }

    // Move File or Directory
    public void mv(String sourcePath, String destinationPath) throws IOException
    {
        sourcePath = checkPath(sourcePath);
        destinationPath = checkPath(destinationPath);
        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        if (!source.exists())
            System.out.println("mv: cannot stat '" + source.getName() + "': No such file or directory");
        else if (source.isDirectory() && destination.isFile())
            System.out.println("mv: cannot overwrite non-directory '" + destination.getAbsolutePath() + "' with directory '" + source.getName() + "'");
        else
        {
            if (destination.isDirectory())
            {
                destinationPath = destinationPath + '/' + source.getName();
                destination = new File(destinationPath);
            }
            if (source.isDirectory())
                destination.mkdir();
            else
            {
                destination.createNewFile();
                Scanner myReader = new Scanner(source);
                FileWriter myWriter = new FileWriter(destination);
                while (myReader.hasNextLine())
                    myWriter.write(myReader.nextLine() + "\n");
                myReader.close();
                myWriter.close();
            }
            source.delete();
        }
    }

    // delete a file
    public void rm(String sourcePath)
    {
        sourcePath = checkPath(sourcePath);
        File path = new File(sourcePath);
        if (checkFile(path))
            path.delete();
    }

    // Show command's Arguments
    public void args(String command)
    {
        switch (command)
        {
            case "cd", "mv" -> System.out.println("“arg1: DestinationPath”");
            case "ls", "help", "pwd", "clear", "date" -> System.out.println("“No Parameters”");
            case "cp" -> System.out.println("“arg1: SourcePath, arg2: DestinationPath”");
            case "cat", "more", "mkdir", "rmdir", "rm" -> System.out.println("“arg1: filePath”");
            case "args" -> System.out.println("“arg1: command”");
            default -> System.out.println(command + ": Command Not Found !");
        }
    }

    // Write command's Arguments to File
    public void argsToFile(String command, String path, boolean append) throws IOException
    {
        try
        {
            path = checkPath(path);
            File filePath = new File(path);
            if (filePath.isDirectory())
                System.out.println("bash: " + path + ": Is a directory");
            else
            {
                FileWriter myWriter = new FileWriter(filePath, append);
                switch (command)
                {
                    case "cd", "mv" -> myWriter.write("“arg1: DestinationPath”\n");
                    case "ls", "help", "pwd", "clear", "date" -> myWriter.write("“No Parameters”\n");
                    case "cp" -> myWriter.write("“arg1: SourcePath, arg2: DestinationPath”\n");
                    case "cat", "more", "mkdir", "rmdir", "rm" -> myWriter.write("“arg1: filePath”\n");
                    case "args" -> myWriter.write("“arg1: command”\n");
                    default -> myWriter.write(command + ": Command Not Found !\n");
                }
                myWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("bash: " + path + ": No such file or directory");
        }
    }

    // Show date and time
    public void date()
    {
        java.util.Date date = new java.util.Date();
        System.out.println(date);
    }

    // Write date and time to File
    public void dateToFile(String path, boolean append) throws IOException
    {
        try
        {
            path = checkPath(path);
            File filePath = new File(path);
            if (filePath.isDirectory())
                System.out.println("bash: " + path + ": Is a directory");
            else
            {
                FileWriter myWriter = new FileWriter(filePath, append);
                java.util.Date date = new java.util.Date();
                myWriter.write(String.valueOf(date) + '\n');
                myWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("bash: " + path + ": No such file or directory");
        }
    }

    // list all user's commands and the syntax of their arguments
    public void help()
    {
        System.out.println("cp: “arg1: SourcePath, arg2: DestinationPath”");
        System.out.println("mv: “arg1: SourcePath, arg2: DestinationPath”");
        System.out.println("cd: “arg1: DestinationPath”");
        System.out.println("cat: “arg1: filePath”");
        System.out.println("more: “arg1: filePath”");
        System.out.println("mkdir: “arg1: filePath”");
        System.out.println("rmdir: “arg1: filePath”");
        System.out.println("rm: “arg1: filePath”");
        System.out.println("args: “arg1: command”");
        System.out.println("ls: “No Parameters”");
        System.out.println("help: “No Parameters”");
        System.out.println("pwd: “No Parameters”");
        System.out.println("clear: “No Parameters”");
        System.out.println("date: “No Parameters”");
    }

    // Write all user's commands and the syntax of their arguments to File
    public void helptoFile(String path, boolean append)
    {
        try
        {
            path = checkPath(path);
            File filePath = new File(path);
            if (filePath.isDirectory())
                System.out.println("bash: " + path + ": Is a directory");
            else
            {
                FileWriter myWriter = new FileWriter(filePath, append);
                myWriter.write("cp: “arg1: SourcePath, arg2: DestinationPath”\n");
                myWriter.write("mv: “arg1: SourcePath, arg2: DestinationPath”\n");
                myWriter.write("cd: “arg1: DestinationPath”\n");
                myWriter.write("cat: “arg1: filePath”\n");
                myWriter.write("more: “arg1: filePath”\n");
                myWriter.write("mkdir: “arg1: filePath”\n");
                myWriter.write("rmdir: “arg1: filePath”\n");
                myWriter.write("rm: “arg1: filePath”\n");
                myWriter.write("args: “arg1: command”\n");
                myWriter.write("ls: “No Parameters”\n");
                myWriter.write("help: “No Parameters”\n");
                myWriter.write("pwd: “No Parameters”\n");
                myWriter.write("clear: “No Parameters”\n");
                myWriter.write("date: “No Parameters”\n");
                myWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("bash: " + path + ": No such file or directory");
        }
    }

    // Get Current Directory
    public void pwd()
    {
        System.out.println(getDir());
    }

    // Write Current Directory to File
    public void pwdToFile(String path, boolean append)
    {
        try
        {
            path = checkPath(path);
            File filePath = new File(path);
            if (filePath.isDirectory())
                System.out.println("bash: " + path + ": Is a directory");
            else
            {
                FileWriter myWriter = new FileWriter(filePath, append);
                myWriter.write(getDir() + '\n');
                myWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("bash: " + path + ": No such file or directory");
        }
    }

    public void clear()
    {
        for (int i = 0; i < 15; i++)
            System.out.println();
    }

    private String checkPath(String path)
    {
        if (path.charAt(0) != '/')
            path = getDir() + path;

        if (path.charAt(path.length() - 1) != '/')
            path += '/';

        return path;
    }

    private boolean checkFile(File file)
    {
        if (!file.exists())
        {
            System.out.println("No such file or directory !");
            return false;
        }
        else if (file.isDirectory())
        {
            System.out.println("Is a directory !");
            return false;
        }
        return true;
    }

    /*   Getters & Setters    */

    public String getDir()
    {
        return dir;
    }

    public void setDir(String dir)
    {
        this.dir = dir;
    }

    /*   Default Constructor    */

    public Terminal()
    {
        setDir("/home/muhmd/");
    }

    /*   Parametrized Constructor    */

    public Terminal(String dir)
    {
        this.dir = dir;
    }

}
