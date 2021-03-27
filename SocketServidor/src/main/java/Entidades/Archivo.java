package Entidades;
import java.io.*;

public class Archivo {
    
    // Constructor
    public Archivo(){
        this.direccion = "";
        this.contenido = "";
    }
    
    // Atributos
    public String direccion;
    public String contenido;
    
    // Metodos
    public boolean Crear(){
        boolean retorno = false;
        File archivo;
        try {
            archivo = new File(this.direccion);
            if(archivo.createNewFile()){
                retorno = true;
            }
        }
        catch(IOException ex ) {
            System.err.println("No se ha podido crear el archivo.");
        }
        return retorno;
    }
    
    public boolean Existencia(){
        boolean existe = false;
        File file;
        file = new File(this.direccion);
        if(file.exists()){
            existe = true;
        }
        return existe;
    }
    
    public String Leer(Cuenta cuenta,boolean buscarCuenta) throws FileNotFoundException, IOException{
        String contenidoArchivo;
        try (BufferedReader bf = new BufferedReader(new FileReader(this.direccion))) {
            contenidoArchivo = "";
            String bfRead;
            while((bfRead = bf.readLine()) != null){
                if(buscarCuenta){
                    if(bfRead.contains(String.valueOf(cuenta.numero))){
                        contenidoArchivo += "\n"+bfRead;
                    }
                }
                else{
                    contenidoArchivo += "\n"+bfRead;
                }
            }
        }
        return contenidoArchivo;
    }
    
    public String Escribir(){
        String Msj = "NO-OK";
        File file;
        FileWriter writer;
        BufferedWriter bWriter;
        PrintWriter pWriter;
        try{
            file = new File(this.direccion);
            writer = new FileWriter(file.getAbsoluteFile(),true);
            bWriter = new BufferedWriter(writer);
            pWriter = new PrintWriter(bWriter);
            
            pWriter.append("\n"+this.contenido);
            pWriter.close();
            bWriter.close();
            Msj = "OK";
            
        }
        catch(IOException ex ) {
            System.out.println(ex);
        }
        return Msj;
    }
    
}
