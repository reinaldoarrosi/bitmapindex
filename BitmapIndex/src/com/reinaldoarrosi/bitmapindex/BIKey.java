package com.reinaldoarrosi.bitmapindex;

public class BIKey {
	public static class BIGroup {
		private int group;
		private String subGroup;
		
		public BIGroup(int group) {
			this.group = group;
			this.subGroup = "";
		}
		
		public BIGroup(int group, String subGroup) {
			this.group = group;
			this.subGroup = (subGroup != null ? subGroup : "");
		}
		
		public int getGroup() {
			return group;
		}

		public String getSubGroup() {
			return subGroup;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;

			if (!(o instanceof BIGroup))
				return false;

			BIGroup compare = (BIGroup) o;
			return (this.group == compare.group && this.subGroup.equals(compare.subGroup));
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + group;
			result = (subGroup.length() > 0 ? 31 * result + subGroup.hashCode() : result);

			return result;
		}
	}

	private BIGroup group;
	private String key;
	
	public BIKey(int group, Object key) {
		this.group = new BIGroup(group);
		
		if(key != null)
			this.key = Utils.toString(key);
		else
			this.key = "";
	}
	
	public BIKey(int group, String subGroup, Object key) {
		this.group = new BIGroup(group, subGroup);
		
		if(key != null)
			this.key = Utils.toString(key);
		else
			this.key = "";
	}
	
	public BIKey(BIGroup group, Object key) {
		this.group = group;
		
		if(key != null)
			this.key = Utils.toString(key);
		else
			this.key = "";
	}

	public BIGroup getGroup() {
		return group;
	}

	public String getKey() {
		return key;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof BIKey))
			return false;

		BIKey compare = (BIKey) o;
		return (this.group.equals(compare.group) && this.key.equals(compare.key));
	}

	@Override
	public int hashCode() {
		int result = group.hashCode();
		result = 31 * result + key.hashCode();

		return result;
	}
}