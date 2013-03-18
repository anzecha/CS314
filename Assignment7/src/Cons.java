/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 23 Oct 08; 27 Mar 09; 06 Aug 10
 */

/*
 * Daniel Monroy
 * dm35729
 * March 17, 2012
 * CS314
 * 52870
 */

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
    /* access functions for expression representation */
    public static Object op  (Cons x) { return first(x); }
    public static Object lhs (Cons x) { return first(rest(x)); }
    public static Object rhs (Cons x) { return first(rest(rest(x))); }
    public static boolean numberp (Object x)
       { return ( (x != null) &&
                  (x instanceof Integer || x instanceof Double) ); }
    public static boolean integerp (Object x)
       { return ( (x != null) && (x instanceof Integer ) ); }
    public static boolean floatp (Object x)
       { return ( (x != null) && (x instanceof Double ) ); }
    public static boolean stringp (Object x)
       { return ( (x != null) && (x instanceof String ) ); }

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

    public boolean equals(Object other) { return equal(this,other); }

    // tree equality
public static boolean equal(Object tree, Object other) {
    if ( tree == other ) return true;
    if ( consp(tree) )
        return ( consp(other) &&
                 equal(first((Cons) tree), first((Cons) other)) &&
                 equal(rest((Cons) tree), rest((Cons) other)) );
    return eql(tree, other); }

    // simple equality test
public static boolean eql(Object tree, Object other) {
    return ( (tree == other) ||
             ( (tree != null) && (other != null) &&
               tree.equals(other) ) ); }

// member returns null if requested item not found
public static Cons member (Object item, Cons lst) {
  if ( lst == null )
     return null;
   else if ( item.equals(first(lst)) )
           return lst;
         else return member(item, rest(lst)); }

//does the value exist in the tree
public static boolean occurs(Object value, Object tree) 
{
	boolean result = false;
	if(tree == null)
		return result;
	else
		return occurs2(value, tree, result);
	
}
//helper for occurs
public static boolean occurs2(Object value, Object tree, boolean result) 
{
	if(tree == null)
	{
		return result;
	}
	else
	{
		if(!consp(first((Cons)tree)))
		{
			if(value.equals(first((Cons)tree)))
			{
				result = true;
				return result;
			}
		}
		else
		{
			result = occurs2(value, first((Cons)tree), result);
		}
		return occurs2(value, rest((Cons)tree), result);
	}
}

public static Cons union (Cons x, Cons y) {
  if ( x == null ) return y;
  if ( member(first(x), y) != null )
       return union(rest(x), y);
  else return cons(first(x), union(rest(x), y)); }

public static boolean subsetp (Cons x, Cons y) {
    return ( (x == null) ? true
             : ( ( member(first(x), y) != null ) &&
                 subsetp(rest(x), y) ) ); }

public static boolean setEqual (Cons x, Cons y) {
    return ( subsetp(x, y) && subsetp(y, x) ); }

    // combine two lists: (append '(a b) '(c d e))  =  (a b c d e)
public static Cons append (Cons x, Cons y) {
  if (x == null)
     return y;
   else return cons(first(x),
                    append(rest(x), y)); }

    // look up key in an association list
    // (assoc 'two '((one 1) (two 2) (three 3)))  =  (two 2)
public static Cons assoc(Object key, Cons lst) {
  if ( lst == null )
     return null;
  else if ( key.equals(first((Cons) first(lst))) )
      return ((Cons) first(lst));
          else return assoc(key, rest(lst)); }

    public static int square(int x) { return x*x; }
    public static int pow (int x, int n) {
        if ( n <= 0 ) return 1;
        if ( (n & 1) == 0 )
            return square( pow(x, n / 2) );
        else return x * pow(x, n - 1); }

public static Cons formulas = 
    list( 
    	  list( "=", "s", list("*", new Double(0.5), list("*", "a", list("expt", "t", new Integer(2)))))
    	  ,
          list( "=", "s", list("+", "s0", list( "*", "v", "t")))
          ,
          list( "=", "a", list("/", "f", "m"))
          ,
          list( "=", "v", list("*", "a", "t"))
          ,
          list( "=", "f", list("/", list("*", "m", "v"), "t"))
          ,
          list( "=", "f", list("/", list("*", "m", list("expt", "v", new Integer(2))), "r"))
          ,
          list( "=", "h", list("-", "h0", list("*", new Double(4.94), list("expt", "t", new Integer(2)))))
          ,
          list( "=", "c", list("sqrt", list("+", list("expt", "a", new Integer(2)), list("expt", "b", new Integer(2)))))
          ,
          list( "=", "v", list("*", "v0",  list("-", new Double(1.0), list("exp", list("/", list("-", "t"), list("*", "r", "c"))))))
        );

    // Note: this list will handle most, but not all, cases.
    // The binary operators - and / have special cases.
public static Cons opposites = 
    list( list( "+", "-"), list( "-", "+"), list( "*", "/"),
          list( "/", "*"), list( "sqrt", "expt"), list( "expt", "sqrt"),
          list( "log", "exp"), list( "exp", "log") );

public static Cons operators = list("=", "+", "*", "-", "/", "expt", "sqrt", "exp", "log");

public static void printanswer(String str, Object answer) {
    System.out.println(str +
                       ((answer == null) ? "null" : answer.toString())); }

    // ****** your code starts here ******


public static Cons findpath(Object item, Object cave) 
{
		if(equal(item, cave))
			return list("\"done\"");
		else if(cave == null)
			return null;
		else
		{
//			Cons result = null;
//			result = findpath2(item, cave, result);
//			return append(result, list("\"done\""));
			return findpath_mouse(item, cave);
		}
		
}


////Does NOT work
//public static Cons findpath2(Object item, Object cave, Cons result)
//{
//	/*
//	if(consp(cave))
//	{
//		if(eql(first((Cons)cave), item))
//			return append(result, list("\"first\"", "\"done\""));
//		else
//		{
//			result = append(result, list("\"rest\""));
//			return findpath2(item, rest((Cons)cave), result);
//		}
//			
//	}
//	else
//	{
//		if(!equal(item,cave))
//			return null;
//		else
//			return result;
//	}*/
//	//tried using the same structure as in assignment 6 maxbt
//	if(cave == null)
//	{
//		return result;
//	}
//	else
//	{
//		if(!consp(first((Cons)cave)))
//		{
//			if(eql(first((Cons)cave), item))
//				return append(result, list("\"first\""));
//			
//		}
//		else
//		{
//			return findpath2(item, first((Cons)cave), result);
//		}
//		return findpath2(item, rest((Cons)cave), append(result, list("\"rest\"")));
//	}
//	
//}

public static Cons findpath_mouse(Object item, Object cave)
{
//	if ( maze[y][x].equals("*") ) 
//		return null;
//    Cons here = list(new Integer(x),new Integer(y));
//	if ( memberv(here, prev) != null ) 
//		return null;
//	else if ( maze[y][x].equals("C") )
//	    return list("C");
//	else if ((path = mouse(maze, x - 1, y, cons(here,prev))) != null )
//	    return cons("W", path);
//	else if ((path = mouse(maze, x, y - 1, cons(here,prev))) != null )
//	    return cons("N", path);
//	else if ((path = mouse(maze, x + 1, y, cons(here,prev))) != null )
//	    return cons("E", path);
//	else if ((path = mouse(maze, x, y + 1, cons(here,prev))) != null )
//	    return cons("S", path);
//	else 
//		return null;
	Cons result;
	if(cave == null)
		return null;
	
	if(consp(cave))
	{
	if(!occurs(item, cave))
		return null;
	 if(item.equals(cave))
		return list("\"done\"");
	else if((result = findpath_mouse(item, rest((Cons)cave))) != null)
		return cons("\"rest\"", result);
	else if((result = findpath_mouse(item, first((Cons)cave))) != null)
		return cons("\"first\"", result);
	else
		return null;
	}
	else
	{
		if(item.equals(cave))
			return list("\"done\"");
		else
			return list("\"item not in cave\"");
	}
}

public static Object follow(Cons path, Object cave) 
{
//	if(path == null)
//		return cave;
//	else
//	{
//		if (eql(first(path), "\"first\""))
//		{
//			return follow(rest(path), first((Cons)cave));
//		}
//		else if(eql(first(path), "\"rest\""))
//		{
//			return follow(rest(path), rest((Cons)cave));
//		}
//		else
//			return cave;
//	}
	if(path == null)
		return cave;
	else
	{
		if (eql(first(path), "\"first\""))
		{
			return follow(rest(path), first((Cons)cave));
		}
		else if(eql(first(path), "\"rest\""))
		{
			return follow(rest(path), rest((Cons)cave));
		}
		else
			return cave;
	}
	
}

public static Object corresp(Object item, Object tree1, Object tree2) 
{
	return follow(findpath(item, tree1), tree2);
}

public static Cons solve(Cons e, String v) 
{
	Cons try1;
	Cons try2;
	if(lhs(e).equals(v))
		return e;
	else if(rhs(e).equals(v))
		return list(op(e), rhs(e), lhs(e));
	else if(!consp(rhs(e)) && !rhs(e).equals(v))
		return null;
	else if(consp(rhs(e)))
	{
		//division
		if(op((Cons)rhs(e)).equals("/"))
		{
			if((try1 = solve(list(op(e), list("*", lhs(e), rhs((Cons)rhs(e))), lhs((Cons)rhs(e))), v)) != null)
			{
				return try1;
			}
			else
			{
				try2 = list(op(e),  list("/", lhs((Cons)rhs(e)), lhs(e)), rhs((Cons)rhs(e)));
				return solve(try2, v);
			}
		}
		//multiplication
		else if(op((Cons)rhs(e)).equals("*"))
		{
			if((try1 = solve(list(op(e), list("/", lhs(e), rhs((Cons)rhs(e))), lhs((Cons)rhs(e))), v)) != null)
			{
				return try1;
			}
			else
			{
				try2 = list(op(e), list("/", lhs(e), lhs((Cons)rhs(e))), rhs((Cons)rhs(e)));
				return solve(try2, v);
			}			
		}
		//addition
		else if(op((Cons)rhs(e)).equals("+"))
		{
			if((try1 = solve(list(op(e), list("-", lhs(e), rhs((Cons)rhs(e))), lhs((Cons)rhs(e))), v)) != null)
			{
				return try1;
			}
			else
			{
				try2 = list(op(e), list("-", lhs(e), lhs((Cons)rhs(e))), rhs((Cons)rhs(e)));
				return solve(try2, v);
			}	
		}
		//subtraction/negative
		else if(op((Cons)rhs(e)).equals("-"))
		{
			//subtraction
			if(rhs((Cons)rhs(e)) != null)
			{
				if((try1 = solve(list(op(e), list("-", lhs((Cons)rhs(e)), lhs(e)), rhs((Cons)rhs(e))), v)) != null)
				{
					return try1;
				}
				else
				{
					try2 = list(op(e), list("+", lhs(e), rhs((Cons)rhs(e))), lhs((Cons)rhs(e)));
					return solve(try2, v);
				}
			}
			//negative (unary minus)
			else
				return solve(list(op(e), list("-", lhs(e)), lhs((Cons)rhs(e))), v);
		}
		//expt
		else if(op((Cons)rhs(e)).equals("expt"))
		{
			try1 = list(op(e), list("sqrt", lhs(e)), lhs((Cons)rhs(e)));
			return solve(try1, v);
		}
		//sqrt
		else if(op((Cons)rhs(e)).equals("sqrt"))
		{
			try1 = list(op(e), list("expt", lhs(e), new Integer(2)), lhs((Cons)rhs(e)));
			return solve(try1, v);
		}
		//exp
		else if(op((Cons)rhs(e)).equals("exp"))
		{
			if((try1 = solve(list(op(e), list("log", lhs(e)), lhs((Cons)rhs(e))), v)) != null)
			{
				return try1;
			}
			else
			{
				try2 = list(op(e), lhs((Cons)rhs(e)), list("log", lhs(e)));
				return solve(try2, v);
			}	
		}
		//log
		else if(op((Cons)rhs(e)).equals("log"))
		{
			if((try1 = solve(list(op(e), list("exp", lhs(e)), lhs((Cons)rhs(e))), v)) != null)
			{
				return try1;
			}
			else
			{
				try2 = list(op(e), lhs((Cons)rhs(e)), list("exp", lhs(e)));
				return solve(try2, v);
			}
		}
		
		//no matching operator
		else
			return null;
	}
	else
		return null;
}

public static Cons solve_mouse(Cons e, String v) 
{
	//write somewhere that else if((result = findpath_mouse(item, rest((Cons)cave))) != null)...dunno where
	//going to sleep
	
	//Cons mideqs;
	if(!consp(rhs(e)) && !rhs(e).equals(v))
		return null;
	
	if(lhs(e).equals(v))
	{
		//Or return list("=", v)  <--- no bc we're not "cons"ing ....append mideqs? (would've to define it first)
		return e;
	}
		
	else if(rhs(e).equals(v))
		return list(op(e), rhs(e), lhs(e));
	
	else if(consp(rhs(e)))
	{
		Object varexist = rhs(e);
		if(!occurs(v, varexist))
			return null;
		//division
		else if(op((Cons)rhs(e)).equals("/"))
		{
			if(v.equals(lhs((Cons)rhs(e))))
			{
				return solve_mouse(list(op(e), lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e)))), v);

			}
			else if(v.equals(rhs((Cons)rhs(e))))
			{
				return solve_mouse(list(op(e), rhs((Cons)rhs(e)), list("/", lhs((Cons)rhs(e)), lhs(e))),v);
			}
			else if(consp(lhs((Cons)rhs(e))))
			{
				if(occurs(v, lhs((Cons)rhs(e))))
				{
					return solve_mouse(list(op(e), lhs(e), lhs((Cons)rhs(e))), v);
				}
				else 
					return null;
			}
			else if(consp(lhs((Cons)rhs(e))))
			{
				if(occurs(v, rhs((Cons)rhs(e))))
				{
					return solve_mouse(list(op(e), lhs(e), rhs((Cons)rhs(e))), v);
				}
				else 
					return null;
			}
			
			else
			{
				return list("diverror");
			}
		}
		//operator not found
		else 
			return null;
	}
	
	//improper input of a formula
	else 
		return null;

	
//RESEMBLE CAVE
//	Cons result;
//	if(cave == null)
//		return null;
//	
//	if(consp(cave))
//	{
//	if(!occurs(item, cave))
//		return null;
//	 if(item.equals(cave))
//		return list("\"done\"");
//	else if((result = findpath_mouse(item, rest((Cons)cave))) != null)
//		return cons("\"rest\"", result);
//	else if((result = findpath_mouse(item, first((Cons)cave))) != null)
//		return cons("\"first\"", result);
//	else
//		return null;
//	}
//	else
//	{
//		if(item.equals(cave))
//			return list("\"done\"");
//		else
//			return list("\"item not in cave\"");
//	}
//	TRY USING THE MOUSE METHOD:
//	
//  Cons path;
//  if ( maze[y][x].equals("*") ) return null;
//  Cons here = list(new Integer(x),new Integer(y));
//  if ( memberv(here, prev) != null ) return null;
//
//IF RHS OR LHS.EQUALS V THEN RETURN LIST("=", "V")%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//  else if ( maze[y][x].equals("C") )
//      return list("C");
//OTHERWISE THEN CHECK FOR THE DIFFERENT OPERATORS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//    else if ((path = mouse(maze, x - 1, y,
//                           cons(here,prev)))
//             != null )
// BUT INSIDE EACH PUT IF STATEMENTS AS WELL... SO NOT JUST ONE RETURN%%%%%%%%%%%%%%%%%%%%%%%%%
//        return cons("W", path);
//      else if ((path = mouse(maze, x, y - 1,
//                             cons(here,prev)))
//               != null )
//          return cons("N", path);
//        else if ((path = mouse(maze, x + 1, y,
//                               cons(here,prev)))
//                 != null )
//            return cons("E", path);
//          else if ((path = mouse(maze, x, y + 1,
//                                 cons(here,prev)))
//                   != null )
//              return cons("S", path);
//            else return null; }	
}


public static Cons solve3(Cons e, String v, Cons right) 
{
	return list("DOES NOT WORK");
//	TRY USING THE MOUSE METHOD:
//	
//  Cons path;
//  if ( maze[y][x].equals("*") ) return null;
//  Cons here = list(new Integer(x),new Integer(y));
//  if ( memberv(here, prev) != null ) return null;
//
//IF RHS OR LHS.EQUALS V THEN RETURN LIST("=", "V")%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//  else if ( maze[y][x].equals("C") )
//      return list("C");
//OTHERWISE THEN CHECK FOR THE DIFFERENT OPERATORS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//    else if ((path = mouse(maze, x - 1, y,
//                           cons(here,prev)))
//             != null )
// BUT INSIDE EACH PUT IF STATEMENTS AS WELL... SO NOT JUST ONE RETURN%%%%%%%%%%%%%%%%%%%%%%%%%
//        return cons("W", path);
//      else if ((path = mouse(maze, x, y - 1,
//                             cons(here,prev)))
//               != null )
//          return cons("N", path);
//        else if ((path = mouse(maze, x + 1, y,
//                               cons(here,prev)))
//                 != null )
//            return cons("E", path);
//          else if ((path = mouse(maze, x, y + 1,
//                                 cons(here,prev)))
//                   != null )
//              return cons("S", path);
//            else return null; }	
}


//METHOD NOT WORKING -> use solve3
public static Cons solve_help(Cons e, String v) 
{
//	if(e == null)
//		return null;
//	Cons rhse = (Cons) rhs(e);
//	if(!occurs(v, e))
//		return null;
//	else if(e.equals(v))
//		return list("=", v);
//	else if(op(e).equals("/"))
//	{
//		if(v.equals(lhs((Cons)rhs(e))))
//		{
//			//return solve(list("=", lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e)))), v);
//			return list("=", lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e))));
//		}
//		else if(v.equals(rhs((Cons)rhs(e))))
//		{
//			//return solve(list("=", rhs((Cons)rhs(e)), list("*", lhs(e), lhs((Cons)rhs(e)))), v);
//			return list("=", rhs((Cons)rhs(e)), list("/", lhs((Cons)rhs(e)), lhs(e)));
//		}
//		
//		
//		//DEAL WITH CASES WHEN IT IS NOT EQUAL BUT INSIDE ANOTHER CONS (ie FIFTH)
//		else if(occurs(v, lhs((Cons)rhs(e))))
//		{
//			return list("divhaplr");
//		}
//			
//		else if(occurs(v, rhs((Cons)rhs(e))))
//			return list("divhaprr");
//		else
//			return list("diverror");
//	}
	
	if(op((Cons)rhs(e)).equals("/"))
	{
		if(v.equals(lhs((Cons)rhs(e))))
		{
			return list("=", lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e))));

		}
		else if(v.equals(rhs((Cons)rhs(e))))
		{
			return list("=", rhs((Cons)rhs(e)), list("/", lhs((Cons)rhs(e)), lhs(e)));
		}
		else if(occurs(v, lhs((Cons)rhs(e))))
		{
			return solve(list(op(e), lhs(e), lhs((Cons)rhs(e))), v);
		}
		else if(occurs(v, rhs((Cons)rhs(e))))
		{
			return solve(list(op(e), lhs(e), rhs((Cons)rhs(e))), v);
		}
		else
		{
			return list("diverror");
		}
	}
//	if(op((Cons)rhs(e)).equals("*"))
//	{
//		if(v.equals(lhs((Cons)rhs(e))))
//		{
//			return list("=", lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e))));
//
//		}
//		else if(v.equals(rhs((Cons)rhs(e))))
//		{
//			return list("=", rhs((Cons)rhs(e)), list("/", lhs((Cons)rhs(e)), lhs(e)));
//		}
//		else if(occurs(v, lhs((Cons)rhs(e))))
//		{
//			return solve(list(op(e), lhs(e), lhs((Cons)rhs(e))), v);
//		}
//		else if(occurs(v, rhs((Cons)rhs(e))))
//		{
//			return solve(list(op(e), lhs(e), rhs((Cons)rhs(e))), v);
//		}
//		else
//		{
//			return list("diverror");
//		}
//	}
	return list("operator not found");
		
//	TRY USING THE MOUSE METHOD:
//		
//	  Cons path;
//	  if ( maze[y][x].equals("*") ) return null;
//	  Cons here = list(new Integer(x),new Integer(y));
//	  if ( memberv(here, prev) != null ) return null;
//	
//	IF RHS OR LHS.EQUALS V THEN RETURN LIST("=", "V")%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//	  else if ( maze[y][x].equals("C") )
//	      return list("C");
//	OTHERWISE THEN CHECK FOR THE DIFFERENT OPERATORS%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
//	    else if ((path = mouse(maze, x - 1, y,
//	                           cons(here,prev)))
//	             != null )
//	 BUT INSIDE EACH PUT IF STATEMENTS AS WELL... SO NOT JUST ONE RETURN%%%%%%%%%%%%%%%%%%%%%%%%%
//	        return cons("W", path);
//	      else if ((path = mouse(maze, x, y - 1,
//	                             cons(here,prev)))
//	               != null )
//	          return cons("N", path);
//	        else if ((path = mouse(maze, x + 1, y,
//	                               cons(here,prev)))
//	                 != null )
//	            return cons("E", path);
//	          else if ((path = mouse(maze, x, y + 1,
//	                                 cons(here,prev)))
//	                   != null )
//	              return cons("S", path);
//	            else return null; }	
	
}

public static Cons solve2(Cons e, String v) 
{
	//try using the same method (no helper)
	//do NOT return list("=", bla bla instead ALWAYS return solve(bla bla)
	//division
	if(op((Cons)rhs(e)).equals("/"))
	{
		if(v.equals(lhs((Cons)rhs(e))))
		{
			return list("=", lhs((Cons)rhs(e)), list("*", lhs(e), rhs((Cons)rhs(e))));
		}
		else if(v.equals(rhs((Cons)rhs(e))))
		{
			return list("=", rhs((Cons)rhs(e)), list("/", lhs((Cons)rhs(e)), lhs(e)));
		}
		else if(occurs(v, lhs((Cons)rhs(e))))
		{
			return solve(list(op(e), lhs(e), lhs((Cons)rhs(e))), v);
		}
		else if(occurs(v, rhs((Cons)rhs(e))))
		{
			return solve(list(op(e), lhs(e), rhs((Cons)rhs(e))), v);
		}
		else
		{
			return list("diverror"); 
		}
	}
			
	//multiplication
	else if(op((Cons)rhs(e)).equals("*"))
	{
		if(v.equals(lhs((Cons)rhs(e))))
			//Can put || v.equals(rhs((Cons)rhs(e))) instead of else if?
		{
			return solve(list("=", lhs((Cons)rhs(e)), list("/", lhs(e), rhs((Cons)rhs(e)))), v);
		}
		else if(v.equals(rhs((Cons)rhs(e))))
		{
			return solve(list("=", rhs((Cons)rhs(e)), list("/", lhs(e), lhs((Cons)rhs(e)))), v);
		}
//		else if(occurs(v, lhs((Cons)rhs(e))))
//		{
//			//System.out.println("Entered herrr");
//			return solve(list(op(e), lhs(e), lhs((Cons)rhs(e))), v);
//		}
		else if(occurs(v, rhs((Cons)rhs(e))))
		{
			//System.out.println("Entered hereee");
			return solve2(list(op(e), lhs(e), rhs((Cons)rhs(e))), v);
		}
		else
			return list("multerror");
	}
	
	//addition
	else if(op((Cons)rhs(e)).equals("+"))
	{
		if(v.equals(lhs((Cons)rhs(e))))
		{
			return solve(list("=", lhs((Cons)rhs(e)), list("-", lhs(e), rhs((Cons)rhs(e)))), v);
		}
		else if(v.equals(rhs((Cons)rhs(e))))
		{
			return solve(list("=", rhs((Cons)rhs(e)), list("-", lhs(e), lhs((Cons)rhs(e)))), v);
		}
		else
			return list("adderror");
	}
	//subtraction/negative
	else if(op((Cons)rhs(e)).equals("-"))
	{
		//subtraction
		if(rhs((Cons)rhs(e)) != null)
		{
			if(v.equals(lhs((Cons)rhs(e))))
			{
				return solve(list("=", lhs((Cons)rhs(e)), list("+", lhs(e), rhs((Cons)rhs(e)))), v);
			}
			else if(v.equals(rhs((Cons)rhs(e))))
			{
				return solve(list("=", rhs((Cons)rhs(e)), list("-", lhs((Cons)rhs(e))), lhs(e)), v);
			}
			else
				return list("suberror");
		}
		else
			return list("negerror");
	}
	
	else
		return list("operator not found");
}

public static Double solveit (Cons equations, String var, Cons values) 
{
	Cons pick_eqtn = null;
	Cons desired_eqtn;
	Cons allvars = append(list(var), vars(values));
	while(equations != null)
	{
		if(setEqual(allvars, vars((Cons)first(equations))))
		{
			pick_eqtn = (Cons)first(equations);
			break;
		}
		else
			equations = rest(equations);
	}
	desired_eqtn = solve(pick_eqtn, var);
	return eval(rhs(desired_eqtn), values);
}

 
    // Include your functions vars and eval from the previous assignment.
    // Modify eval as described in the assignment.
public static Cons vars (Object expr) 
{
	Cons result = null;
	return vars2(expr, result);
}

@SuppressWarnings("static-access")
public static Cons vars2 (Object expr, Cons result) 
{
	if(expr == null)
	{
		return union(result,result);
	}
	else
	{
		if(!consp(first((Cons)expr)))
		{
			//I created a new list to get rid of the operators
			if(member(first((Cons)expr), operators) == null)
			{
				if(!numberp(first((Cons)expr)))
				{
					result = result.cons(first((Cons)expr), result);
				}
			}
		}
		else
		{
			result = vars2(first((Cons)expr), result);
		}
		return vars2(rest((Cons)expr), result);
	}
	
	
}

//public static Integer eval (Object tree) 
//{
//	
//	if(consp(tree))
//	{	
//		String oper = (String) op((Cons) tree);
//		if(oper.equals("*"))
//		{
//			return eval(lhs((Cons)tree)) * eval(rhs((Cons)tree));
//		}
//		else if(oper.equals("+"))
//		{
//			return eval(lhs((Cons)tree)) + eval(rhs((Cons)tree));
//		}
//		else if(oper.equals("-"))
//		{
//			if(rhs((Cons)tree) == null)
//			{
//				return eval(lhs((Cons)tree)) * -1;
//			}
//			else
//			{
//				return eval(lhs((Cons)tree)) - eval(rhs((Cons)tree));
//			}
//			
//		}
//		else if(oper.equals("/"))
//		{
//			return eval(lhs((Cons)tree)) / eval(rhs((Cons)tree));
//		}
//		else if(oper.equals("expt"))
//		{
//			return (Integer)(int) Math.pow(eval(lhs((Cons)tree)) , eval(rhs((Cons)tree)));
//		}
//	}
//	else if(numberp(tree))
//	{
//		return (Integer)tree;
//	}
//		return 0;
//	
//}

public static Double eval (Object tree, Cons bindings) 
{
	if(consp(tree))
	{	
		String oper = (String) op((Cons) tree);
		if(oper.equals("*"))
		{
			return eval(lhs((Cons)tree), bindings) * eval(rhs((Cons)tree), bindings);
		}
		else if(oper.equals("+"))
		{
			return eval(lhs((Cons)tree), bindings) + eval(rhs((Cons)tree), bindings);
		}
		else if(oper.equals("-"))
		{
			if(rhs((Cons)tree) == null)
			{
				return eval(lhs((Cons)tree), bindings) * -1;
			}
			else
			{
				return eval(lhs((Cons)tree), bindings) - eval(rhs((Cons)tree), bindings);
			}
			
		}
		else if(oper.equals("/"))
		{
			return eval(lhs((Cons)tree), bindings) / eval(rhs((Cons)tree), bindings);
		}
		else if(oper.equals("expt"))
		{
			return Math.pow(eval(lhs((Cons)tree), bindings) , eval(rhs((Cons)tree), bindings));
		}
		else if(oper.equals("exp"))
		{
			return Math.pow(Math.E , eval(lhs((Cons)tree), bindings));
		}
		else if(oper.equals("sqrt"))
		{
			return Math.sqrt(eval(lhs((Cons)tree), bindings));
		}
		else if(oper.equals("log"))
		{
			return Math.log(eval(lhs((Cons)tree), bindings));
		}
		
	}
	else if(numberp(tree))
	{
		if (integerp(tree))
		    return ((Integer)tree).doubleValue();
		else
			return (Double)tree;
	}
	else if(stringp(tree))
	{
		return (Double)lhs(assoc(tree, bindings));
	}
	return 0.0;
}


    // ****** your code ends here ******

    public static void main( String[] args ) {
 		
    	Cons cave = list("rocks", "gold", list("monster"));
        Cons path = findpath("gold", cave);
        printanswer("cave = " , cave);
        printanswer("path = " , path);
        printanswer("follow = " , follow(path, cave));
        Cons treea = list(list("my", "eyes"),
                          list("have", "seen", list("the", "light")));
        Cons treeb = list(list("my", "ears"),
                          list("have", "heard", list("the", "music")));
        printanswer("treea = " , treea);
        printanswer("treeb = " , treeb);
//      System.out.println("correct path = " + list("\"rest\"", "\"first\"", "\"rest\"", "\"rest\"", "\"first\"", "\"rest\"","\"first\"", "\"done\""));
        printanswer("corresp = " , corresp("light", treea, treeb));

        System.out.println("formulas = ");
        
        //comment out test cases
//        Cons blabla =  list( "=", "s", list("+", "s0", list( "*", "v", "t")));
//        printanswer("blabla = " , blabla);
//        printanswer("ans=", solve(blabla, "s0"));
        //till here

        Cons frm = formulas;
        Cons vset = null;
        while ( frm != null ) {
            printanswer("   "  , ((Cons)first(frm)));
            vset = vars((Cons)first(frm));
            while ( vset != null ) {
                printanswer("       "  ,
                    solve((Cons)first(frm), (String)first(vset)) );
                vset = rest(vset); }
            frm = rest(frm); }

        Cons bindings = list( list("a", (Double) 32.0),
                              list("t", (Double) 4.0));
        printanswer("Eval:      " , rhs((Cons)first(formulas)));
        printanswer("  bindings " , bindings);
        printanswer("  result = " , eval(rhs((Cons)first(formulas)), bindings));
        
        
        printanswer("Tower: " , solveit(formulas, "h0",
                                            list(list("h", new Double(0.0)),
                                                 list("t", new Double(4.0)))));

        printanswer("Car: " , solveit(formulas, "a",
                                            list(list("v", new Double(88.0)),
                                                 list("t", new Double(8.0)))));
        
        printanswer("Capacitor: " , solveit(formulas, "c",
                                            list(list("v", new Double(3.0)),
                                                 list("v0", new Double(6.0)),
                                                 list("r", new Double(10000.0)),
                                                 list("t", new Double(5.0)))));

        printanswer("Ladder: " , solveit(formulas, "b",
                                            list(list("a", new Double(6.0)),
                                                 list("c", new Double(10.0)))));


      }

}
