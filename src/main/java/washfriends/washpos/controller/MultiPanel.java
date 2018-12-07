package washfriends.washpos.controller;

import washfriends.washpos.view.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

// switching between views (pages)
// Is a singleton.
public class MultiPanel extends CardLayout {
	// Reference to singleton
	public static final MultiPanel SELF = new MultiPanel(); 
	// Map of view name to views
	private final Map<String, InitView> VIEWS = new HashMap<String, InitView>(); 
	// Reference to parent container
	private JFrame parent = null;   
	
	private MultiPanel() { } // private constructor
	
	/**
     * Set the parent (wrapping) container.
     *
     * @param parent    the wrapping container
     * @return          this object (for chaining)
     */
    public MultiPanel setParent(JFrame parent) {
        this.parent = parent;
        return this;
    }
    

    /**
     * Adds the given view and associates it with the given name.
     * Returns true if successfully added, otherwise false.
     * Condition to add views: name unique (does not already exist) and
     * parent is set.
     *
     * @param name      the lookup (view name) string
     * @param iv        the view to add
     * @return          true if successfully added, otherwise false.
     */
    public boolean add(String name, InitView iv) {
        if (parent != null && !VIEWS.containsKey(name)) {
            addLayoutComponent(iv, name);
            VIEWS.put(name, iv);
            parent.add(iv);
            return true;
        }
        return false;
    }
    public boolean add(InitView iv) {
        return add(iv.getName(), iv);
    }

    /**
     * Display the view associated with the given name.
     * Returns true if successfully added, otherwise false.
     * Condition to add views: name exists and parent is set.
     *
     * @param name      of the view to display
     * @param args      arguments need to display the view
     * @return          true if successfully added, otherwise false.
     */
    public boolean show(String name, Object... args) {
        if (parent != null && VIEWS.containsKey(name)) {
            System.out.println("SHOW VIEW: " + name + " :: " + Arrays.toString(args));
            show(parent.getContentPane(), name);
            VIEWS.get(name).prepareView(args);
            return true;
        }
        return false;
    }
}
