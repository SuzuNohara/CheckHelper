import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author SuZZZu
 */
public class CheckHelper{

    private static String urlSite = "http://10.119.154.130/sf/sp/index.php";
    //private static String urlSite = "http://10.119.154.131/sf/sp/index.php";
    private static String inParams = "button2=ENTRADA&accion=registrar&tipo=ENTRADA";
    private static String outParams = "button=SALIDA&accion=registrar&tipo=SALIDA";
    private static int maxIn = 1680000;//Ejecucion 8:30
    private static int minIn = 1200000;
    private static int maxOut = 600000;//Ejecucion 6:00
    private static int minOut = 300000;
    private static Write esc;

    public static void main(String args[]) throws IndexOutOfBoundsException, InterruptedException, MalformedURLException, IOException {
        esc = new Write();
        esc.abrir("./log.txt");
        esc.escribir("____________________________________________________________");
        System.out.println("____________________________________________________________");
        if(args.length != 1){
            esc.escribir("Error en ejecucion: Numero de argumentos");
            System.out.println("Error en ejecucion: Numero de argumentos");
            System.exit(1);
        }
        if(args[0].equals("in")){
            esc.escribir("[" + getHour() + "] - Iniciando proceso de registro de entrada");
            System.out.println("[" + getHour() + "] - Iniciando proceso de registro de entrada");
            waitTime(args[0]);
            report(args[0]);
        }else if(args[0].equals("out")){
            esc.escribir("[" + getHour() + "] - Iniciando proceso de registro de salida");
            System.out.println("[" + getHour() + "] - Iniciando proceso de registro de salida");
            waitTime(args[0]);
            report(args[0]);
        }else{
            esc.escribir("Error en ejecucion: Consistencia de argumentos");
            System.out.println("Error en ejecucion: Consistencia de argumentos");
            System.exit(1);
        }
        esc.escribir("____________________________________________________________");
        esc.cerrar();
        System.out.println("____________________________________________________________");
    }

    private static boolean report(String mode) throws MalformedURLException, IOException {
        URL url;
        if(mode.equals("in")){
            url = new URL(urlSite + "?" + inParams);
            esc.escribir("[" + getHour() + "] - Construyendo url..." + url.toString());
            System.out.println("[" + getHour() + "] - Construyendo url..." + url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            esc.escribir("[" + getHour() + "] - Registrando entrada - " + con.getResponseCode());
            System.out.println("[" + getHour() + "] - Registrando entrada - " + con.getResponseCode());
            return con.getResponseCode() == 200;
        }else if(mode.equals("out")){
            url = new URL(urlSite + "?" + outParams);
            esc.escribir("[" + getHour() + "] - Construyendo url..." + url.toString());
            System.out.println("[" + getHour() + "] - Construyendo url..." + url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            esc.escribir("[" + getHour() + "] - Registrando salida - " + con.getResponseCode());
            System.out.println("[" + getHour() + "] - Registrando salida - " + con.getResponseCode());
            return con.getResponseCode() == 200;
        }
        return false;
    }

    private static void waitTime(String mode) throws InterruptedException {
        int range = 0;
        if(mode.equals("in")){
            range = (int)(Math.random() * (maxIn - minIn + 1)) + minIn;
        }else if(mode.equals("out")){
            range = (int)(Math.random() * (maxOut - minOut + 1)) + minOut;
        }
        esc.escribir("[" + getHour() + "] - El proceso esperara: " + ((int)((range/1000)/60)) + ":" + ((int)((range/1000)%60)));
        System.out.println("[" + getHour() + "] - El proceso esperara: " + ((int)((range/1000)/60)) + ":" + ((int)((range/1000)%60)));
        Thread.sleep(range);
        esc.escribir("[" + getHour() + "] - Tiempo de espera terinado");
        System.out.println("[" + getHour() + "] - Tiempo de espera terinado");
    }

    private static String getHour(){
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        return hourFormat.format(date);
    }
}