package expressions;
import java.util.*;

import expressions.Number;

class Multiply implements Expression{

	private final Expression left;
	private final Expression right;
	
	Multiply(Expression left, Expression right){
	   this.left = left;
	   this.right = right;
	}
	
	public String toString(){
	    return "(" + this.left.toString() + " * " + this.right.toString() + ")";
	}


    public List<Expression> terms(){
        List <Expression> terms = new ArrayList<>();
        terms.add(this);
        return terms;
    };
    
// jia zhushi
    public List<Expression> factors(){
        List<Expression> factors = new ArrayList<>();
        factors.addAll(this.left.factors());
        factors.addAll(this.right.factors());
        return factors;
    };
    
 
    public Expression differentiate(Expression var){
        return Expression.sum(
                Expression.times(left,right.differentiate(var)),
                Expression.times(left.differentiate(var),right)
             );
    }
    
    public Expression simplify(Map<Expression,Double>values){
        Expression simpExp = Expression.times(left.simplify(values), right.simplify(values));
        Number numFactor = new Number(simpExp.numFactor());
        Expression varFactor = simpExp.varFactor();
        return Expression.times(numFactor,varFactor);
    }
    
    public double numTerm(){
        return 0;
    }
    
    public double numFactor(){
        return left.numFactor()*right.numFactor();
    }
   
    public Expression varTerm(){
        return this;
    }
    
    public Expression varFactor(){
        return Expression.times(left.varFactor(),right.varFactor());
    }
	
	public boolean equals(Object other){
	    if(!(other instanceof Multiply)){return false;}
	    Multiply otherMult = (Multiply)other;
	    //return (this.factors().equals(otherMult.factors()));
	    return (this.left.equals(otherMult.left) && this.right.equals(otherMult.right));
	}
	
	public int hashCode(){     
	    int result = 43;
        result = 37*result + left.hashCode();
        result = 37*result + right.hashCode();
        return result;
    }
	
   public static void main(String[] args) {
        Number n = new Number(5);
        Variable v = new Variable("x");
        Plus pl1 = new Plus(n,v); 
        Plus pl2 = new Plus(n,new Variable("y")); 
        Map<Expression,Double> m = new HashMap<>();
        m.put(new Variable("x"), 3.0); 
        System.out.println(Expression.times(n,Expression.sum(n,v)).simplify(m)); 
    }
}
