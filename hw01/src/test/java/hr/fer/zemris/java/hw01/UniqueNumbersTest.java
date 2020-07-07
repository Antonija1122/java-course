package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

/**
 * Testiranje Funkcija iz razreda UniqueNumbers
 * 
 * @author antonija
 *
 */
class UniqueNumbersTest {

	@Test
	public void dodajManji() {

		TreeNode glava = new TreeNode();
		glava.value = 5;
		glava.left = null;
		glava.right = null;

		glava = UniqueNumbers.addNode(glava, 3);
		assertEquals(3, glava.left.value);
	}

	@Test
	public void dodajVeci() {

		TreeNode glava = new TreeNode();
		glava.value = 5;
		glava.left = null;
		glava.right = null;

		glava = UniqueNumbers.addNode(glava, 6);
		assertEquals(6, glava.right.value);
	}

	@Test
	public void vecPostoji() {

		TreeNode glava = new TreeNode();
		glava.value = 5;
		glava.left = null;
		glava.right = null;
		if (!UniqueNumbers.cointainsValue(glava, 5)) {
			fail("Nije pronađen postojeći čvor!");
		}
	}

	@Test
	public void velicinaPraznog() {

		TreeNode glava = null;

		int n = UniqueNumbers.treeSize(glava);
		assertEquals(0, n);
	}

	@Test
	public void velicinaJednog() {

		TreeNode glava = new TreeNode();
		glava.value = 5;
		glava.left = null;
		glava.right = null;

		int n = UniqueNumbers.treeSize(glava);
		assertEquals(1, n);
	}

	public void velicinaVise() {

		TreeNode glava = new TreeNode();
		glava.value = 5;
		glava.left = null;
		glava.right = null;
		glava = UniqueNumbers.addNode(glava, 3);

		int n = UniqueNumbers.treeSize(glava);
		assertEquals(2, n);
	}

}
