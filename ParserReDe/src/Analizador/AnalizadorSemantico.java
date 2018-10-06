package Analizador;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import Errores.*;


public class AnalizadorSemantico {

	JTree arbol;
	DefaultMutableTreeNode declaraciones, estatutos;
	Hashtable<String,String> simbolos;
	//Listas que sirven para los saltos del codigo intermedio
	LinkedList<String> intermedio;
        int linea, auxiliar, salto, salto2, instruc;
	//toma la posicion del ultimo condicional para poder poner el salto correcto.
        	//Sirve para saber cual es el numero de la instruccion.
	

	
	//Constructor con los parametros de el arbol de declaraciones y el arbol de estatutos
	public AnalizadorSemantico(DefaultMutableTreeNode declaraciones, DefaultMutableTreeNode estatutos) throws SemanticError{
		this.declaraciones = declaraciones;
		this.estatutos = estatutos;
		linea = 1;
		intermedio = new LinkedList<String>();

		
               
                auxiliar = 1;
		
		//Crea una hashtable para que sea la tabla de simbolos.
		simbolos = new Hashtable<String, String>();
		Declaraciones();
		//Statutos();
                TransformarEstatutos();
                System.out.println("----TABLA DE SIMBOLOS.----");
		//Transforma todas las keys de una hashtable a enumeration de tipo string.
		Enumeration<String> k = simbolos.keys();
		String key;
		//Mientras existan elementos
		while(k.hasMoreElements()){
			//Toma el siguiente y lo almacena
			key = k.nextElement();
			System.out.println(key + "   "+ simbolos.get(key));
		}
		//Imprime el codigo intermedio
		System.out.println("\nCODIGO INTERMEDIO EN CUADRUPLES.");
		for(String c: intermedio){
			System.out.println(c);
		}
             

	}
	
	//Metodo que hara que tome todos los estatutos y los ponga en la tabla de simbolos.
	void Declaraciones() throws SemanticError{
		//crea un arbol con las declaraciones
		arbol = new JTree(declaraciones);
                
		//toma la raiz de este arbol.
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) arbol.getModel().getRoot();
		//Crea una enumeration para obtener los valores o nodos del arbol
		Enumeration < ? > e = raiz.preorderEnumeration();
		String tipo, ident;
		//El primer elemento esta vacio
		e.nextElement();
		//Recorre el arbol mientras existan elementos, de dos en dos ya que tiene el tipo y el identificador.
		while(e.hasMoreElements()){
			tipo = e.nextElement().toString();
			ident = e.nextElement().toString();
			//A�ade el tipo y el identificador a una hashtable para busqueda mas rapida.
			if(simbolos.get(ident) != null)
				throw new SemanticError("Error Semantico:  '" +ident + "' ya existe");
			simbolos.put(ident, tipo);
		}
               // System.out.println("aqui esta la tabla"+simbolos);
	}
	

	public void TransformarEstatutos()throws SemanticError{
             
               //Toma el arbol de declaraciones
		arbol = new JTree(estatutos);
		//Toma el nodo raiz.
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) arbol.getModel().getRoot();
		//Transforma en enumeration el arbol.
		Enumeration <?> e = raiz.preorderEnumeration();
		//Toma un elemento que es vacio.
		e.nextElement();
		//Mientras existan elementos en el enumeration
		while(e.hasMoreElements()){
			//Llama al metodo para que siga con al ejecucion.
			Intermedio(e);
		}
		//Termina añadiendo una linea final al codigo intermedio.
		intermedio.add(linea+"");
		linea++;
                
	}
        	//Varaibles necesarias.
	String oper, val1, val2, cad,aux,tipo1,tipo2;
	int act, e, llegue;
	String token, sim1, sim2;
        	
        	public void Intermedio(Enumeration<?> t) throws SemanticError{
		//Toma el siguiente token
                
		token = t.nextElement().toString();
                
                //operator = 
		//Pregunta si es alguno de estos tipos
		switch(token){
			case "Begin":
				//llama recursivamente para continuar con el proceso.
				Intermedio(t);
				break;
			case "end" : 
				//Si es end significa que termina
				return;
			case "else":
				//Si es else retorna para continuar el proceso.
				return;
			case "print":
    				//Toma los elementos correspondientes
				aux = t.nextElement().toString();
				val1 = t.nextElement().toString();
				val2 = t.nextElement().toString();
				tipo1 = ""; tipo2 = "";
				//Toma el primer tipo
				tipo1 = simbolos.get(val1);
				if(simbolos.get(val1) == null){
					//Si entra aqui es que no existe y pregunta si es numero, si lo es asigna un int a tipo1
					try{Integer.parseInt(val1);tipo1 = "Int";}
					//Si no puede parsear a entero tira una exepcion.
					catch(Exception err){throw new SemanticError("--Error Semantico--  "+ val1 +" no existe");}
				}
				//Toma el segundo tipo
				tipo2 = simbolos.get(val2);
				if(simbolos.get(val2) == null){
					//Si entra aqui es que no existe y pregunta si es numero, si lo es asigna un int a tipo2
					try{Integer.parseInt(val2);tipo2 = "Int";}
					//Si no puede parsear a entero tira una exepcion.
					catch(Exception err){throw new SemanticError("--Error semantico-- "+ val2 + " no existe");}
				}
				//Si los tipos no son iguales tira una exepcion.
				if(!tipo1.equals(tipo2)){
					throw new SemanticError("--Error semantico-- un tipo "+ tipo2 + " no puede ser transformado a "+tipo1);
				}
				//Añade una instruccion cuadruplo de impresion.
				intermedio.add(linea+"\tPRINT\t\t\t"+val1+aux+val2);
				//Aumenta la linea de iteracion.
				linea++;
				break;
			case "If":
				//Toma los elementos correspondientes
				
				val1 = t.nextElement().toString();
                                aux = t.nextElement().toString();
				val2 = t.nextElement().toString();
				tipo1 = ""; tipo2 = "";
				//Toma el primer tipo
				tipo1 = simbolos.get(val1);
				if(simbolos.get(val1) == null){
					//Si entra aqui es que no existe y pregunta si es numero, si lo es asigna un int a tipo1
					try{Integer.parseInt(val1);tipo1 = "Int";}
					//Si no puede parsear a entero tira una exepcion.
					catch(Exception err){throw new SemanticError("--Error Semantico--  "+ val1 +" no existe");}
				}
				//Toma el segundo tipo
				tipo2 = simbolos.get(val2);
				if(simbolos.get(val2) == null){
					//Si entra aqui es que no existe y pregunta si es numero, si lo es asigna un int a tipo2
					try{Integer.parseInt(val2);tipo2 = "Int";}
					//Si no puede parsear a entero tira una exepcion.
					catch(Exception err){throw new SemanticError("--Error semantico--  "+ val2 + " no existe");}
				}
				//Si los tipos no son iguales tira una exepcion.
				if(!tipo1.equals(tipo2)){
					throw new SemanticError("--Error semantico-- un tipo "+ tipo2 + " no puede ser transformado a "+tipo1);
				}
				//Añade un instruccion al intermedio como resta.
				intermedio.add(linea+ "\t-\t"+val1+"\t"+val2+"\tt"+auxiliar);
				//Aumenta la linea y la variable temporal
				linea ++;
				auxiliar++;
				//Guarda el salto, para ver a que lado saltara.
				salto = intermedio.size();
				//Añade una instruccion incompleta de salto ante un no cero.
				intermedio.add(linea+"\tJNZ\tt"+(auxiliar-1)+"\t\t");
				linea++;
				//Llamado recursivo a opciones para que continue.
				Intermedio(t);
				//Guarda el segundo salto para pasar el else.
				salto2 = intermedio.size();
				//Añade una instruccion incompleta de salto a alguna instruccion.
				intermedio.add(linea+"\tJP\t\t\t");
				linea++;
				//Almacena la linea donde inicia el else.
				instruc = linea;
				//Llamado recursivo a opciones para que continue.
				Intermedio(t);
				//Edita el primer salto ante 0 para que llegue al else.
				intermedio.set(salto, intermedio.get(salto)+instruc);
				//Edita el segundo salto para que pase el else y conitnue.
				intermedio.set(salto2, intermedio.get(salto2)+linea);
				break;
		}
            
	}

}
