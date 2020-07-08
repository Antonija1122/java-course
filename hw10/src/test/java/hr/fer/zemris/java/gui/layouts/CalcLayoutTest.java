package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void test1Exception() {
		JPanel panel = new JPanel(new CalcLayout(2));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(0, 1)));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(1, 8)));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(1, -2)));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(6, 5)));
	}

	@Test
	void test2Exception() {
		JPanel panel = new JPanel(new CalcLayout(2));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(1, 5)));
	}

	@Test
	void test3Exception() {
		JPanel panel = new JPanel(new CalcLayout(2));
		panel.add(l("tekst 1"), new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(1, 1)));

		panel.add(l("tekst 1"), new RCPosition(2, 3));
		assertThrows(CalcLayoutException.class, () -> panel.add(l("tekst 1"), new RCPosition(2, 3)));

	}

	@Test
	void test4() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);

	}

	@Test
	void test5() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		System.out.println(dim.width + " " + dim.height);
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);

	}

	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

}
