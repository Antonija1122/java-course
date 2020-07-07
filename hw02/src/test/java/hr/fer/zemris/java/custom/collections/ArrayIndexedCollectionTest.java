package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ArrayIndexedCollectionTest {
	

	  //Prvo su testovi za konstruktore
	
	  @Test 
	  public void constructor1Test() { 
		 ArrayIndexedCollection col = new ArrayIndexedCollection(2); 
		 assertEquals(col.capacity(), 2);
		 assertEquals(col.size(), 0);
		 
	  }
	  
	  @Test
	  public void constructor1TestException() {
		  assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
		  assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-2));
	    }
	  
	  @Test 
	  public void constructor2Test() { 
		  ArrayIndexedCollection col = new ArrayIndexedCollection(); 
		  assertEquals(col.capacity(), 16);
		  assertEquals(col.size(), 0); 
	  }
	  
	  
	  public void constructor3Test() {  
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(4);
		col2.add("a");
		col2.add("b");
		  
		ArrayIndexedCollection col = new ArrayIndexedCollection(col2, 4); 
		assertEquals(col.capacity(), 4);
		assertEquals(col.size(), 2);
		Object[] content=col.toArray();
		Object[] list=new Object[] {"a", "b"};
		assertArrayEquals(content, list);
		
	  }
	  
	  @Test
	  public void constructor3TestExceptions() {
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(2);
		col2.add("a");
		col2.add("b");
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(col2, 0));
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(col2, -2));
		
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 2));
	    }
	  
	  
	  public void constructor4Test() {  
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(4);
		col2.add("a");
		col2.add("b");
		  
		ArrayIndexedCollection col = new ArrayIndexedCollection(col2); 
		assertEquals(col.capacity(), 16);
		assertEquals(col.size(), 2);
		Object[] content=col.toArray();
		Object[] list=new Object[] {"a", "b"};
		assertArrayEquals(content, list);
		
	  }
	  
	  @Test
	  public void constructor4TestExceptions() {
		  assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	  }
	  
	  
	  //Nadalje se testovi za metode
	 
    
    @Test
    public void isEmptyTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	assertTrue(col.isEmpty());
    	col.add("bla");
    	assertFalse(col.isEmpty());
    }
    
    
    @Test
    public void sizeTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	assertEquals(0, col.size());
    	col.add("bla");
    	assertEquals(1, col.size());
    	col.add("bla2");
    	assertEquals(2, col.size());
    }
    
    @Test
    //test ujedno pokazuje i da se realocira memorija da dodavanje vise početnog broja elemenata tj vise od dva elementa 
    public void addTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	col.add(Integer.valueOf(20));
    	col.add("bla2");
    	col.add("bla3");
    	assertEquals(3, col.size());
    	Object[] content=col.toArray();
    	assertEquals(content[0], Integer.valueOf(20));
    	assertEquals(content[1], "bla2");	
    	assertEquals(content[2], "bla3");
    }
    
    @Test
    public void addTestNull() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	col.add("bla");
    	assertThrows(NullPointerException.class, () -> col.add(null));
    	
    }
    
    @Test
    public void containTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	col.add("bla");
    	assertFalse(col.contains("bla23455"));
    	assertTrue(col.contains("bla"));
    	assertFalse(col.contains(null));
    }
    
    @Test
    public void removeObjectValueTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	col.add("bla");
    	col.add("bla2");
    	
    	assertFalse(col.remove("bla23455"));//false jer ne sadrži taj objekt
    	assertTrue(col.remove("bla"));
    	assertEquals(1, col.size());
    	
    	Object[] content=col.toArray();
    	assertEquals(content[0], "bla2");
    	
    	assertTrue(col.remove("bla2"));
    	
    }
    
    
    @Test
    public void toArrayTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	col.add("bla");
    	col.add("bla2");
    	
    	Object[] content=col.toArray();
    	assertEquals(2, content.length);
    	
    	Object[] obj = new Object[] { "bla", "bla2"};
    	assertEquals(content[0], obj[0]);
    	assertEquals(content[1], obj[1]);
    }
    
    @Test
    public void toArrayTestNull() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	assertThrows(UnsupportedOperationException.class, () -> col.toArray());
    }
    
    
    
    @Test
	public void forEachTest() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		class P extends Processor {
			public int sum=0;
			@Override
			public void process(Object value) {
				sum=sum+(int)value;
			}
		}
		
		P process= new P();
		arr.add(Integer.valueOf(20));
		arr.add(Integer.valueOf(30));
		arr.add(Integer.valueOf(40));
        arr.forEach(process);
		assertEquals(process.sum,20+30+40);
	}
    
    	
    @Test
    public void addAllTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    	ArrayIndexedCollection other = new ArrayIndexedCollection(5);
    	
    	col.add(Integer.valueOf(20));
    	other.add("bla");
    	other.add("bla2");
    	col.addAll(other);
    	
    	Object[] content=col.toArray();
    	assertEquals(3, col.size());
    	
    	assertEquals(Integer.valueOf(20), content[0]);
    	assertEquals("bla", content[1]);
    	assertEquals("bla2", content[2]);
    	
    }
    
    @Test 
    public void addAllTestNull() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    	ArrayIndexedCollection other = null;
    	
    	col.add(Integer.valueOf(20));

    	assertThrows(NullPointerException.class, () -> col.addAll(other));

    	
    }
    
    @Test
    public void clearTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    	
    	col.add(Integer.valueOf(20));
    	col.add("bla");
    	col.add("bla2");
    	
    	assertEquals(3, col.size());
    	
    	col.clear();
    	assertEquals(0, col.size());
    	
    }
    
    @Test
    public void getTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    	
    	col.add(Integer.valueOf(20));
    	col.add("bla");
    	col.add("bla2");
    	
    	assertEquals(col.get(0),Integer.valueOf(20));
    	assertEquals(col.get(1),"bla");
    	assertEquals(col.get(2),"bla2");
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
    	assertThrows(IndexOutOfBoundsException.class, () -> col.get(3));
    }
    
    @Test
    public void insertTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    	
    	col.add(Integer.valueOf(20));
    	col.add("bla");
    	col.add("bla2");
    	
    	col.insert("prvi", 0);
    	col.insert("drugi", 1);
    	col.insert("srednji", 3);
    	col.insert("zadnji", 6);
    	
    	Object[] content=col.toArray();
    	Object[] obj = new Object[] {"prvi","drugi",Integer.valueOf(20), "srednji","bla" , "bla2", "zadnji"};
    	
    	
    	assertArrayEquals(content , obj);
    	assertEquals(7, col.size());
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> col.insert( "nesto",-1));
    	assertThrows(IndexOutOfBoundsException.class, () -> col.insert("nesto", 10));
    }
    
    
    @Test
    public void indexOfTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    
    	col.add("prvi");
    	col.add("drugi");
    	col.add("treci");
    	
    	int index=col.indexOf("prvi");
    	int index2=col.indexOf("treci");
    	int nema=col.indexOf("bla");
    	int returnZero=col.indexOf("bla");
    	
    	assertEquals(index, 0);
    	assertEquals(index2, 2);
    	assertEquals(nema, -1);
    	assertEquals(returnZero, -1);
    	
    }
    
    
    @Test
    public void removeAtIndexTest() {
    	ArrayIndexedCollection col = new ArrayIndexedCollection(5);
    
    	col.add("prvi");
    	col.add("drugi");
    	col.add("treci");
    	col.add("cetvrti");
    	col.add("peti");
    	col.add("sesti");

    	col.remove(0); //makni prvi
    	col.remove(2); //makni cetvrti
    	col.remove(col.size()-1); //makni zadnji
    	
    	Object[] content=col.toArray();
    	Object[] obj = new Object[] {"drugi", "treci", "peti"};

    	assertArrayEquals(content , obj);
    	assertEquals(3, col.size());
    	
    	
    	assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
    	assertThrows(IndexOutOfBoundsException.class, () -> col.remove(7));
    	
    	col.remove(0);
    	col.remove(0);
    	col.remove(0);
    	assertEquals(0, col.size());
    	
    }

    
    
    
    
}
