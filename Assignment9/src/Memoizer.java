//memoizer
import java.util.HashMap;

/*
 * Daniel Monroy
 * dm35729
 * April 1, 2012
 * CS314
 * 52870
 */



public class Memoizer 
{
	
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private Functor thefn;
	@SuppressWarnings("rawtypes")
	Memoizer(Functor f)
	{
		thefn = f;
		map = new HashMap();
	}
	
	@SuppressWarnings("unchecked")
	public Object call(Object x)
	{
		if(map.get(x) != null)
			return map.get(x);
		else
		{
			Object res = thefn.fn(x);
			map.put(x, res);
			return res;
		}
		
	}
	
}