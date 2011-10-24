/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

/**
 *
 * @author tavaskor
 */
public interface AbstractDice {
    public void roll();
    public int get();
    public int maximumRollableValue();
}
