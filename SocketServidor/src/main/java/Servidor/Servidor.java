package Servidor;
import Entidades.*;
import java.io.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.*;
import java.util.logging.*;

public class Servidor {
    
    public static void main(String args[]) {

            final String direccion = "/home/user/Documentos/Data.txt";
            final int port = 1313;
            ServerSocket server;
            Socket sc;
            DataInputStream in;
            DataOutputStream out;
            
            Archivo archivo;
            Cuenta cuenta;

            try {
                    server = new ServerSocket(port);
                    System.out.println("Server iniciado...");

                    while(true) {
                            String respuesta = "";
                            
                            sc = server.accept();
                            in = new DataInputStream(sc.getInputStream());
                            out = new DataOutputStream(sc.getOutputStream());
                            
                            cuenta = new Cuenta();
                            archivo = new Archivo();
                            archivo.direccion = direccion;
                                                        
                            String[] cuentasStr = in.readUTF().split(";");
                            
                            cuenta.numero = parseInt(cuentasStr[0]);
                            cuenta.valor = parseDouble(cuentasStr[1]);
                            cuenta.insercion = (parseInt(cuentasStr[2].trim()) == 1);
                            
                            if(cuenta.insercion){
                                archivo.contenido = cuenta.numero+"-$"+cuenta.valor;
                                if(archivo.Existencia() || archivo.Crear()){
                                    respuesta = archivo.Escribir();
                                }
                            }
                            else{
                                respuesta = archivo.Leer(cuenta,true);
                            }
                            
                            out.writeUTF(respuesta);
                            sc.close();
                            
                            System.out.println("Cliente desconectado.");
                    }
            }
            catch(IOException ex ) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,null,ex);
            }
    }
   
}
