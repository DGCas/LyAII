package Proyecto;
class ArbolExp{
		private Nodo raiz;
		public ArbolExp(){raiz = null;}
        public ArbolExp(String expPre){
		       Nodo n, q = new Nodo(new Character(expPre.charAt(0)));
		       Pila p = new Pila();
		       boolean antesOperando = false;
		       raiz = q;
		       for(int i=1;i<expPre.length();i++){
					n = new Nodo(new Character(expPre.charAt(i)));
					if(antesOperando){
						 q = (Nodo) p.quitar();
						 q.der = n;
					}else{
						 q.izq = n;
						 p.poner(q);
					}
					q = n;
					if(operador(expPre.charAt(i)))
						 antesOperando = false;
					else
						 antesOperando = true;
		       }
        }
        public Nodo getRaiz(){
        	return raiz;
        }
        private boolean operador(char c){
        	char operadores[] = {'+','-','*','/','^'};
        	boolean encontrado = false;
        	for(int i=0; i<5 && !encontrado; i++)
        	    if(c == operadores[i])
        	       encontrado = true;
        	return encontrado;
       	}
}
