package com.reinaldoarrosi.bitmapindex;

import com.reinaldoarrosi.bitmapindex.BIKey.BIGroup;

public class BICriteria {	
	public static class Operator {
		public static final int EQUALS = 0;
		public static final int EQUALS_OR_EMPTY = 1;
		public static final int NOT_EQUALS = 2;
		public static final int NOT_EQUALS_OR_EMPTY = 3;
		public static final int EMPTY_ONLY = 6;
		
		public static final int OR = 4;
		public static final int AND = 5;
	}
	
	private BIKey key;
	private int operator;
	private BICriteria left;
	private BICriteria right;
	
	private BICriteria(BIKey key, int operator) {
		this.key = key;
		this.operator = operator;
	}
	
	private BICriteria(BICriteria left, BICriteria right, int operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public int getOperator() {
		return operator;
	}
	
	public BIKey getKey() {
		return key;
	}
	
	public BICriteria getLeftCriteria() {
		return left;
	}
	
	public BICriteria getRightCriteria() {
		return right;
	}
	
	public BICriteria or(BICriteria criteria) {
		return new BICriteria(this, criteria, Operator.OR);
	}
	
	public BICriteria and(BICriteria criteria) {
		return new BICriteria(this, criteria, Operator.AND);
	}
	
	public BICriteria andEquals(BIKey key) {
		return and(new BICriteria(key, Operator.EQUALS));
	}
	
	public BICriteria andNotEquals(BIKey key) {
		return and(new BICriteria(key, Operator.NOT_EQUALS));
	}
	
	public BICriteria andEqualsOrEmpty(BIKey key) {
		return and(new BICriteria(key, Operator.EQUALS_OR_EMPTY));
	}
	
	public BICriteria andNotEqualsOrEmpty(BIKey key) {
		return and(new BICriteria(key, Operator.NOT_EQUALS_OR_EMPTY));
	}
	
    public BICriteria andEmptyOnly(int group)
    {
        return and(new BICriteria(new BIKey(group, null), Operator.EMPTY_ONLY));
    }
    
    public BICriteria andEmptyOnly(BIGroup group)
    {
        return and(new BICriteria(new BIKey(group, null), Operator.EMPTY_ONLY));
    }
	
	public BICriteria orEquals(BIKey key) {
		return or(new BICriteria(key, Operator.EQUALS));
	}
	
	public BICriteria orNotEquals(BIKey key) {
		return or(new BICriteria(key, Operator.NOT_EQUALS));
	}
	
	public BICriteria orEqualsOrEmpty(BIKey key) {
		return or(new BICriteria(key, Operator.EQUALS_OR_EMPTY));
	}
	
	public BICriteria orNotEqualsOrEmpty(BIKey key) {
		return or(new BICriteria(key, Operator.NOT_EQUALS_OR_EMPTY));
	}
	
    public BICriteria orEmptyOnly(int group)
    {
        return or(new BICriteria(new BIKey(group, null), Operator.EMPTY_ONLY));
    }
    
    public BICriteria orEmptyOnly(BIGroup group)
    {
        return or(new BICriteria(new BIKey(group, null), Operator.EMPTY_ONLY));
    }
	
	public static BICriteria equals(BIKey key) {
		return new BICriteria(key, Operator.EQUALS);
	}
	
	public static BICriteria notEquals(BIKey key) {
		return new BICriteria(key, Operator.NOT_EQUALS);
	}
	
	public static BICriteria equalsOrEmpty(BIKey key) {
		return new BICriteria(key, Operator.EQUALS_OR_EMPTY);
	}
	
	public static BICriteria notEqualsOrEmpty(BIKey key) {
		return new BICriteria(key, Operator.NOT_EQUALS_OR_EMPTY);
	}
	
    public static BICriteria emptyOnly(int group)
    {
        return new BICriteria(new BIKey(group, 0), Operator.EMPTY_ONLY);
    }
    
    public static BICriteria emptyOnly(BIGroup group)
    {
        return new BICriteria(new BIKey(group, null), Operator.EMPTY_ONLY);
    }
}
