package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class HelpScreen implements Screen {
    
    private PlayScreen game;
    
    private int pos = 0;
    private String[] text;
    
    public HelpScreen(PlayScreen game) {
        this.game = game;
        text = new String[32];
        Arrays.fill(text,"");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File("help.txt")));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file: " + e);
        }
        
        try {
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                text[i++] = line;
            }
        } catch (IOException e) {
            System.out.println("Unable to read file: " + e);
        }
        
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("Unable to close file: " + e);
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int i=0;i<24;i++) {
            terminal.writeCenter(text[i+pos],i);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ESCAPE)
            return game;
        else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_J || k == KeyEvent.VK_NUMPAD2) {
            if (pos<8)
                pos++;
        } else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_K || k == KeyEvent.VK_NUMPAD8) {
            if (pos>0)
                pos--;
        }
        
        return this;
    }
}
