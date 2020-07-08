package hr.fer.zemris.java.custom.collections.demo;


import hr.fer.zemris.java.custom.collections.Dictionary;

public class DicDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Dictionary<Long, String> dic=new Dictionary<Long, String> ();
		
		dic.put(Long.valueOf(30), "Ivan");
		
		dic.put(Long.valueOf(31), "Marko");
		
		dic.put(Long.valueOf(32), "Mate");
		
		dic.put(Long.valueOf(31), "Luka"); //preslikava Marka
		
		dic.put(Long.valueOf(34), "Vanda");
		
		dic.put(Long.valueOf(34), "Karlo"); //preslikava Vandu


		
		System.out.println(dic.get(Long.valueOf(30)));
		System.out.println(dic.get(Long.valueOf(31)));
		System.out.println(dic.get(Long.valueOf(32)));
		System.out.println(dic.get(Long.valueOf(34)));

		
		System.out.println(dic.size());
		
		
		

	}

}
