/*
 * Daniel Monroy
 * dm35729
 * February 19, 2012
 * CS314
 * 52870
 */

// libtest.java      GSN    03 Oct 08
// 

import java.util.LinkedList;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class libtest {

    // ****** your code starts here ******


public static Integer sumlist(LinkedList<Integer> lst) 
{
	Integer sum = 0;
	for(int i = 0; i < lst.size(); i++)
	{
		sum += lst.get(i);
	}
	return sum;
}

public static Integer sumarrlist(ArrayList<Integer> lst) 
{
	Integer sum = 0;
	for(int i = 0; i < lst.size(); i++)
	{
		sum += lst.get(i);
	}
	return sum;
}

public static LinkedList<Object> subset (Predicate p, LinkedList<Object> lst) 
{
	LinkedList<Object> result = new LinkedList<Object>();
	for(int i = 0; i < lst.size(); i++)
	{
		if(p.pred(lst.get(i)))
		{
			result.addLast(lst.get(i));
		}
	}
	return result;
}

public static LinkedList<Object> dsubset (Predicate p, LinkedList<Object> lst) 
{
	for(int i = 0; i < lst.size(); i++)
	{
		if(!p.pred(lst.get(i)))
		{
			lst.remove(i);
		}
	}
	return lst;
}

public static Object some (Predicate p, LinkedList<Object> lst) 
{
	for(int i = 0; i < lst.size(); i++)
	{
		if(p.pred(lst.get(i)))
		{
			return lst.get(i);
		}
	}
	return null;
}

public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) 
{
	LinkedList<Object> result = new LinkedList<Object>();
	for(int i = 0; i < lst.size(); i++)
	{
			result	.addLast(f.fn(lst.get(i)));
	}
	return result;
}

public static LinkedList<Object> merge (LinkedList<Object> lsta, LinkedList<Object> lstb)
{
	LinkedList<Object> result = new LinkedList<Object>();
	LinkedList<Object> copya = new LinkedList<Object>(lsta);
	LinkedList<Object> copyb = new LinkedList<Object>(lstb);
	int i = -1;
	if(lsta.isEmpty())
		return lstb;
	else if(lstb.isEmpty())
		return lsta;
	else
		return merge2(copya, copyb, result, i);
//	 ((Comparable)(lsta.getFirst())).compareTo(((Comparable)(lstb.getFirst())));
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public static LinkedList<Object> merge2 (LinkedList<Object> lsta, LinkedList<Object> lstb, LinkedList<Object> result, int i)
{
	++i;
	if(lsta.isEmpty())
	{
		result.addAll(lstb);
		return result;
	}
	else if(lstb.isEmpty())
	{
		result.addAll(lsta);
		return result;
	}
		
	else
	{
		if(((Comparable)(lsta.getFirst())).compareTo(((Comparable)(lstb.getFirst()))) < 0)
		{
			result.add(lsta.getFirst());
			lsta.removeFirst();
			return merge2(lsta, lstb, result, i);
		}
		else
		{
			result.add(lstb.getFirst());
			lstb.removeFirst();
			return merge2(lsta, lstb, result, i);
		}
	}
}

public static LinkedList<Object> sort (LinkedList<Object> lst) 
{
	LinkedList<Object> half = new LinkedList<Object>();
	LinkedList<Object> mid = new LinkedList<Object>();
	if(lst.size() == 1)
	{
		return lst;
	}
	else
	{
//		for(int i = 0; i < lst.size()/2; i++)
//		{
//			mid.add(lst.get(i));
//			
//		}
//		for(int j = lst.size()/2; j < lst.size(); j++)
//		{
//			half.add(lst.get(j));
//		}
		
		mid = (LinkedList<Object>) lst.subList(0, lst.size()/2);
		half = (LinkedList<Object>) lst.subList(lst.size()/2, lst.size());
		return merge(sort(mid), sort(half));
	}
}


public static LinkedList<Object> intersection (LinkedList<Object> lsta, LinkedList<Object> lstb) 
{
	LinkedList<Object> sorta = new LinkedList<Object>(sort(lsta));
	LinkedList<Object> sortb = new LinkedList<Object>(sort(lstb));
	LinkedList<Object> total = new LinkedList<Object>(merge(sorta, sortb));
	LinkedList<Object> result = new LinkedList<Object>();
	
	for(int i = 0; i < total.size()-1; i++)
	{
		if(total.get(i).equals(total.get(i +1)))
		{
			if(!result.contains(total.get(i)))
			{
				result.add(total.get(i));
			}
		}
	}
	
	return result;
	
}

public static LinkedList<Object> reverse (LinkedList<Object> lst) 
{
	LinkedList<Object> result = new LinkedList<Object>();
	for(int i = 0; i < lst.size(); i++)
	{
		result.addFirst(lst.get(i));
	}
	return result;
}

    // ****** your code ends here ******

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst.toString());
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb.toString());
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc.toString());
        System.out.println("subset = " + subset(myp, lstc).toString());

        System.out.println("lstc     = " + lstc.toString());
        System.out.println("dsubset  = " + dsubset(myp, lstc).toString());
        System.out.println("now lstc = " + lstc.toString());

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd.toString());
        System.out.println("some = " + some(myp, lstd).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, lstd).toString());

        LinkedList<Object> lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(6));
        lste.add(new Integer(9));
        System.out.println("lste = " + lste.toString());
        LinkedList<Object> lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        System.out.println("lstf = " + lstf.toString());
        System.out.println("merge = " + merge(lste, lstf).toString());

        LinkedList<Object> lstg = new LinkedList<Object>();
        lstg.add(new Integer(39));
        lstg.add(new Integer(84));
        lstg.add(new Integer(5));
        lstg.add(new Integer(59));
        lstg.add(new Integer(86));
        lstg.add(new Integer(17));
        System.out.println("lstg = " + lstg.toString());

        System.out.println("intersection(lstd, lstg) = " + intersection(lstd, lstg).toString());
        System.out.println("reverse lste = " + reverse(lste).toString());

        System.out.println("sort(lstg) = " + sort(lstg).toString());

   }
}