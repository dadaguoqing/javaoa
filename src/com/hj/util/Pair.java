package com.hj.util;

/**
 * class that holds two ints
 * @author zeroth_cat
 *
 */
public class Pair {
	private int first;
	private int second;
	
	public Pair(int first, int second){
		this.first = first;
		this.second = second;
	}
	
	public int first_getter(){
		return this.first;
	}
	
	public int second_getter(){
		return this.second;
	}
	
	public void first_setter(int first){
		this.first = first;
	}
	
	public void second_setter(int second){
		this.second = second;
	}
}
