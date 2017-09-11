package com.zycus.model.human;

import com.zycus.model.Creature;

public class Human extends Creature {
	private String gene = "human";
	private int medicine;

	public Human(int life, int movementRange, String gene, int medicine) {
		super(life, movementRange);
		this.gene = gene;
		this.medicine = medicine;
	}

	public Human() {
		super();
	}

	public Human(int life, int movementRange) {
		super(life, movementRange);
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}

	@Override
	public String toString() {
		return "Human [gene=" + gene + ", medicine=" + medicine + "]";
	}

}
