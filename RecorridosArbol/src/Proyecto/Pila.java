package Proyecto;
public class Pila{
    private Object v[];
    private int tope, max;
    public Pila(){
        max = 100;
        v = new Object[max];
        tope = -1;
    }
    public Pila(int max){
        this.max = max;
        v = new Object[max];
        tope = -1;
    }
    public boolean vacia(){
        return tope == -1;
    }
    public boolean llena(){
        return tope == max-1;
    }
    public void poner(Object dato){
        if(!llena())
           v[++tope] = dato;
        else
           System.out.println("Pila Llena...!");
    }
    public Object quitar(){
        Object dato = null;
        if(!vacia())
           dato = v[tope--];
        else
           System.out.println("Pila Vacía...!");
        return dato;
    }
    public Object cima(){
    	if(!vacia())
    	   return v[tope];
    	else
    	   return null;   
    }	
}
