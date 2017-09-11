package com.zycus.model;

public class Creature {

	private int life = 1000;

	// for future purpose
	private int mana = 10;
	private int luck = 40;

	private int armor = 5;
	private int accuracy = 80;

	private int movementRange = 3;

	private int attackRange = 5;

	private String gene = "neutral";

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public Creature(int life, int mana, int luck, int armor, int accuracy, int movementRange, int attackRange,
			int attackPoints) {
		super();
		this.life = life;
		this.mana = mana;
		this.luck = luck;
		this.armor = armor;
		this.accuracy = accuracy;
		this.movementRange = movementRange;
		this.attackRange = attackRange;
		this.attackPoints = attackPoints;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	private int attackPoints = 100;

	public Creature(int life, int movementRange, int attackPoints) {
		super();
		this.life = life;
		this.movementRange = movementRange;
		this.attackPoints = attackPoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public Creature(int life, int mana, int luck, int armor, int accuracy, int movementRange, int attackPoints) {
		super();
		this.life = life;
		this.mana = mana;
		this.luck = luck;
		this.armor = armor;
		this.accuracy = accuracy;
		this.movementRange = movementRange;
		this.attackPoints = attackPoints;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public Creature(int life, int movementRange) {
		super();
		this.life = life;
		this.movementRange = movementRange;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMovementRange() {
		return movementRange;
	}

	public void setMovementRange(int movementRange) {
		this.movementRange = movementRange;
	}

	public Creature() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Creature [life=" + life + ", movementRange=" + movementRange + "]";
	}

}
