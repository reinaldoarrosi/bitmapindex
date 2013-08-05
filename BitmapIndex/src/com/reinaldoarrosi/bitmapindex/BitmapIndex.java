package com.reinaldoarrosi.bitmapindex;

import java.util.HashMap;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.reinaldoarrosi.bitmapindex.BICriteria.Operator;
import com.reinaldoarrosi.bitmapindex.BIKey.BIGroup;

public class BitmapIndex {
	private HashMap<BIKey, EWAHCompressedBitmap> bitmaps;
	private HashMap<BIGroup, EWAHCompressedBitmap> emptyBitmaps;
	private int maxBitSize = 0;
	
	public BitmapIndex() {
		bitmaps = new HashMap<BIKey, EWAHCompressedBitmap>();
		emptyBitmaps = new HashMap<BIGroup, EWAHCompressedBitmap>();
	}
	
	public void set(BIKey key, int bit) {
		EWAHCompressedBitmap bitmap = bitmaps.get(key);
		EWAHCompressedBitmap emptyBitmap = emptyBitmaps.get(key.getGroup());
		
		if(bitmap == null) {
			bitmap = new EWAHCompressedBitmap();
			bitmaps.put(key, bitmap);
		}
		
		if(emptyBitmap == null) {
			emptyBitmap = new EWAHCompressedBitmap();
			emptyBitmaps.put(key.getGroup(), emptyBitmap);
		}
		
		bitmap.set(bit);

		emptyBitmap.not();
		emptyBitmap.set(bit);
		emptyBitmap.not();
		
		maxBitSize = (maxBitSize < (bit + 1) ? (bit + 1) : maxBitSize);
	}
	
	public EWAHCompressedBitmap query(BICriteria criteria) {
		if(criteria.getOperator() == Operator.OR) {
			EWAHCompressedBitmap left = query(criteria.getLeftCriteria());
			EWAHCompressedBitmap right = query(criteria.getRightCriteria());
			
			return left.or(right);
		} else if(criteria.getOperator() == Operator.AND) {
			EWAHCompressedBitmap left = query(criteria.getLeftCriteria());
			EWAHCompressedBitmap right = query(criteria.getRightCriteria());
			
			return left.and(right);
		} else if(criteria.getOperator() == Operator.EMPTY_ONLY) {
			return getEmptyBitmap(criteria.getKey());
		} else {
			EWAHCompressedBitmap bitmap = bitmaps.get(criteria.getKey());
			int operator = criteria.getOperator();
			
			if(bitmap == null)
				bitmap = getFilledBitmap(false);
			else
				bitmap = getCopyBitmap(bitmap);
			
			if(operator == Operator.NOT_EQUALS || operator == Operator.NOT_EQUALS_OR_EMPTY) 
				bitmap.not();
			
			if(criteria.getOperator() == Operator.NOT_EQUALS_OR_EMPTY || criteria.getOperator() == Operator.EQUALS_OR_EMPTY)
				bitmap = bitmap.or(getEmptyBitmap(criteria.getKey()));
			
			return bitmap;
		}
	}
	
	private EWAHCompressedBitmap getFilledBitmap(boolean fill) {
		EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
		bitmap.setSizeInBits(maxBitSize, fill);
		
		return bitmap;
	}
	
	private EWAHCompressedBitmap getCopyBitmap(EWAHCompressedBitmap bitmap) {
		return getCopyBitmap(bitmap, false);
	}
	
	private EWAHCompressedBitmap getCopyBitmap(EWAHCompressedBitmap bitmap, boolean fill) {
		try {
			EWAHCompressedBitmap ret = (EWAHCompressedBitmap) bitmap.clone(); 
			ret.setSizeInBits(maxBitSize, fill);
			
			return ret;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private EWAHCompressedBitmap getEmptyBitmap(BIKey key) {
		EWAHCompressedBitmap bitmap = emptyBitmaps.get(key.getGroup());
		
		if(bitmap == null)
			bitmap = getFilledBitmap(true);
		else
			bitmap = getCopyBitmap(bitmap, true);
		
		return bitmap;
	}
}
