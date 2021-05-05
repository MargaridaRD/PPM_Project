package textUserInterface;

import projeto.Effects;

import projeto.Gallery;


import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class textUserInterface {
    ArrayList<String> album;

   public textUserInterface(){
       album = create_album("src/textUserInterface/album.txt");

   }

    public ArrayList<String> create_album(String s) {
       ArrayList album = new ArrayList<String>();
        File file = new File(s);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                album.add("src/projeto/img/"+line);
                }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Não encontrou o ficheiro!!");
            System.exit(1);
        }
        return album;

    }

    public void menu (){
       ArrayList< String> album1 =album;
       for( int i =0;  i< album1.size(); i++){
            String s = album1.get(i).replace("src/projeto/img/", "");
            album1.set(i,s);
       }

      new text().menu(album1);


    }

    public static void main(String[] args) {
        textUserInterface album1 = new textUserInterface();
        album1.menu();

    }

}

