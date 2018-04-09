import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Read{

    private Scanner entrada;
    private String endSec;
    private boolean msg;

    public Read(){
        config("@",true);
    }
    
    public void config(String endSec, boolean msg){
        this.endSec = endSec;
        this.msg = msg;
    }

	public void abrir( String archivo ){
		try{
			entrada = new Scanner( new File( archivo ));
		}catch( FileNotFoundException e){
			if(msg){
                System.err.println("Error al abrir el archivo");
            }
		}
	}

	public String leer(){
		try{
			if(entrada.hasNext()){
				return entrada.nextLine();
			}else{
				return endSec;
			}
		}catch( NoSuchElementException e){
			if(msg){
                System.err.println("El archivo no esta bien formado");
            }
			entrada.close();
		}catch( IllegalStateException e){
            if(msg){
                System.err.println("Error al leer el archivo");
            }
            entrada.close();
		}
		return "";
	}

	public void cerrar(){
		if(entrada != null){
			entrada.close();
		}
	}
}