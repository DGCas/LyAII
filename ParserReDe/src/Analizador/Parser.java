package Analizador;
import java.util.*;
import Semantica.*;
import Errores.*;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Parser {
	final int Int = 1, Float = 2, Semi = 3, Equal = 4, If = 5, THEN = 6, ELSE = 7, beginx=8, endx =9 ,printx = 10, Id = 11;
	int tokenCode, cont, noLinea;
	String  token;
        //String intermedio;
	LinkedList<String> intermedio;
	LinkedList<Integer> condiciones;
	LinkedList<String> linea;
	LinkedList<Declaration> declarations;
	LinkedList<Statement> statutos;
	LinkedList<LinkedList<String>> tokens;
	
	JTree program;
	DefaultMutableTreeNode declaraciones;
	DefaultMutableTreeNode declaracion;
	DefaultMutableTreeNode statements;
	DefaultMutableTreeNode statement;
	DefaultMutableTreeNode ifStatement;
	
        
	
	public Parser (LinkedList<LinkedList<String>> tokens){
		this.tokens = tokens;
		declarations = new LinkedList<Declaration> ();
		statutos = new LinkedList <Statement> ();
		declaraciones = new DefaultMutableTreeNode ();
		statements = new DefaultMutableTreeNode ();
                cont = 0;
		noLinea = 0;
		intermedio = new LinkedList<String>();
		condiciones = new LinkedList<Integer>();
	}
	
	public void ComenzarParser() throws UnexpectedTokenException, SemanticError{
             
		Declarations();
		Statements();
                ImprimeArbol();
                System.out.println("Exito!\n");
		AnalizadorSemantico a = new AnalizadorSemantico(declaraciones, statements);
	}
	
	public void ImprimeArbol(){
		System.out.print("Declaraciones de variables.");
		program  = new JTree (declaraciones);
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) program.getModel().getRoot();
		Enumeration < ? > e = raiz.preorderEnumeration();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
		System.out.print("Estatutos.");
		program = new JTree(statements);
		raiz = (DefaultMutableTreeNode) program.getModel().getRoot();
		e = raiz.preorderEnumeration();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
	}
	
	//Toma la siguiente linea de la lista de tokens.
	public void siguienteLinea (){
		if (tokens.isEmpty())
			return;
		linea = tokens.removeFirst();
		noLinea ++;
	}
	
	void Avanza(){
		if(token == null)
			siguienteLinea();
		token = linea.pollFirst();
		if(token == null)
			return;
		tokenCode = StringToCode(token);
	}
	
	void Come (int token) throws UnexpectedTokenException{
		if(tokenCode == token){
			Avanza();
		}
		else {
			throw new UnexpectedTokenException("Token inesperado en la linea "+ noLinea + ".\nCodigo de token esperado: "+ token + ".\nToken encontrado: " + tokenCode);
		}
	}
	
	void Declarations () throws UnexpectedTokenException{
		Avanza();
		//Verifica que tipo de token es el que se utiliza, si no se sale del m�todo.
		if(tokenCode == Int || tokenCode == Float){
			String type = token;
			Avanza();
			String identifier = token;
			Come(Id);
			Come(Semi);
			//Agrega a la lista de declaraciones una nueva declaraci�n.
			declarations.add(new Declaration(new Type(type),new Identifier (identifier)));
			declaracion = new DefaultMutableTreeNode(type);
			//declaracion.add(new DefaultMutableTreeNode("->" + identifier));
			declaracion.add(new DefaultMutableTreeNode(identifier));
			declaraciones.add(declaracion);
			Declarations();
		}
	}
	//M�todo para la creacion y verificacion de los estatutos, regresa un estatuto.
	Statement Statements() throws UnexpectedTokenException{
		//Pregunta si hay declaraciones o no, ya que lo ultimo que hace este es comer, y el come avanza automaticamente.
		switch(tokenCode){
                        case beginx:
                            Come(beginx);
                            Avanza();
                            Statements();
                            Last();
                            break;    
			//Aqui se verifica el if.
			case If:
                            
				Come(If);
                                Comparation e= (Comparation) Expresion();
				Avanza();
                                statement = new DefaultMutableTreeNode ("If");
				
                                statement.add(new DefaultMutableTreeNode(e.getExp1()));
                                statement.add(new DefaultMutableTreeNode(e.getSimbolo()));
                                statement.add(new DefaultMutableTreeNode(e.getExp2()));
				statements.add(statement);
                                Come(THEN);
                                Statements();
//Crea un objeto de tipo expresion, al llamar al metodo para crear la expresion.
				//Statements();
                                Avanza();
                                Come(ELSE);
                                Statements();
	
                               
				break;    
                        case printx:
                           
                            Come(printx);
                            Comparation ex= (Comparation) Expresion();
                     //
                           
                            statement = new DefaultMutableTreeNode ("print");
                            statement.add(new DefaultMutableTreeNode(ex.getSimbolo()));
                            statement.add(new DefaultMutableTreeNode(ex.getExp1()));
                            statement.add(new DefaultMutableTreeNode(ex.getExp2()));
                            statements.add(statement);
                        break;
			default:
				if(token == null)
					return null;
				throw new UnexpectedTokenException("Token inesperado en la linea "+ noLinea + ".\nToken encontrado: " + tokenCode);
		}
		return null;
	}
       void Last ()throws UnexpectedTokenException{
            switch(tokenCode){
                case endx:
                    Come(endx);
                break;
                
                case Semi:
                    Come(Semi);
                    Avanza();
                    Statements();
                    Last();
                    break; 
                default:Error(token);
                    break;
                    //Error(token,"(end|;");
                
            }
        }
        
	//M�todo para realizar la parte de la expression de igualdad.
	Expression Expresion() throws UnexpectedTokenException{
		Identifier identifier = new Identifier(token);
		Come(Id);
		String oper = token;
                Avanza();
		Identifier identifier2 = new Identifier(token);
		Come(Id);
		return new Comparation(identifier, oper, identifier2);
	}
	
	//Cambia una cadena dada que tenga palabra clave a su codigo.
	public int StringToCode(String cad){
		int code = 0;
		switch(cad){
			case "Int":
				code = 1;
				break;
			case "Float":
				code = 2;
				break;
			case ";":
				code = 3;
				break;
			case "=":
				code = 4;
				break;
			case "If":
				code = 5;
				break;
			case "then":
				code = 6;
				break;
			case "else":
				code = 7;
				break;
                        case "Begin":
                                code = 8;
                            break;
                        case "end":
                                code =9;
                            break;
                        case "print":
                                code = 10;
                                break;
			default:
				//Codigo por default para los identificadores
				code = 11;
		}
		return code;
	}
	void Error(String tipo){
		System.out.println("Error en el programa: " + tipo);
	}
}
