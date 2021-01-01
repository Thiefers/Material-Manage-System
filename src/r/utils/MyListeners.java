package r.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public interface MyListeners extends MouseListener, MenuListener, ActionListener, ItemListener, TableModelListener,
		KeyListener, MouseMotionListener {
	// MouseListener
	@Override
	default void mouseClicked(MouseEvent e) {
	}

	@Override
	default void mouseEntered(MouseEvent e) {
	}

	@Override
	default void mouseExited(MouseEvent e) {
	}

	@Override
	default void mousePressed(MouseEvent e) {
	}

	@Override
	default void mouseReleased(MouseEvent e) {
	}

	// MenuListener
	@Override
	default void menuSelected(MenuEvent e) {
	}

	@Override
	default void menuCanceled(MenuEvent e) {
	}

	@Override
	default void menuDeselected(MenuEvent e) {
	}

	// ActionListener
	@Override
	default void actionPerformed(ActionEvent e) {
	}

	// ItemListener
	@Override
	default void itemStateChanged(ItemEvent e) {
	}

	// TableModelListener
	@Override
	default void tableChanged(TableModelEvent e) {
	}

	// KeyListener
	@Override
	default void keyPressed(KeyEvent e) {
	}

	@Override
	default void keyReleased(KeyEvent e) {
	}

	@Override
	default void keyTyped(KeyEvent e) {
	}

	// MouseMotionListener
	@Override
	default void mouseDragged(MouseEvent e) {
	}

	@Override
	default void mouseMoved(MouseEvent e) {
	}
}