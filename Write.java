import java.io.FileNotFoundException;
import java.io.File;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Write{
    
	private Formatter salida;
	private String arch;
    private File arc;
    private boolean msg;

    public Write(){
        config(true);
    }

    public void config(boolean msg){
        this.msg = msg;
    }

	public void abrir(String archivo){
		try{
			arch = archivo;
			salida = new Formatter( archivo );
			arc = new File( archivo );
		}catch(SecurityException e){
			if(msg){
                System.err.println("No tiene acceso al archivo");
            }
		}catch(FileNotFoundException e){
            if(msg){
                System.err.println("Error al crear el archivo");
            }
		}
	}

	public void escribir(String s){
            try{
                salida.format("%s\n", s);
            }catch(FormatterClosedException e){
                if(msg){
                    System.err.println("Error al escribir en el archivo");
                }
            }catch(NoSuchElementException e){
                if(msg){
                    System.err.println("Entrada invalida");
                }
            }
	}

	public void cerrar(){
		if(salida != null){
			salida.close();
		}
	}

	public void vacio(){
		escribir("");
		cerrar();
		abrir( arch );
	}

	public void borrar(){
		cerrar();
		if(!arc.delete()){
            if(msg){
                System.out.println("El archivo no se pudo borrar");
            }
		}
	}
}