/*
 * 	Daniel Monroy
 *	UTEID: dm35729
 *	Assignment 3
 *	February 10, 2012
 * 	Section:52870
 */


/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

/* convert a list to a string for printing */
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    // ****** your code starts here ******


/* iterative version */
public static int sum (Cons lst) 
{
	int result = 0;
	if(lst == null)
		return result;
	else
		return (Integer)first(lst) + (Integer)sum(rest(lst));
}

    /* mean = (sum x[i]) / n   */
public static double mean (Cons lst) 
{
	int count = 0;
	int top = 0;
	return mean2(lst, count, top);
}

public static double mean2 (Cons lst, int count, int top)
{
	while(lst != null)
	{
		count++;
		top = top + (Integer)first(lst);
		return mean2(rest(lst), count, top);
	}
	return (double) top/count;
}

    /* square of the mean = mean(lst)^2   */

    /* mean square = (sum x[i]^2) / n   */
public static double meansq (Cons lst) 
{
	int count = 0;
	int top = 0;
	return meansq2(lst, count, top);
}

public static double meansq2 (Cons lst, int count, int top)
{
	while(lst != null)
	{
		count++;
		top = top + square((Integer)first(lst));
		return meansq2(rest(lst), count, top);
	}
	return (double) top/count;
}

public static double variance (Cons lst) 
{
	return meansq(lst) - (mean(lst) * mean(lst));
}

public static double stddev (Cons lst) 
{
	return Math.sqrt(variance(lst));
}

public static double sine (double x) 
{
	return sine2(x, 3, 1, 0, x, 1);
}


public static double sine2(double x, int n, int count, double ans, double top, int bottom)
{
	if(count >= 11)
	{
		return ans + x;
	}
	else
	{
		top *= x*x;
		bottom *= (-1) * n * (n-1);
		ans += top / bottom;
		n +=2;
		count++;
		return sine2(x, n, count, ans, top, bottom);
	}
}

public static Cons nthcdr (int n, Cons lst) 
{
	if(lst == null)
		return null;
	else
	{
		Cons lst2 = null;
		return nthcdr2(n, lst, lst2);
	}
}

public static Cons nthcdr2(int n, Cons lst, Cons lst2) 
{
	for(int i = 1; i <=n; i++)
	{
		if(lst != null)
		{
			lst = rest(lst);
			lst2 = lst;
		}
		else
			break;
	}
	return lst2;
}

public static Object elt (Cons lst, int n) 
{
	return first(nthcdr(n, lst));
}

public static double interpolate (Cons lst, double x) 
{
	int compare = (int) Math.floor(x);
	double delta = x - compare; 
	int low = (Integer) elt(lst, compare);
	int high = (Integer) elt(lst, compare+1);
	return low + delta * (high - low);
}

/* Make a list of Binomial coefficients */
/* binomial(2) = (1 2 1) */
public static Cons binomial(int n) 
{
	if(n == 1)
	{
		Cons one = list(Integer.valueOf(1), Integer.valueOf(1));
		return one;
	}
	else
		return binomialcons(n);
	
}

//public static void binomialprint(int n)
//{
//	
//	int[][] ints = binomial2(n);
//	for (int[] is : ints) {
//	   System.out.println(Arrays.toString(is));
//	}
//}

public static Cons binomialcons(int n)
{
	
	int [][] use = binomial2(n);
	Cons result=null;
	for(int i = 1; i <= n+1; i++)
	{
		result = cons(use[n][i] ,result);
	}
	return result;
}


public static int[][] binomial2(int n)
{
	int [][] ans = new int[n+3][n+3];
	ans[0][0] = 0;
	ans[0][1] = 1;
	ans[0][2] = 0;
	for(int x = 1; x <= n; x++)
	{
		int counter = ans[x-1].length;
		ans[x][0] = 0;
		ans[x][counter-1] = 0;
		for(int i = 1; i < (counter-1); i++)
		{
			ans[x][i] = (ans[x-1][i-1] + ans[x-1][i]);
		}
	}
	return ans;
}

public static int sumtr (Cons lst) 
{
	if(lst == null)
		return 0;
	else 
		return sumtr2(lst, 0);
}
public static int res = 0;
public static int sumtr2(Cons lst, int result)
{
	while(lst != null)
	{
		if(!consp(first(lst)))
		{
			res += (Integer)first(lst);
			return sumtr2(rest(lst), res);
		}
		else
		{
			result += sumtr2((Cons) first(lst), res);
			return sumtr2(rest(lst), res);
		}
	}
	return res;
}

    /* use auxiliary functions as you wish. */
public static Cons subseq (Cons lst, int start, int end) 
{
	Cons lst2 = null;
	
	for(int i = start; i < end; i++)
	{
		lst2 = cons(elt(lst, i), lst2);
	}
	return trrev(lst2);
}

//Reverse the list ^
public static Cons trrev (Cons lst) {
	  return trrevb(lst, null); }

	public static Cons trrevb (Cons in, Cons out) {
	  if ( in == null )
	     return out;
	   else return trrevb( rest(in),
	                       cons(first(in), out) ); }

	
public static Cons posfilter (Cons lst) 
{
	Cons lst2 = null;
	
	if(lst == null)
		return null;
	else
		return trrev(posfilter2(lst, lst2));
 
}

public static Cons posfilter2(Cons lst, Cons lst2)
{
	if(lst == null)
		return lst2;
	else
	{
		if((Integer)first(lst) >= 0)
		{
			lst2 = cons(first(lst), lst2);
			return posfilter2(rest(lst), lst2);
		}
		else
			return posfilter2(rest(lst), lst2);
	} 
}

public static Cons subset (Predicate p, Cons lst) 
{
	Cons lst2 = null;
	
	if(lst == null)
		return lst2;
	else
		return trrev(subset2(p, lst, lst2));
}

public static Cons subset2 (Predicate p, Cons lst, Cons lst2)
{
	if(lst == null)
		return lst2;
	
	if(p.pred(first(lst)))
	{
		lst2 = cons(first(lst), lst2);
	}
	return subset2(p, rest(lst), lst2);
}


public static Cons mapcar (Functor f, Cons lst) 
{
	Cons lst2 = null;
	
	if(lst == null)
		return lst2;
	else
		return trrev(mapcar2(f, lst, lst2));
}

public static Cons mapcar2 (Functor f, Cons lst, Cons lst2) 
{
	if(lst == null)
		return lst2;
	
	lst2 = cons(f.fn(first(lst)), lst2);
	return mapcar2(f, rest(lst), lst2);
}

public static Object some (Predicate p, Cons lst) 
{
	if(lst == null)
		return null;
	
	if(p.pred(first(lst)))
		return first(lst);
	else
		return some(p, rest(lst));
}

public static boolean every (Predicate p, Cons lst) 
{
	if(lst == null)
		return true;
	else if(!p.pred(first(lst)))
		return false;
	
	return every(p, rest(lst));
}

    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons mylist =
            list(new Integer(95), new Integer(72), new Integer(86),
                 new Integer(70), new Integer(97), new Integer(72),
                 new Integer(52), new Integer(88), new Integer(77),
                 new Integer(94), new Integer(91), new Integer(79),
                 new Integer(61), new Integer(77), new Integer(99),
                 new Integer(70), new Integer(91) );
        System.out.println("mylist = " + mylist.toString());
        System.out.println("sum = " + sum(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist));
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.print("nthcdr 5 = ");
        System.out.println(nthcdr(5, mylist));
        System.out.print("nthcdr 18 = ");
        System.out.println(nthcdr(18, mylist));
        System.out.println("elt 5 = " + elt(mylist,5));

        Cons mylistb = list(new Integer(0), new Integer(30), new Integer(56),
                           new Integer(78), new Integer(96));
        System.out.println("mylistb = " + mylistb.toString());
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom.toString());
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(new Integer(1),
                            list(new Integer(2), new Integer(3)),
                            list(list(new Integer(4), new Integer(5)),
                                 new Integer(6)));
        System.out.println("mylistc = " + mylistc.toString());
        System.out.println("sumtr = " + sumtr(mylistc));

        Cons mylistd = list(new Integer(0), new Integer(1), new Integer(2),
                            new Integer(3), new Integer(4), new Integer(5),
                            new Integer(6) );
        System.out.println("mylistd = " + mylistd.toString());
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(new Integer(3), new Integer(17), new Integer(-2),
                            new Integer(0), new Integer(-3), new Integer(4),
                            new Integer(-5), new Integer(12) );
        System.out.println("myliste = " + myliste.toString());
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, mylistd).toString());

        System.out.println("some = " + some(myp, myliste).toString());

        System.out.println("every = " + every(myp, myliste));

      }

}