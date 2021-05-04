package textUserInterface;

import projeto.Effects;

import projeto.Tree;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class textUserInterface {
   ArrayList<String> album;

   public textUserInterface(){
       album = create_album("src/textUserInterface/album.txt");

   }

    public  ArrayList create_album(String s) {
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
        System.out.println("Album: " + album1 );
        System.out.println( "Escolha uma imagem" );
        Scanner sc = new Scanner(System.in);
        String x= sc.nextLine();
        while (!album1.contains(x)){
            System.out.println( "Imagem não encontrada. Insira nova imagem " );
            x=sc.nextLine();

        }
        System.out.print( "Escolha um efeito:\n" + "1-mirrorV\n" + "2-mirrorH\n"+ "3-rotateR\n"+ "4-rotateL\n"+
        "5-scale\n" + "6-noise\n" + "7-contrast\n" +"8-sepia\n"  +"0-sair\n");
        int option = sc.nextInt();

         while( option != 0){
             Tree img= new Tree("src/projeto/img/"+x);
             Effects effects= new Effects(img.imageToTree());


             switch(option) {
                 case 1:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.mirrorV());

                     break;
                 case 2:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.mirrorH());
                     break;

                 case 3:
                     img.treeToImage( "src/projeto/img/"+ x,"png",effects.rotateR());
                     break;
                 case 4:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.rotateL());
                     break;
                 case 5:
                     System.out.println("Scale de quanto?? ");
                     Double scale_value= sc.nextDouble();
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.scale(scale_value));
                     break;
                     /*
                 case 6:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.mapColorEffect(effects.noise));
                     break;
                 case 7:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.mapColorEffect(effects.contrast));
                     break;
                 case 8:
                     img.treeToImage( "src/projeto/img/"+ x,"png", effects.mapColorEffect(effects.noise));
                     break;

                    */

                 default:
                     System.out.println("Insira um númro válido ");
                     option=sc.nextInt();
                     break;
             }
             System.out.println("Insira outro efeito ");
             option = sc.nextInt();
         }
         sc.close();


    }

    public static void main(String[] args) {
        textUserInterface album1 = new textUserInterface();
        album1.menu();

    }

}

