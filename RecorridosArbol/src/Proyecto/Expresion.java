package Proyecto;
public class Expresion{
      private String exp;
      public Expresion(){
            exp = null;
      }
      public Expresion(String exp){
            this.exp = exp;
      }
      public void infija(String exp){
      	   this.exp = exp;
      }
      public String prefija(){
      	   Character c,d,e;
      	   int i,prioridadCima,prioridadOper;
      	   String expPre = new String();
           Pila aux = new Pila();
  		   Pila med = new Pila();
		   Pila pre = new Pila();
		   for(i=0;i<exp.length();i++)
			   aux.poner(new Character(exp.charAt(i)));
		   while(!aux.vacia()){
			     c = (Character) aux.quitar();
			     if(c.charValue() == ')'){
					med.poner(c);
			     }else if(c.charValue() == '('){
			     	e = (Character) med.cima();
					while(e.charValue() != ')'){
						  c = (Character) med.quitar();
						  pre.poner(c);
						  e = (Character) med.cima();
					}
					c = (Character) med.quitar();
				 }else if(operador(c.charValue())){
		                  e = (Character) med.cima(); 
		                  prioridadCima = prioridad(e);
					  	  prioridadOper = prioridad(c);
						  while(!med.vacia() && (prioridadOper < prioridadCima)){
						   	    d = (Character) med.quitar();
								pre.poner(d);
								e = (Character) med.cima();
								prioridadCima = prioridad(e);
						  }
						  med.poner(c);
				 }else
				    pre.poner(c);
		 }
		 while(!med.vacia()){
			   c = (Character) med.quitar();
			   pre.poner(c);
		 }
		 while(!pre.vacia()){
		 	   c = (Character) pre.quitar(); 
			   expPre += c.charValue();
		 }	   
   	     return expPre;
      }
      
      public String postfija(){
      	   //aqui instruciones
      	   return "En Construccion..!";
      }
      private boolean operador(char c){
      	   char operadores[] = {'+','-','*','/','^'};
      	   boolean op = false;
      	   for(int i=0;(i<5) && (!op);i++)
      	       if(operadores[i] == c)	    
      	          op = true;
      	   return op; 
      }
      private int prioridad(Character op){
  		   int r = 4;
  		   if(op != null)
		      switch(op.charValue()){
			   	     case ')' : r = 0;
				     case '(' : r = 0; break;
				     case '+' : r = 1;
				     case '-' : r = 1; break;
				     case '*' : r = 2;
				     case '/' : r = 2; break;
				     case '^' : r = 3;
		      }  
		   return r;
      }
}
