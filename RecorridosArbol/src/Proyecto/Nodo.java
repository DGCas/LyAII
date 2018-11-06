package Proyecto;
public class Nodo{
	private Object dato;
	public Nodo izq, der;
	public Nodo(Object dato, Nodo izq, Nodo der){
	       this.dato = dato;
	       this.izq = izq;
	       this.der = der;
	}
	public Nodo(Object dato){
	       this.dato = dato;
	       izq = null;
	       der = null;
	}
	public Object getDato(){
		return dato;
	}
}