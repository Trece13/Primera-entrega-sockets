package Cliente;
import Entidades.*;
import java.io.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;

public class Cliente {
    
    public static void main(String args[]) {
        
        try {
                boolean flujoActivado = true;

                while(flujoActivado){

                    final String host = "127.0.0.1";
                    final int port = 1313;
                    DataInputStream in;
                    DataOutputStream out;
                    Cuenta cuenta;

                    try (Socket sc = new Socket(host,port)) {
                        in = new DataInputStream(sc.getInputStream());
                        out = new DataOutputStream(sc.getOutputStream());
                        cuenta = new Cuenta();
                        
                        cuenta = FlujoTrabajoInicial(cuenta);
                        out.writeUTF(cuenta.numero+";"+cuenta.valor+";"+(cuenta.insercion == true ? 1 : 0));
                        flujoActivado = FlujoTrabajoFinal(in.readUTF());
                    }
                }

        }
        catch(IOException ex ) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public static Cuenta FlujoTrabajoInicial(Cuenta cuenta){
        switch(EjecutarPantallaPrincipal()){
            case 0 -> {
                cuenta.numero = parseInt(EjecutarPantallaCuenta());
                cuenta.valor = parseDouble(EjecutarPantallaValor());
                cuenta.insercion = true;
            }
            case 1 -> {
                cuenta.numero = parseInt(EjecutarPantallaCuenta());
                cuenta.insercion = false;
            }
        }
        return cuenta;
    }

    private static boolean FlujoTrabajoFinal(String respuesta){
        EjecutarPantallaRespuesta(respuesta);
        return (EjecutarPantallaFinal() == 0);
    }

    private static int EjecutarPantallaPrincipal(){
        String[] options = {"Insertar","Consultar"};
        return JOptionPane.showInternalOptionDialog(null,"Tipo de transaccion:", "title",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0] );
    }

    private static String EjecutarPantallaCuenta(){
        return JOptionPane.showInputDialog("Digite la cuenta:");
    }

    private static String EjecutarPantallaValor(){
        return JOptionPane.showInputDialog("Digite el valor:");
    }

    private static void EjecutarPantallaRespuesta(String respuesta){
        JOptionPane.showMessageDialog(null,respuesta);
    }

    private static int EjecutarPantallaFinal(){
        String[] options = {"Si","No"};
        return JOptionPane.showInternalOptionDialog(null,"¿Quieres ejecutarotra transacción?", "title",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null,options,options[0] );
    }
    
}
