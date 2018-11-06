package Proyecto;

import java.util.Scanner;

class Principal{
	public static void main(String[] args){
		Scanner exp = new Scanner(System.in);
		System.out.println("Teclea la expresion");
		String cadena = exp.nextLine();
		Expresion e = new Expresion(cadena);
		ArbolExp a = new ArbolExp(e.prefija());
		System.out.print("PreOrden : ");
		Recorrido.preOrden(a.getRaiz()); 
		System.out.print("\nInOrden : ");
		Recorrido.inOrden(a.getRaiz()); 
		System.out.print("\nPostOrden : ");
		Recorrido.postOrden(a.getRaiz()); 
		System.out.println();
	}
}