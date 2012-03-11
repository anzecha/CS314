/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 02 Sep 09; 27 Jan 10
 *          05 Feb 10; 16 Jul 10; 02 Sep 10; 13 Jul 11
 */


/*
 *	Daniel Monroy
 *	UTEID: dm35729
 *	Assignment 2
 *	February 2, 2012
 * 	Section:52870
 */
public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // make a new Cons and put the arguments into it
    // add one new thing to the front of an existing list
    // cons("a", list("b", "c"))  =  (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // test whether argument is a Cons
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first thing in a list:    first(list("a", "b", "c")) = "a"
    // safe, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing:
    //    rest(list("a", "b", "c")) = (b c)
    // safe, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    
    //length of list
    public static int length (Cons arg) {
    	  int n = 0;
    	  for (Cons lst=arg; lst != null; lst = rest(lst) )
    	      n++;
    	  return n; }

    // second thing in a list:    second(list("a", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list:    third(list("a", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively replace the first
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively replace the rest
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of things:   list("a", "b", "c") = (a b c)
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

    /* Sum of squares of integers from 1..n */
    public static int sumsq (int n) 
    {
    	if(n == 1)
    		return 1;
    	else
    		return ((n) * n + sumsq(n - 1));
    }

    /* Addition using Peano arithmetic */
    public static int peanoplus(int x, int y) 
    {
    	if(y == 0)
    		return x;
    	else
    		++x;
    		--y;
    		return peanoplus(x, y);
    }

    /* Multiplication using Peano arithmetic */
    public static int peanotimes(int x, int y) 
    {
    	if (y == 1)
    		return x;
    	else if (x == 1)
    		return y;
    	else
    		return peanoplus(x, peanotimes(x, --y));
    }

    /* n choose k: distinct subsets of k items chosen from n items */
    public static int choose(int n, int k) 
    {
    	if(n == k || n== 0 || k == 0)
    		return 1;
    	else if(n == 1)
    		return 0;
    	else
    		return choose_help(n, 2, (n-k+1), n);
    } 
    
    public static int choose_help(int n, int k, int min, int ans)
    {
    	if (n == min)
    		return ans;
    	else
    		ans = (ans * (n-1)) /k;
    		return choose_help(--n, ++k, min, ans);
    }

/* Add up a list of Integer */
/* iterative version, using while */
public static int sumlist (Cons lst) {
  int sum = 0;
   while ( lst != null ) {
      sum += (Integer) first(lst);   // cast since first() can be Object
    lst = rest(lst); }
  return sum; }

/* second iterative version, using for */
public static int sumlistb (Cons arg) {
  int sum = 0;
  for (Cons lst = arg ; lst != null; lst = rest(lst) )
    sum += (Integer) first(lst);   // cast since first() can be Object
  return sum; }

/* recursive version */
public static int sumlistr (Cons lst) 
{
	if(lst == null)
		return 0;
	else
		return (Integer) first(lst) + sumlistr(rest(lst));
}

/* tail recursive version */
public static int sumlisttr (Cons lst) 
{
	if (lst == null)
		return 0;
	else
		return sumlisttr_help(lst, 0);
}

public static int sumlisttr_help(Cons lst, int sum)
{
	if(lst == null)
		return sum;
	else
		sum += (Integer) first(lst);
		return sumlisttr_help(rest(lst), sum);
}

/* Sum of squared differences of elements of two lists */
/* iterative version */
public static int sumsqdiff (Cons lst, Cons lstb) 
{
	int sum = 0;
	while( lst != null)
	{
	    sum += square((Integer)first(lst) - (Integer)first(lstb));   // cast since first() can be Object
		lstb = rest(lstb);
		lst = rest(lst);
	}
	  return sum;
}

/* recursive version */
public static int sumsqdiffr (Cons lst, Cons lstb) 
{
	if(lst == null)
		return 0;
	else
		return square((Integer)first(lst) - (Integer)first(lstb)) + sumsqdiffr(rest(lst), rest(lstb));
}

/* tail recursive version */
public static int sumsqdifftr (Cons lst, Cons lstb) 
{
	if(lst == null)
		return 0;
	else
		return sumsqdifftr2(lst, lstb, 0);
}

public static int sumsqdifftr2 (Cons lst, Cons lstb, int sum)
{
	if(lst == null)
		return sum;
	else
		sum += square((Integer)first(lst) - (Integer)first(lstb));
		return sumsqdifftr2 (rest(lst), rest (lstb), sum);
}

/* Find the maximum value in a list of Integer */
/* iterative version */
public static int maxlist (Cons lst) 
{
	int max = 0;
	while(lst != null)
	{
		if((Integer)first(lst) > max)
		{
			max = (Integer) first(lst);
			lst = rest(lst);
		}
		else
			lst = rest(lst);
	}
	return max;
}

/* recursive version */
public static int maxlistr (Cons lst) 
{
	if(length(lst) == 2)
	{
		if((Integer)first(lst) >= (Integer)second(lst))
			return (Integer) first(lst);
		
		return (Integer) second(lst);
	}
	
	else
		if((Integer)first(lst) >= (Integer)second(lst))
			return (maxlistr(rest(rest(lst))));
		else
			return maxlistr(rest(lst));
}

/* tail recursive version */
public static int maxlisttr (Cons lst) 
{
	if(lst == null)
		return 0;
	else
		return maxlisttr2(lst, 0);
}

public static int maxlisttr2 (Cons lst, int max)
{
	if(lst == null)
		return max;
	else
		if((Integer) first(lst) > max)
			max = (Integer) first(lst);
		return maxlisttr2(rest(lst), max);
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

//	public static void binomialprint(int n)
//	{
//		
//		int[][] ints = binomial2(n);
//		for (int[] is : ints) {
//		   System.out.println(Arrays.toString(is));
//		}
//	}
    
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


    // ****** your code ends here ******


    public static void main( String[] args )
      { 
        System.out.println("sumsq(5) = " + sumsq(5));

        System.out.println("peanoplus(3, 5) = " + peanoplus(3, 5));
        System.out.println("peanotimes(3, 5) = " + peanotimes(3, 5));
        System.out.println("peanotimes(30, 30) = " + peanotimes(30, 30));

        System.out.println("choose 5 3 = " + choose(5, 3));
        System.out.println("choose 100 3 = " + choose(100, 3));
        System.out.println("choose 20 10 = " + choose(20, 10));
        System.out.println("choose 100 5 = " + choose(100, 5));
        for (int i = 0; i <= 4; i++)
          System.out.println("choose 4 " + i + " = " + choose(4, i));

        Cons mylist = list(Integer.valueOf(3), Integer.valueOf(4),
                           Integer.valueOf(8), Integer.valueOf(2));
        Cons mylistb = list(Integer.valueOf(2), Integer.valueOf(1),
                           Integer.valueOf(6), Integer.valueOf(5));

        System.out.println("mylist = " + mylist);

        System.out.println("sumlist = " + sumlist(mylist));
        System.out.println("sumlistb = " + sumlistb(mylist));
        System.out.println("sumlistr = " + sumlistr(mylist));
        System.out.println("sumlisttr = " + sumlisttr(mylist));

        System.out.println("mylistb = " + mylistb);

        System.out.println("sumsqdiff = " + sumsqdiff(mylist, mylistb));
        System.out.println("sumsqdiffr = " + sumsqdiffr(mylist, mylistb));

        System.out.println("sumsqdifftr = " + sumsqdifftr(mylist, mylistb));

        System.out.println("maxlist " + mylist + " = " + maxlist(mylist));
        System.out.println("maxlistr = " + maxlistr(mylist));
        System.out.println("maxlisttr = " + maxlisttr(mylist));

        System.out.println("binomial(4) = " + binomial(4));
        System.out.println("binomial(20) = " + binomial(20));
      }

}