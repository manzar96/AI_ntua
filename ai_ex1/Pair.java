// Some of the code above has been copied from the link below
// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java

import java.util.Objects;

public final class Pair<A, B> {

     public final A a;
     public final B b;

     private Pair(A a, B b) {

	        this.a = a;
	        this.b = b;

     }

     public static <C, D> Pair<C, D> valueOf(C c, D d) {

    	   	return new Pair<C, D>(c, d);

     }

     public A getA() {

	        return a;
     }


     public B getB() {

	        return b;
     }

     @Override
     public String toString() {

	        return "(" + a + ", " + b + ")";
     }


     @Override
     public boolean equals(Object other) {

	if( this == other ) return true;

    	if (!( other instanceof Pair ) ) return false;

	Pair<?,?> that = (Pair<?,?>) other;
    	return	( Objects.equals(this.a, that.a) && Objects.equals(this.b, that.b) );

     }


     @Override
     public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((a == null) ? 0 : a.hashCode());
        result = prime * result + ((b == null) ? 0 : b.hashCode());
        return result;

     }
}
