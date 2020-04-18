package me.apex.hades.utils;

import me.apex.hades.Hades;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFile {

    private File file;
    private String name;
    private List<String> lines = new ArrayList<>();

    public TextFile(String name, String path)
    {
        this.file = new File(Hades.instance.getDataFolder() + path);
        this.file.mkdirs();
        this.file = new File(Hades.instance.getDataFolder() + path, name + ".txt");
        try{
            this.file.createNewFile();
        }catch(Exception e) { }
        this.name = name;
        this.readTextFile();
    }

    public void clear() { this.lines.clear(); }

    public void addLine(String line) { this.lines.add(line); }

    public void write()
    {
        try{
            FileWriter fw = new FileWriter(this.file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String line : this.lines)
            {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            fw.close();
        }catch(Exception e) { }
    }

    public void readTextFile()
    {
        this.lines.clear();
        try{
            String line;
            FileReader fr = new FileReader(this.file);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null)
            {
                this.lines.add(line);
            }
            br.close();
            fr.close();
        }catch(Exception e) { e.printStackTrace(); }
    }

    public String getText()
    {
        String text = "";
        int i = 0;
        while(i < this.lines.size())
        {
            String line = this.lines.get(i);
            text = text + line + (this.lines.size() -1 == i ? "" : "\n");
            ++i;
        }
        return text;
    }

    public List<String> getLines()
    {
        return lines;
    }

}