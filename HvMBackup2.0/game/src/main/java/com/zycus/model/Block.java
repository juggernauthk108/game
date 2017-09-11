package com.zycus.model;

public class Block {
	Creature creature;
	int x;
	int y;
	String money;
	String uid;
		
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	private int amountMoney = 0 ;

	public int getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(int amountMoney) {
		this.amountMoney = amountMoney;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creature == null) ? 0 : creature.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (creature == null) {
			if (other.creature != null)
				return false;
		} else if (!creature.equals(other.creature))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Block(Creature creature, int x, int y, String money) {
		super();
		this.creature = creature;
		this.x = x;
		this.y = y;
		this.money = money;
	}

	public Block(Creature creature, int x, int y) {
		super();
		this.creature = creature;
		this.x = x;
		this.y = y;
	}

	public Block() {
		super();
	}

	public Creature getCreature() {
		return creature;
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Block [creature=" + creature + ", x=" + x + ", y=" + y + "]";
	}

}