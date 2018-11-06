package Proyecto;
public class Recorrido{
	public static void preOrden(Nodo p){
		if(p!=null){
			System.out.print(p.getDato());
			preOrden(p.izq);
			preOrden(p.der);
		}
	}
	public static void inOrden(Nodo p){
		if(p!=null){
			inOrden(p.izq);
			System.out.print(p.getDato());
			inOrden(p.der);
		}
	}
	public static void postOrden(Nodo p){
		if(p!=null){
			postOrden(p.izq);
			postOrden(p.der);
			System.out.print(p.getDato());
		}
	}
}